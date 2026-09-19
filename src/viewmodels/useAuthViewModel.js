import { useCallback, useState } from 'react';
import { appRepository } from '../services/appRepository';

export function useAuthViewModel(setSession) {
  const [loading, setLoading] = useState(false);
  const register = useCallback(async (name) => {
    setLoading(true);
    try {
      const next = await appRepository.register(name);
      setSession(next);
      return next;
    } finally { setLoading(false); }
  }, [setSession]);

  const logout = useCallback(async () => {
    await appRepository.logout();
    setSession(null);
  }, [setSession]);

  return { loading, register, logout };
}
