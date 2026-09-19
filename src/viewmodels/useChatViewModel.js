import { useEffect, useState } from 'react';
import { appRepository } from '../services/appRepository';

export function useChatViewModel({ session, setSession, counsellor }) {
  const [text, setText] = useState('');
  const [sending, setSending] = useState(false);
  const [messages, setMessages] = useState(() => session?.chatThreads?.[counsellor?.id] || [
    { id: 'welcome', sender: 'counsellor', text: `Hello ${session?.displayName || 'there'}. I’m here to listen. How are you feeling today?`, time: 'Now' },
  ]);

  useEffect(() => {
    setMessages(session?.chatThreads?.[counsellor?.id] || []);
  }, [session, counsellor?.id]);

  const send = async () => {
    const value = text.trim();
    if (!value || sending) return;
    setSending(true);
    try {
      const message = { id: `${Date.now()}`, sender: 'user', text: value, time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) };
      const next = await appRepository.addChatMessage(session, counsellor.id, message);
      setSession(next);
      setMessages(next.chatThreads[counsellor.id]);
      setText('');
      setTimeout(async () => {
        const reply = { id: `${Date.now()}-r`, sender: 'counsellor', text: 'Thank you for sharing that. Let’s take it one step at a time.', time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) };
        const latest = await appRepository.addChatMessage(next, counsellor.id, reply);
        setSession(latest);
        setMessages(latest.chatThreads[counsellor.id]);
      }, 700);
    } finally { setSending(false); }
  };
  return { text, setText, messages, sending, send };
}
