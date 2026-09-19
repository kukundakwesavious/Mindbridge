import { useCallback, useState } from 'react';
import { appRepository } from '../services/appRepository';
import { Session } from '../types';

export function useAuthViewModel(setSession: (s: Session | null) => void) {
  const [loading, setLoading] = useState(false);

  const register = useCallback(
    async (name: string) => {
      setLoading(true);
      try {
        const next = await appRepository.register(name);
        setSession(next);
        return next;
      } finally {
        setLoading(false);
      }
    },
    [setSession]
  );

  const registerWithCredentials = useCallback(
    async (params: {
      displayName: string;
      email: string;
      district?: string;
      university?: string;
    }) => {
      setLoading(true);
      try {
        const next = await appRepository.registerWithCredentials(params);
        setSession(next);
        return next;
      } finally {
        setLoading(false);
      }
    },
    [setSession]
  );

  const registerWithGoogle = useCallback(
    async (params: {
      displayName: string;
      email: string;
      avatar?: string;
      university?: string;
    }) => {
      setLoading(true);
      try {
        const next = await appRepository.registerWithGoogle(params);
        setSession(next);
        return next;
      } finally {
        setLoading(false);
      }
    },
    [setSession]
  );

  const logout = useCallback(async () => {
    await appRepository.logout();
    setSession(null);
  }, [setSession]);

  return {
    loading,
    register,
    registerWithCredentials,
    registerWithGoogle,
    logout,
  };
}
