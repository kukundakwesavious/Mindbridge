import { Session } from '../types';

const KEY = '@mindbridge_session_v2';

export async function getStoredSession(): Promise<Session | null> {
  try {
    const raw = typeof window !== 'undefined' ? window.localStorage.getItem(KEY) : null;
    return raw ? JSON.parse(raw) : null;
  } catch (err) {
    console.error('Failed to load session from storage:', err);
    return null;
  }
}

export async function saveStoredSession(session: Session): Promise<Session> {
  try {
    if (typeof window !== 'undefined') {
      window.localStorage.setItem(KEY, JSON.stringify(session));
    }
  } catch (err) {
    console.error('Failed to save session to storage:', err);
  }
  return session;
}

export async function clearStoredSession(): Promise<void> {
  try {
    if (typeof window !== 'undefined') {
      window.localStorage.removeItem(KEY);
    }
  } catch (err) {
    console.error('Failed to clear session from storage:', err);
  }
}
