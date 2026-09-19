import React from 'react';
import { Star } from 'lucide-react';
import { Avatar, Card, Header, PrimaryButton, Screen } from '../components/UI';
import { useNavigation } from '../../navigation/NavigationContext';
import { Counsellor } from '../../types';

export default function CounsellorDetailScreen() {
  const navigation = useNavigation();
  const c = navigation.params?.counsellor as Counsellor;

  if (!c) {
    return (
      <Screen>
        <Header title="Counsellor Profile" onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">Counsellor not found.</p>
      </Screen>
    );
  }

  return (
    <Screen>
      <Header
        title="Counsellor profile"
        onBack={() => navigation.goBack()}
      />

      <Card className="flex flex-col items-center py-6 text-center mb-3">
        <Avatar image={c.image} initials={c.avatar} size={110} />
        <h2 className="text-xl font-black text-[#14213D] mt-3">{c.name}</h2>
        <p className="text-sm text-[#68758A] mt-0.5">{c.role}</p>
        <p className="text-sm font-extrabold text-[#1456B8] mt-1">{c.speciality}</p>

        <div className="flex items-center gap-1.5 mt-2.5 bg-amber-50 px-3 py-1 rounded-full border border-amber-100">
          <Star size={16} className="text-[#D99B00] fill-[#D99B00]" />
          <span className="text-sm font-black text-[#14213D]">{c.rating}</span>
          <span className="text-xs text-[#68758A]">from {c.reviews} reviews</span>
        </div>
      </Card>

      <Card className="mb-4">
        <div className="mb-3">
          <h3 className="text-xs font-black uppercase tracking-wider text-[#14213D] mb-1">
            Languages
          </h3>
          <p className="text-sm text-[#68758A] font-medium">{c.languages.join(', ')}</p>
        </div>

        <div>
          <h3 className="text-xs font-black uppercase tracking-wider text-[#14213D] mb-1">
            Availability
          </h3>
          <p className="text-sm font-medium text-[#14213D]">
            {c.available ? (
              <span className="text-[#1B9A72] flex items-center gap-1.5">
                <span className="w-2 h-2 rounded-full bg-[#1B9A72]" />
                Currently accepting sessions
              </span>
            ) : (
              <span className="text-[#68758A]">Currently unavailable</span>
            )}
          </p>
        </div>
      </Card>

      <div className="space-y-3">
        <PrimaryButton
          title={c.available ? 'Book a session' : 'Join availability list'}
          icon="calendar-outline"
          disabled={!c.available}
          onPress={() => navigation.navigate('Booking', { counsellor: c })}
        />

        <p className="text-[11px] leading-relaxed text-[#68758A] text-center px-4">
          Profiles in this prototype represent the proposed verified-professional workflow. Production deployment requires real credential verification.
        </p>
      </div>
    </Screen>
  );
}
