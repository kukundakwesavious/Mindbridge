import AsyncStorage from '@react-native-async-storage/async-storage';

const KEY = '@mindbridge_session_v2';

export async function getStoredSession() {
  try {
    const raw = await AsyncStorage.getItem(KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export async function saveStoredSession(session) {
  await AsyncStorage.setItem(KEY, JSON.stringify(session));
  return session;
}

export async function clearStoredSession() {
  await AsyncStorage.removeItem(KEY);
}
