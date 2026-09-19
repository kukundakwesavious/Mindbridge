import { useCallback, useState } from 'react';
import { appRepository } from '../services/appRepository';
export function useProfileViewModel({ session, setSession }) {
  const [saving, setSaving] = useState(false);
  const update = useCallback(async (patch) => {
    setSaving(true);
    try { const next = await appRepository.updateSession(session, patch); setSession(next); return next; }
    finally { setSaving(false); }
  }, [session, setSession]);
  return { saving, update };
}
