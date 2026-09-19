import { useMemo } from 'react';
import { Session } from '../types';

export function useHomeViewModel(session: Session | null | undefined) {
  return useMemo(
    () => ({
      greeting: session?.displayName || 'Friend',
      upcoming: (session?.bookedSessions || [])
        .filter((s) => s.status !== 'completed')
        .slice(0, 2),
    }),
    [session]
  );
}
