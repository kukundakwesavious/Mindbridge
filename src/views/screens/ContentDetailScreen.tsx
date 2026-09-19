import React from 'react';
import { Card, Header, IconBox, PrimaryButton, Screen } from '../components/UI';
import { useNavigation } from '../../navigation/NavigationContext';
import { ContentItem } from '../../types';

export default function ContentDetailScreen() {
  const navigation = useNavigation();
  const item = navigation.params?.item as ContentItem;

  if (!item) {
    return (
      <Screen>
        <Header title="Content Detail" onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">Resource not found.</p>
      </Screen>
    );
  }

  return (
    <Screen>
      <Header
        title={item.type}
        subtitle={`${item.time} • ${item.category}`}
        onBack={() => navigation.goBack()}
      />

      <div className="mb-4">
        <div className="flex items-center gap-3 mb-2">
          <IconBox icon={item.icon} tone={item.tone} size={42} />
          <span className="text-xs font-black uppercase tracking-wider text-[#1456B8] bg-[#EAF2FF] px-2.5 py-1 rounded-md">
            {item.category}
          </span>
        </div>
        <h1 className="text-2xl font-black text-[#14213D] leading-tight mt-2">
          {item.title}
        </h1>
      </div>

      <Card className="bg-[#EAF2FF] border-[#CFE1FD] p-4 mb-4">
        <h2 className="text-xs font-black uppercase tracking-wider text-[#1456B8] mb-1">
          Key takeaway
        </h2>
        <p className="text-sm text-[#14213D] leading-relaxed font-medium">
          {item.summary}
        </p>
      </Card>

      <div className="space-y-3 mb-6">
        {item.body.map((p, idx) => (
          <Card key={idx} className="p-4 mb-0">
            <p className="text-sm text-[#14213D] leading-relaxed">
              {p}
            </p>
          </Card>
        ))}
      </div>

      <Card className="p-4 bg-white border border-[#E3E9F2]">
        <h2 className="text-sm font-black text-[#14213D] mb-1">
          Need someone to talk to?
        </h2>
        <p className="text-xs text-[#68758A] leading-relaxed mb-4">
          A verified counsellor can help you work through these questions privately and at your pace.
        </p>
        <PrimaryButton
          title="Find a counsellor"
          icon="chatbubble-ellipses"
          onPress={() => navigation.navigate('Counsellors')}
        />
      </Card>
    </Screen>
  );
}
