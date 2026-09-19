import React, { useRef, useEffect } from 'react';
import { ArrowUp } from 'lucide-react';
import { Avatar, Header, Screen } from '../components/UI';
import { useChatViewModel } from '../../viewmodels/useChatViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Counsellor, Session } from '../../types';

interface ChatScreenProps {
  session: Session;
  setSession: (s: Session) => void;
}

export default function ChatScreen({ session, setSession }: ChatScreenProps) {
  const navigation = useNavigation();
  const c = navigation.params?.counsellor as Counsellor;
  const vm = useChatViewModel({ session, setSession, counsellor: c });
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [vm.messages]);

  if (!c) {
    return (
      <Screen>
        <Header title="Secure Chat" onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">No chat session found.</p>
      </Screen>
    );
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      vm.send();
    }
  };

  return (
    <div className="min-h-screen w-full bg-[#F7F9FC] flex flex-col items-center justify-center">
      <div className="w-full max-w-md h-screen bg-[#F7F9FC] flex flex-col relative shadow-xl sm:border-x sm:border-slate-200">
        {/* Chat Header */}
        <div className="p-4 pb-3 border-b border-[#E3E9F2] bg-white">
          <Header
            title={c.name}
            subtitle={`${c.role} • Secure text session`}
            onBack={() => navigation.goBack()}
            right={<Avatar image={c.image} initials={c.avatar} size={40} />}
          />
        </div>

        {/* Message Thread */}
        <div className="flex-1 overflow-y-auto p-4 space-y-3">
          {vm.messages.map((m) => {
            const isUser = m.sender === 'user';
            return (
              <div
                key={m.id}
                className={`flex flex-col max-w-[82%] ${
                  isUser ? 'ml-auto items-end' : 'mr-auto items-start'
                }`}
              >
                <div
                  className={`p-3.5 rounded-2xl text-[13px] leading-relaxed shadow-2xs ${
                    isUser
                      ? 'bg-[#1456B8] text-white rounded-br-xs'
                      : 'bg-white border border-[#E3E9F2] text-[#14213D] rounded-bl-xs'
                  }`}
                >
                  <p>{m.text}</p>
                </div>
                <span
                  className={`text-[10px] mt-1 px-1 ${
                    isUser ? 'text-[#68758A]' : 'text-[#68758A]'
                  }`}
                >
                  {m.time}
                </span>
              </div>
            );
          })}
          <div ref={messagesEndRef} />
        </div>

        {/* Composer */}
        <div className="p-3 border-t border-[#E3E9F2] bg-white flex items-center gap-2">
          <input
            type="text"
            value={vm.text}
            onChange={(e) => vm.setText(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Write a message..."
            className="flex-1 min-h-[48px] border border-[#E3E9F2] rounded-2xl px-4 py-2 text-sm text-[#14213D] bg-[#F8FAFD] focus:outline-none focus:border-[#1456B8]"
          />
          <button
            type="button"
            onClick={vm.send}
            disabled={!vm.text.trim() || vm.sending}
            className="w-11 h-11 rounded-full bg-[#1456B8] hover:bg-[#0B3F8A] active:scale-95 disabled:opacity-40 text-white flex items-center justify-center flex-shrink-0 cursor-pointer transition-all"
          >
            <ArrowUp size={20} />
          </button>
        </div>
      </div>
    </div>
  );
}
