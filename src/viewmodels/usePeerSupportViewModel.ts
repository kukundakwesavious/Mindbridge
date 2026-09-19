import { useMemo, useState } from 'react';
import { appRepository } from '../services/appRepository';
import { PeerPost, Session } from '../types';

const seed: PeerPost[] = [
  {
    id: 'seed1',
    author: 'Anonymous',
    text: 'Small steps still count. I’m learning to ask for support when I need it.',
    createdAt: new Date(Date.now() - 3600000 * 5).toISOString(),
  },
  {
    id: 'seed2',
    author: 'Anonymous',
    text: 'Today I took a break, called a friend and felt a little lighter.',
    createdAt: new Date(Date.now() - 3600000 * 24).toISOString(),
  },
];

interface UsePeerSupportProps {
  session: Session;
  setSession: (s: Session) => void;
}

export function usePeerSupportViewModel({ session, setSession }: UsePeerSupportProps) {
  const [text, setText] = useState('');

  const posts = useMemo(() => {
    return [...(session?.peerPosts || []), ...seed];
  }, [session?.peerPosts]);

  const publish = async () => {
    if (!text.trim()) return;
    const post: PeerPost = {
      id: `P-${Date.now()}`,
      author: 'Anonymous',
      text: text.trim(),
      createdAt: new Date().toISOString(),
    };
    const next = await appRepository.addPeerPost(session, post);
    setSession(next);
    setText('');
  };

  return { text, setText, posts, publish };
}
