import { Session } from '../types';

export const initialSession: Session = {
  anonymousId: '',
  displayName: 'Friend',
  language: 'English',
  notifications: true,
  onboardingComplete: true,
  bookedSessions: [],
  referrals: [],
  chatThreads: {},
  peerPosts: [],
};

export function createAnonymousSession(name = 'Friend'): Session {
  return {
    ...initialSession,
    displayName: name.trim() || 'Friend',
    authProvider: 'anonymous',
    anonymousId: `MB-${Math.random().toString(36).slice(2, 8).toUpperCase()}`,
  };
}

export function createCredentialSession(params: {
  displayName: string;
  email: string;
  district?: string;
  university?: string;
}): Session {
  return {
    ...initialSession,
    displayName: params.displayName.trim() || 'MindBridge Member',
    email: params.email.trim(),
    district: params.district,
    university: params.university,
    authProvider: 'credentials',
    anonymousId: `MB-${Math.random().toString(36).slice(2, 8).toUpperCase()}`,
  };
}

export function createGoogleSession(params: {
  displayName: string;
  email: string;
  avatar?: string;
  university?: string;
}): Session {
  return {
    ...initialSession,
    displayName: params.displayName.trim() || 'Google User',
    email: params.email.trim(),
    avatar: params.avatar,
    university: params.university,
    authProvider: 'google',
    anonymousId: `MB-GGL-${Math.random().toString(36).slice(2, 8).toUpperCase()}`,
  };
}
