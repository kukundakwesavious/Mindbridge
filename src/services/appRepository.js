import { clearStoredSession, getStoredSession, saveStoredSession } from './storage';
import { createAnonymousSession } from '../models/session';

export const appRepository = {
  async getSession() { return getStoredSession(); },
  async register(name) { return saveStoredSession(createAnonymousSession(name)); },
  async logout() { return clearStoredSession(); },
  async updateSession(session, patch) { return saveStoredSession({ ...session, ...patch }); },
  async addBooking(session, booking) {
    return saveStoredSession({ ...session, bookedSessions: [...(session.bookedSessions || []), booking] });
  },
  async addReferral(session, referral) {
    return saveStoredSession({ ...session, referrals: [...(session.referrals || []), referral] });
  },
  async addChatMessage(session, counsellorId, message) {
    const threads = session.chatThreads || {};
    const thread = [...(threads[counsellorId] || []), message];
    return saveStoredSession({ ...session, chatThreads: { ...threads, [counsellorId]: thread } });
  },
  async addPeerPost(session, post) {
    return saveStoredSession({ ...session, peerPosts: [post, ...(session.peerPosts || [])] });
  },
};
