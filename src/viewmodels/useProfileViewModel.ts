import { useCallback, useState } from 'react';
import { appRepository } from '../services/appRepository';
import { Session } from '../types';

interface UseProfileProps {
  session: Session;
  setSession: (s: Session) => void;
}

export function useProfileViewModel({ session, setSession }: UseProfileProps) {
  const [saving, setSaving] = useState(false);

  const update = useCallback(
    async (patch: Partial<Session>) => {
      setSaving(true);
      try {
        const next = await appRepository.updateSession(session, patch);
        setSession(next);
        return next;
      } finally {
        setSaving(false);
      }
    },
    [session, setSession]
  );

  return { saving, update };
}
