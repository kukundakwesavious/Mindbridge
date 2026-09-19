import { useMemo } from 'react';
export function useHomeViewModel(session) {
  return useMemo(() => ({
    greeting: session?.displayName || 'Friend',
    upcoming: (session?.bookedSessions || []).filter(s => s.status !== 'completed').slice(0, 2),
  }), [session]);
}
