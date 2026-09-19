export const initialSession = {
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

export function createAnonymousSession(name = 'Friend') {
  return {
    ...initialSession,
    displayName: name.trim() || 'Friend',
    anonymousId: `MB-${Math.random().toString(36).slice(2, 8).toUpperCase()}`,
  };
}
