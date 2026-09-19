import { clearStoredSession, getStoredSession, saveStoredSession } from './storage';
import { createAnonymousSession, createCredentialSession, createGoogleSession } from '../models/session';
import { Booking, ChatMessage, PeerPost, ReferralRequest, Session } from '../types';

export const appRepository = {
  async getSession(): Promise<Session | null> {
    return getStoredSession();
  },
  async register(name: string): Promise<Session> {
    return saveStoredSession(createAnonymousSession(name));
  },
  async registerWithCredentials(params: {
    displayName: string;
    email: string;
    district?: string;
    university?: string;
  }): Promise<Session> {
    return saveStoredSession(createCredentialSession(params));
  },
  async registerWithGoogle(params: {
    displayName: string;
    email: string;
    avatar?: string;
    university?: string;
  }): Promise<Session> {
    return saveStoredSession(createGoogleSession(params));
  },
  async logout(): Promise<void> {
    return clearStoredSession();
  },
  async updateSession(session: Session, patch: Partial<Session>): Promise<Session> {
    return saveStoredSession({ ...session, ...patch });
  },
  async addBooking(session: Session, booking: Booking): Promise<Session> {
    return saveStoredSession({
      ...session,
      bookedSessions: [...(session.bookedSessions || []), booking],
    });
  },
  async addReferral(session: Session, referral: ReferralRequest): Promise<Session> {
    return saveStoredSession({
      ...session,
      referrals: [...(session.referrals || []), referral],
    });
  },
  async addChatMessage(session: Session, counsellorId: string, message: ChatMessage): Promise<Session> {
    const threads = session.chatThreads || {};
    const thread = [...(threads[counsellorId] || []), message];
    return saveStoredSession({
      ...session,
      chatThreads: { ...threads, [counsellorId]: thread },
    });
  },
  async addPeerPost(session: Session, post: PeerPost): Promise<Session> {
    return saveStoredSession({
      ...session,
      peerPosts: [post, ...(session.peerPosts || [])],
    });
  },
};
