import React from 'react';
import { ChevronRight } from 'lucide-react';
import { Card, Header, IconBox, Screen, SmartImage } from '../components/UI';
import { referralTypes } from '../../models/referrals';
import { media } from '../../data/media';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface ReferralsScreenProps {
  session: Session | null | undefined;
}

export default function ReferralsScreen({ session }: ReferralsScreenProps) {
  const navigation = useNavigation();
  const submitted = session?.referrals || [];

  return (
    <Screen>
      <Header
        title="Referral pathways"
        subtitle="Connect to local care"
        onBack={navigation.canGoBack() ? () => navigation.goBack() : undefined}
      />

      <div className="h-44 rounded-2xl overflow-hidden mb-4 border border-[#E3E9F2]">
        <SmartImage
          source={media.ugandaCommunity}
          fallback="/assets/referral-illustration.png"
          className="w-full h-full object-cover"
          alt="Community referrals banner"
        />
      </div>

      <div className="mb-3">
        <h2 className="text-base font-black text-[#14213D]">
          Choose a support pathway
        </h2>
        <p className="text-xs text-[#68758A] mt-0.5 leading-relaxed">
          MindBridge connects you with verified community leaders, health facilities and emergency resources.
        </p>
      </div>

      <div className="space-y-3 mb-6">
        {referralTypes.map((item) => (
          <Card
            key={item.id}
            onClick={() => navigation.navigate('ReferralDetail', { type: item })}
            className="flex items-center gap-3.5 p-4"
          >
            <IconBox icon={item.icon} tone={item.tone} size={48} />
            <div className="flex-1 min-w-0">
              <h3 className="text-sm font-black text-[#14213D] leading-snug">
                {item.title}
              </h3>
              <p className="text-xs text-[#68758A] mt-0.5">
                {item.subtitle}
              </p>
            </div>
            <ChevronRight size={18} className="text-[#68758A] flex-shrink-0" />
          </Card>
        ))}
      </div>

      {submitted.length > 0 && (
        <div className="mb-4">
          <h2 className="text-sm font-black text-[#14213D] mb-2">
            Your submitted referral requests
          </h2>
          <div className="space-y-2">
            {submitted.map((r) => (
              <Card key={r.id} className="p-3.5 bg-[#F9FBFE]">
                <div className="flex items-center justify-between">
                  <span className="text-xs font-black text-[#14213D]">
                    {r.provider}
                  </span>
                  <span className="text-[10px] font-black uppercase tracking-wider px-2 py-0.5 rounded-full bg-[#EAF2FF] text-[#1456B8]">
                    {r.status}
                  </span>
                </div>
                <p className="text-[11px] text-[#68758A] mt-1">
                  Type: {r.type} • Requested on {new Date(r.createdAt).toLocaleDateString()}
                </p>
              </Card>
            ))}
          </div>
        </div>
      )}
    </Screen>
  );
}
