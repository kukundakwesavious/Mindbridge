import React from 'react';
import { ChevronRight, Sparkles } from 'lucide-react';
import { Card, Header, IconBox, Pill, Screen, SearchField } from '../components/UI';
import { useLibraryViewModel } from '../../viewmodels/useLibraryViewModel';
import { useNavigation } from '../../navigation/NavigationContext';

export default function LibraryScreen() {
  const navigation = useNavigation();
  const vm = useLibraryViewModel();

  return (
    <Screen>
      <Header
        title="Psychoeducation"
        subtitle="Accessible wellbeing guides"
        onBack={navigation.canGoBack() ? () => navigation.goBack() : undefined}
      />

      {/* Amani AI companion shortcut in Library */}
      <div
        onClick={() => navigation.navigate('AmaniChat')}
        className="mb-4 bg-gradient-to-r from-[#EBF3FC] via-[#F2F9F5] to-white border border-[#D7E6F8] rounded-2xl p-3.5 flex items-center justify-between cursor-pointer hover:border-[#1456B8] transition-all shadow-2xs group"
      >
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] text-white flex items-center justify-center shrink-0 shadow-2xs group-hover:scale-105 transition-transform">
            <Sparkles size={18} />
          </div>
          <div>
            <h3 className="text-xs font-black text-[#14213D] flex items-center gap-1.5">
              Need personalized guidance?
              <span className="text-[10px] font-bold text-[#1456B8] bg-[#E1EEFD] px-1.5 py-0.5 rounded-full">Ask Amani</span>
            </h3>
            <p className="text-[11px] text-[#68758A] mt-0.5">
              Amani can explain concepts, coping tools, or help with what you're feeling.
            </p>
          </div>
        </div>
        <ChevronRight size={18} className="text-[#68758A] group-hover:translate-x-0.5 transition-transform shrink-0 ml-2" />
      </div>

      <SearchField
        value={vm.query}
        onChangeText={vm.setQuery}
        placeholder="Search articles and guides"
      />

      <div className="flex items-center overflow-x-auto pb-1 mb-3 -mx-1 px-1 scrollbar-none">
        {vm.categories.map((c) => (
          <Pill
            key={c}
            label={c}
            active={vm.category === c}
            onPress={() => vm.setCategory(c)}
          />
        ))}
      </div>

      <div className="space-y-3">
        {vm.items.map((item) => (
          <Card
            key={item.id}
            onClick={() => navigation.navigate('ContentDetail', { item })}
            className="flex items-center gap-3.5 p-4"
          >
            <IconBox icon={item.icon} tone={item.tone} size={50} />
            <div className="flex-1 min-w-0">
              <h2 className="text-sm font-black text-[#14213D] leading-snug">
                {item.title}
              </h2>
              <p className="text-xs text-[#68758A] mt-0.5">
                {item.type} • {item.time} • {item.category}
              </p>
              <p className="text-xs text-[#68758A] mt-1 line-clamp-2 leading-relaxed">
                {item.summary}
              </p>
            </div>
            <ChevronRight size={18} className="text-[#68758A] flex-shrink-0" />
          </Card>
        ))}

        {vm.items.length === 0 && (
          <div className="py-12 text-center text-sm text-[#68758A]">
            No resources match your search.
          </div>
        )}
      </div>
    </Screen>
  );
}
