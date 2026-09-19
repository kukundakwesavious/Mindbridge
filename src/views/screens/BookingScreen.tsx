import React, { useState } from 'react';
import { CheckCircle2 } from 'lucide-react';
import { Avatar, Card, Header, Pill, PrimaryButton, Screen } from '../components/UI';
import { useBookingViewModel } from '../../viewmodels/useBookingViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Counsellor, Session } from '../../types';

interface BookingScreenProps {
  session: Session;
  setSession: (s: Session) => void;
}

export default function BookingScreen({ session, setSession }: BookingScreenProps) {
  const navigation = useNavigation();
  const c = navigation.params?.counsellor as Counsellor;
  const vm = useBookingViewModel({ session, setSession, counsellor: c });
  const [success, setSuccess] = useState(false);

  if (!c) {
    return (
      <Screen>
        <Header title="Book a session" onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">No counsellor specified.</p>
      </Screen>
    );
  }

  const handleBook = async () => {
    const booking = await vm.book();
    if (booking) {
      setSuccess(true);
    }
  };

  return (
    <Screen>
      <Header
        title="Book a session"
        subtitle={c.name}
        onBack={() => navigation.goBack()}
      />

      {/* Success Modal / Banner */}
      {success ? (
        <div className="bg-white rounded-2xl p-6 border border-[#E3E9F2] text-center shadow-lg animate-fade-in my-auto">
          <div className="w-16 h-16 rounded-full bg-[#E8F7F1] text-[#1B9A72] flex items-center justify-center mx-auto mb-4">
            <CheckCircle2 size={36} />
          </div>
          <h2 className="text-xl font-black text-[#14213D]">Session Booked!</h2>
          <p className="text-xs text-[#68758A] mt-2 mb-6 leading-relaxed">
            Your appointment with <span className="font-bold text-[#14213D]">{c.name}</span> has been saved on this device.
          </p>
          <div className="bg-[#F2F6FC] rounded-xl p-3 text-xs text-[#68758A] mb-6">
            <span className="font-bold text-[#14213D]">{vm.mode}</span> on{' '}
            <span className="font-bold text-[#14213D]">{vm.date}</span> at{' '}
            <span className="font-bold text-[#14213D]">{vm.time}</span>
          </div>
          <div className="space-y-2">
            <PrimaryButton
              title="Open my sessions"
              onPress={() => navigation.navigate('Sessions')}
            />
            <button
              type="button"
              onClick={() => navigation.navigate('Main')}
              className="w-full py-3 text-sm font-extrabold text-[#68758A] hover:text-[#14213D] cursor-pointer"
            >
              Back to Home
            </button>
          </div>
        </div>
      ) : (
        <>
          {/* Counsellor Info */}
          <Card className="flex items-center gap-3 mb-4">
            <Avatar image={c.image} initials={c.avatar} size={58} />
            <div className="flex-1 min-w-0">
              <h2 className="text-sm font-black text-[#14213D] truncate">{c.name}</h2>
              <p className="text-xs text-[#68758A] mt-0.5">
                {c.role} • {c.speciality}
              </p>
            </div>
          </Card>

          {/* Session Type */}
          <div className="mb-3">
            <h3 className="text-sm font-black text-[#14213D] mb-2">Session type</h3>
            <div className="flex flex-wrap gap-1">
              {['Text chat', 'Voice call', 'Video call'].map((x) => (
                <Pill
                  key={x}
                  label={x}
                  active={vm.mode === x}
                  onPress={() => vm.setMode(x)}
                />
              ))}
            </div>
          </div>

          {/* Date */}
          <div className="mb-3">
            <h3 className="text-sm font-black text-[#14213D] mb-2">Date</h3>
            <div className="flex flex-wrap gap-1">
              {['Today', 'Tomorrow', 'Next available'].map((x) => (
                <Pill
                  key={x}
                  label={x}
                  active={vm.date === x}
                  onPress={() => vm.setDate(x)}
                />
              ))}
            </div>
          </div>

          {/* Time */}
          <div className="mb-4">
            <h3 className="text-sm font-black text-[#14213D] mb-2">Time</h3>
            <div className="flex items-center overflow-x-auto pb-1 -mx-1 px-1 scrollbar-none">
              {vm.slots.map((x) => (
                <Pill
                  key={x}
                  label={x}
                  active={vm.time === x}
                  onPress={() => vm.setTime(x)}
                />
              ))}
            </div>
          </div>

          {/* Summary */}
          <Card className="bg-[#F2F6FC] border-[#DCE5F1] mb-5">
            <h3 className="text-xs font-black uppercase tracking-wider text-[#14213D]">
              Booking summary
            </h3>
            <p className="text-xs font-bold text-[#68758A] mt-1">
              {vm.mode} • {vm.date} • {vm.time}
            </p>
          </Card>

          <PrimaryButton
            title="Confirm booking"
            icon="shield-checkmark"
            loading={vm.saving}
            onPress={handleBook}
          />
        </>
      )}
    </Screen>
  );
}
