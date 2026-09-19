import React from 'react';
import { ChevronRight, Star } from 'lucide-react';
import { Avatar, Card, Header, Pill, Screen, SearchField } from '../components/UI';
import { useCounsellorsViewModel } from '../../viewmodels/useCounsellorsViewModel';
import { useNavigation } from '../../navigation/NavigationContext';

export default function CounsellorListScreen() {
  const navigation = useNavigation();
  const vm = useCounsellorsViewModel();
  const filters = ['All', 'Available', 'English', 'Luganda', 'Runyankore'];

  return (
    <Screen>
      <Header
        title="Talk to a Counsellor"
        subtitle="Verified professionals"
        onBack={() => navigation.goBack()}
      />

      <SearchField
        value={vm.query}
        onChangeText={vm.setQuery}
        placeholder="Search by name or speciality"
      />

      <div className="flex items-center overflow-x-auto pb-1 mb-3 -mx-1 px-1 scrollbar-none">
        {filters.map((f) => (
          <Pill
            key={f}
            label={f}
            active={vm.filter === f}
            onPress={() => vm.setFilter(f)}
          />
        ))}
      </div>

      <div className="space-y-3">
        {vm.counsellors.map((c) => (
          <Card
            key={c.id}
            onClick={() => navigation.navigate('CounsellorDetail', { counsellor: c })}
            className="flex items-center gap-3 p-3.5"
          >
            <Avatar image={c.image} initials={c.avatar} size={64} />
            <div className="flex-1 min-w-0">
              <div className="flex items-center justify-between gap-1">
                <h2 className="text-sm font-black text-[#14213D] truncate">
                  {c.name}
                </h2>
                {c.available && (
                  <span className="flex items-center gap-1 text-[10px] font-extrabold text-[#1B9A72] bg-[#E8F7F1] px-2 py-0.5 rounded-full flex-shrink-0">
                    <span className="w-1.5 h-1.5 rounded-full bg-[#1B9A72]" />
                    Available
                  </span>
                )}
              </div>
              <p className="text-xs text-[#68758A] mt-0.5">{c.role}</p>
              <p className="text-xs font-bold text-[#14213D] mt-0.5 truncate">
                {c.speciality}
              </p>

              <div className="flex items-center gap-2 mt-1.5 text-xs text-[#68758A]">
                <span className="flex items-center gap-1 font-bold text-[#14213D]">
                  <Star size={13} className="text-[#D99B00] fill-[#D99B00]" />
                  {c.rating}
                  <span className="text-[#68758A] font-normal">({c.reviews})</span>
                </span>
                <span>•</span>
                <span className="truncate text-[11px]">{c.languages.join(' • ')}</span>
              </div>
            </div>
            <ChevronRight size={18} className="text-[#68758A] flex-shrink-0" />
          </Card>
        ))}

        {vm.counsellors.length === 0 && (
          <div className="py-12 text-center text-sm text-[#68758A]">
            No counsellors match your search.
          </div>
        )}
      </div>
    </Screen>
  );
}
