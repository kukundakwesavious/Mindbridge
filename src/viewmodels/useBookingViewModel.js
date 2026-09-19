import { useMemo, useState } from 'react';
import { appRepository } from '../services/appRepository';

const slots = ['09:00 AM', '10:30 AM', '12:00 PM', '02:00 PM', '04:00 PM'];
export function useBookingViewModel({ session, setSession, counsellor }) {
  const [mode, setMode] = useState('Text chat');
  const [date, setDate] = useState('Today');
  const [time, setTime] = useState(slots[1]);
  const [saving, setSaving] = useState(false);
  const canBook = !!counsellor?.available && !!time;
  const summary = useMemo(() => ({ mode, date, time }), [mode, date, time]);

  const book = async () => {
    if (!canBook) return null;
    setSaving(true);
    try {
      const booking = {
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
    } finally { setSaving(false); }
  };
  return { slots, mode, setMode, date, setDate, time, setTime, saving, canBook, summary, book };
}
