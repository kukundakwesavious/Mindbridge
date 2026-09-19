import { useState } from 'react';
import { appRepository } from '../services/appRepository';
import { referralTypes, faithLeaders } from '../models/referrals';
export function useReferralViewModel({ session, setSession }) {
  const [saving, setSaving] = useState(false);
  const createReferral = async (type, provider = null) => {
    setSaving(true);
    try {
      const referral = { id: `R-${Date.now()}`, type, provider: provider?.name || type, status: 'submitted', createdAt: new Date().toISOString() };
      const next = await appRepository.addReferral(session, referral);
      setSession(next);
      return referral;
    } finally { setSaving(false); }
  };
  return { referralTypes, faithLeaders, saving, createReferral };
}
