import { useMemo, useState } from 'react';
import { appRepository } from '../services/appRepository';
import { Booking, Counsellor, Session } from '../types';

const slots = ['09:00 AM', '10:30 AM', '12:00 PM', '02:00 PM', '04:00 PM'];

interface UseBookingProps {
  session: Session;
  setSession: (s: Session) => void;
  counsellor: Counsellor;
}

export function useBookingViewModel({ session, setSession, counsellor }: UseBookingProps) {
  const [mode, setMode] = useState<string>('Text chat');
  const [date, setDate] = useState<string>('Today');
  const [time, setTime] = useState<string>(slots[1]);
  const [saving, setSaving] = useState<boolean>(false);

  const canBook = !!counsellor?.available && !!time;
  const summary = useMemo(() => ({ mode, date, time }), [mode, date, time]);

  const book = async (): Promise<Booking | null> => {
    if (!canBook) return null;
    setSaving(true);
    try {
      const booking: Booking = {
        id: `S-${Date.now()}`,
        counsellorId: counsellor.id,
        counsellorName: counsellor.name,
        mode,
        date,
        time,
        status: 'upcoming',
        createdAt: new Date().toISOString(),
      };
      const next = await appRepository.addBooking(session, booking);
      setSession(next);
      return booking;
    } finally {
      setSaving(false);
    }
  };

  return { slots, mode, setMode, date, setDate, time, setTime, saving, canBook, summary, book };
}
