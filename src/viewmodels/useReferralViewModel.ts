import { useState } from 'react';
import { appRepository } from '../services/appRepository';
import { faithLeaders, referralTypes } from '../models/referrals';
import { FaithLeader, ReferralRequest, Session } from '../types';

interface UseReferralProps {
  session: Session;
  setSession: (s: Session) => void;
}

export function useReferralViewModel({ session, setSession }: UseReferralProps) {
  const [saving, setSaving] = useState(false);

  const createReferral = async (type: string, provider: FaithLeader | null = null): Promise<ReferralRequest> => {
    setSaving(true);
    try {
      const referral: ReferralRequest = {
        id: `R-${Date.now()}`,
        type,
        provider: provider?.name || type,
        status: 'submitted',
        createdAt: new Date().toISOString(),
      };
      const next = await appRepository.addReferral(session, referral);
      setSession(next);
      return referral;
    } finally {
      setSaving(false);
    }
  };

  return { referralTypes, faithLeaders, saving, createReferral };
}
