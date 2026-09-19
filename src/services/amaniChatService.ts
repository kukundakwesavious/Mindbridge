import { AmaniChatMessage } from '../types';

const AMANI_STORAGE_KEY = 'mindbridge_amani_chat_history_v1';

export const DEFAULT_AMANI_GREETING: AmaniChatMessage = {
  id: 'msg-amani-welcome',
  sender: 'amani',
  text: "Oli otya! I'm **Amani**, your 24/7 MindBridge AI companion. *Amani* means *Peace*—and that is exactly what I am here to bring you.\n\nWhether you need practical guidance, help with anxiety or academic pressure, self-care exercises, relationships, understanding your thoughts, or navigating MindBridge's counsellors and resources—I am here to help you with whatever you need.\n\nHow are you feeling right now?",
  time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
};

export function getStoredAmaniChat(): AmaniChatMessage[] {
  try {
    const raw = localStorage.getItem(AMANI_STORAGE_KEY);
    if (!raw) return [DEFAULT_AMANI_GREETING];
    const parsed = JSON.parse(raw);
    return Array.isArray(parsed) && parsed.length > 0 ? parsed : [DEFAULT_AMANI_GREETING];
  } catch (err) {
    console.error('Error loading Amani chat history:', err);
    return [DEFAULT_AMANI_GREETING];
  }
}

export function saveAmaniChat(messages: AmaniChatMessage[]): void {
  try {
    localStorage.setItem(AMANI_STORAGE_KEY, JSON.stringify(messages));
  } catch (err) {
    console.error('Error saving Amani chat history:', err);
  }
}

export function clearAmaniChat(): void {
  try {
    localStorage.removeItem(AMANI_STORAGE_KEY);
  } catch (err) {
    console.error('Error clearing Amani chat history:', err);
  }
}

export async function sendAmaniMessage(
  userText: string,
  history: AmaniChatMessage[]
): Promise<string> {
  try {
    const messagesPayload = history.map((m) => ({
      role: m.sender === 'user' ? 'user' : 'model',
      content: m.text,
    }));

    // Add current user prompt
    messagesPayload.push({
      role: 'user',
      content: userText,
    });

    const response = await fetch('/api/chat', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        messages: messagesPayload,
        message: userText,
      }),
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.details || errorData.error || `Server responded with ${response.status}`);
    }

    const data = await response.json();
    return data.reply || "I'm right here with you. Please take a gentle breath and tell me a bit more.";
  } catch (error: any) {
    console.error('Amani chat request failed:', error);
    // Provide a resilient, supportive fallback response
    return "Thank you for sharing that with me. Even if technology encounters a small bump, your feelings matter deeply. Take a slow, gentle breath in... and out. Tell me more about what you're experiencing, or let me know if you would like me to guide you to a counsellor or emergency hotline.";
  }
}
