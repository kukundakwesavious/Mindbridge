import React, { useState, useEffect, useRef } from 'react';
import {
  Sparkles,
  Send,
  Trash2,
  PhoneCall,
  Shield,
  HelpCircle,
  Compass,
  Bot,
  X,
  ChevronDown,
  Copy,
  Check,
  Volume2,
  VolumeX
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';
import { useNavigation } from '../../navigation/NavigationContext';
import { AmaniChatMessage, Session } from '../../types';
import {
  getStoredAmaniChat,
  saveAmaniChat,
  sendAmaniMessage,
  DEFAULT_AMANI_GREETING,
} from '../../services/amaniChatService';

interface AmaniChatScreenProps {
  session?: Session | null;
  isEmbeddedTab?: boolean;
}

const QUICK_PROMPTS = [
  'I feel stressed and overwhelmed right now 😔',
  'How do I book a confidential counsellor session? 🗓️',
  'Can you guide me through a 2-minute calming breath? 🌿',
  'I have exam / university pressure and cannot focus 📚',
  'What should I do if a friend is in emotional crisis? 🆘',
  'How does MindBridge keep my identity private? 🔒',
];

export default function AmaniChatScreen({ session, isEmbeddedTab = false }: AmaniChatScreenProps) {
  const navigation = useNavigation();
  const [messages, setMessages] = useState<AmaniChatMessage[]>([]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [showClearConfirm, setShowClearConfirm] = useState(false);
  const [showInfoModal, setShowInfoModal] = useState(false);
  const [copiedId, setCopiedId] = useState<string | null>(null);
  const [speakingId, setSpeakingId] = useState<string | null>(null);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  // Load chat history from persistent storage
  useEffect(() => {
    const saved = getStoredAmaniChat();
    setMessages(saved);
  }, []);

  // Auto-scroll to bottom of conversation
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, loading]);

  // Clean up speech synthesis on unmount
  useEffect(() => {
    return () => {
      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
      }
    };
  }, []);

  const handleSend = async (textToSend?: string) => {
    const text = (textToSend || input).trim();
    if (!text || loading) return;

    const userMsg: AmaniChatMessage = {
      id: `usr-${Date.now()}`,
      sender: 'user',
      text,
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };

    const newHistory = [...messages, userMsg];
    setMessages(newHistory);
    saveAmaniChat(newHistory);
    setInput('');
    setLoading(true);

    try {
      const amaniReplyText = await sendAmaniMessage(text, messages);
      const amaniMsg: AmaniChatMessage = {
        id: `amani-${Date.now()}`,
        sender: 'amani',
        text: amaniReplyText,
        time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      };

      const updatedHistory = [...newHistory, amaniMsg];
      setMessages(updatedHistory);
      saveAmaniChat(updatedHistory);
    } catch (err) {
      console.error('Error in chat exchange:', err);
    } finally {
      setLoading(false);
      setTimeout(() => inputRef.current?.focus(), 100);
    }
  };

  const handleClearChat = () => {
    const reset = [DEFAULT_AMANI_GREETING];
    setMessages(reset);
    saveAmaniChat(reset);
    setShowClearConfirm(false);
  };

  const handleCopy = (id: string, text: string) => {
    navigator.clipboard?.writeText(text);
    setCopiedId(id);
    setTimeout(() => setCopiedId(null), 2000);
  };

  const handleSpeak = (id: string, text: string) => {
    if (!('speechSynthesis' in window)) return;

    if (speakingId === id) {
      window.speechSynthesis.cancel();
      setSpeakingId(null);
      return;
    }

    window.speechSynthesis.cancel();
    // Clean text of markdown characters before speaking
    const cleanText = text.replace(/[*#_~`]/g, '');
    const utterance = new SpeechSynthesisUtterance(cleanText);
    utterance.rate = 0.95;
    utterance.pitch = 1.0;
    utterance.onend = () => setSpeakingId(null);
    utterance.onerror = () => setSpeakingId(null);
    setSpeakingId(id);
    window.speechSynthesis.speak(utterance);
  };

  // Rich markdown parser for Gemini-like structured responses
  const renderRichText = (content: string) => {
    const blocks = content.split(/\n\n+/);
    return blocks.map((block, bIdx) => {
      const trimmed = block.trim();

      // Heading 3: ### Title
      if (trimmed.startsWith('### ')) {
        return (
          <h3
            key={bIdx}
            className="text-xs font-black text-[#14213D] uppercase tracking-wider mt-3 mb-1.5 flex items-center gap-1.5"
          >
            <span className="w-1.5 h-3.5 bg-[#1456B8] rounded-full"></span>
            {trimmed.slice(4)}
          </h3>
        );
      }

      // Heading 2: ## Title
      if (trimmed.startsWith('## ')) {
        return (
          <h2
            key={bIdx}
            className="text-sm font-black text-[#14213D] tracking-tight mt-3 mb-1.5 flex items-center gap-1.5"
          >
            <span className="w-2 h-4 bg-[#1B9A72] rounded-full"></span>
            {trimmed.slice(3)}
          </h2>
        );
      }

      // Blockquote: > Quote
      if (trimmed.startsWith('> ')) {
        return (
          <div
            key={bIdx}
            className="border-l-3 border-[#1456B8] bg-[#F2F7FF] pl-3 py-1.5 pr-2 my-2 rounded-r-lg text-xs italic text-[#14213D]"
          >
            {trimmed.slice(2)}
          </div>
        );
      }

      // Lines inside the block
      const lines = trimmed.split('\n');
      return (
        <div key={bIdx} className={bIdx > 0 ? 'mt-2' : ''}>
          {lines.map((line, lIdx) => {
            const lineTrim = line.trim();
            const isBullet = lineTrim.startsWith('- ') || lineTrim.startsWith('* ') || lineTrim.startsWith('• ');
            const numMatch = lineTrim.match(/^(\d+)\.\s+(.*)$/);

            let cleanLine = lineTrim;
            let prefix: React.ReactNode = null;

            if (isBullet) {
              cleanLine = lineTrim.replace(/^[-*•]\s*/, '');
              prefix = <span className="text-[#1456B8] font-black mr-1.5">•</span>;
            } else if (numMatch) {
              cleanLine = numMatch[2];
              prefix = (
                <span className="inline-flex items-center justify-center w-4 h-4 rounded-full bg-[#EBF3FC] text-[#1456B8] font-bold text-[10px] mr-1.5 shrink-0">
                  {numMatch[1]}
                </span>
              );
            }

            // Bold and italic formatting
            const parts = cleanLine.split(/(\*\*[^*]+\*\*|\*[^*]+\*)/g);
            const formatted = parts.map((part, partIdx) => {
              if (part.startsWith('**') && part.endsWith('**')) {
                return (
                  <strong key={partIdx} className="font-black text-[#0F1E36]">
                    {part.slice(2, -2)}
                  </strong>
                );
              }
              if (part.startsWith('*') && part.endsWith('*')) {
                return (
                  <em key={partIdx} className="italic text-[#1456B8]">
                    {part.slice(1, -1)}
                  </em>
                );
              }
              return part;
            });

            if (isBullet || numMatch) {
              return (
                <div key={lIdx} className="flex items-start mt-1 text-xs leading-relaxed">
                  {prefix}
                  <span className="flex-1">{formatted}</span>
                </div>
              );
            }

            return (
              <p key={lIdx} className="text-xs leading-relaxed">
                {formatted}
              </p>
            );
          })}
        </div>
      );
    });
  };

  return (
    <div
      className={`min-h-screen bg-[#F7F9FC] flex flex-col justify-between max-w-md mx-auto relative border-x border-[#E3E9F2] shadow-sm ${
        isEmbeddedTab ? 'pb-[76px]' : ''
      }`}
    >
      {/* Top Android App Bar */}
      <div className="bg-white/95 backdrop-blur-md border-b border-[#E3E9F2] sticky top-0 z-20 px-4 py-3 shadow-2xs">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            {!isEmbeddedTab && (
              <button
                type="button"
                onClick={() => navigation.goBack()}
                className="p-1.5 -ml-1 text-[#68758A] hover:text-[#14213D] rounded-full hover:bg-slate-100 transition-colors cursor-pointer"
                title="Back"
              >
                <ChevronDown size={22} className="rotate-90" />
              </button>
            )}

            <div className="relative">
              <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] flex items-center justify-center text-white shadow-xs">
                <Sparkles size={20} />
              </div>
              <span className="absolute bottom-0 right-0 w-3 h-3 bg-[#1B9A72] border-2 border-white rounded-full"></span>
            </div>

            <div>
              <div className="flex items-center gap-1.5">
                <h1 className="text-base font-black text-[#14213D] tracking-tight">
                  Amani
                </h1>
                <span className="text-[10px] font-black tracking-wider uppercase px-1.5 py-0.5 rounded-full bg-gradient-to-r from-[#EBF3FC] to-[#E5F5EF] text-[#1456B8]">
                  Gemini AI Companion
                </span>
              </div>
              <p className="text-[11px] text-[#1B9A72] font-semibold flex items-center gap-1">
                <span className="w-1.5 h-1.5 rounded-full bg-[#1B9A72] animate-pulse"></span>
                Always listening, always here to help
              </p>
            </div>
          </div>

          {/* Actions */}
          <div className="flex items-center gap-1">
            <button
              type="button"
              onClick={() => setShowInfoModal(true)}
              className="p-2 text-[#68758A] hover:text-[#1456B8] hover:bg-[#F2F7FF] rounded-full transition-colors cursor-pointer"
              title="About Amani"
            >
              <HelpCircle size={19} />
            </button>
            <button
              type="button"
              onClick={() => setShowClearConfirm(true)}
              className="p-2 text-[#68758A] hover:text-red-600 hover:bg-red-50 rounded-full transition-colors cursor-pointer"
              title="Reset conversation"
            >
              <Trash2 size={18} />
            </button>
          </div>
        </div>

        {/* Emergency Quick Pill */}
        <div className="mt-2.5 pt-2 border-t border-slate-100 flex items-center justify-between text-xs">
          <span className="text-[#68758A] text-[11px] flex items-center gap-1 font-medium">
            <Shield size={12} className="text-[#1B9A72]" /> 100% Anonymous & Private
          </span>
          <button
            type="button"
            onClick={() => navigation.navigate('Crisis')}
            className="text-[11px] font-extrabold text-[#C83B35] hover:underline flex items-center gap-1 cursor-pointer bg-red-50 px-2 py-0.5 rounded-full"
          >
            <PhoneCall size={11} /> Crisis 116 / 112
          </button>
        </div>
      </div>

      {/* Messages Scroll Area */}
      <div className="flex-1 overflow-y-auto px-4 py-4 space-y-4">
        {/* Peaceful Context Banner */}
        <div className="bg-gradient-to-r from-[#EBF3FC] via-[#F2F9F5] to-[#FFFFFF] p-3.5 rounded-2xl border border-[#D7E6F8] shadow-2xs">
          <div className="flex items-start gap-2.5">
            <div className="w-8 h-8 rounded-xl bg-white text-[#1456B8] flex items-center justify-center shrink-0 shadow-2xs">
              <Bot size={18} />
            </div>
            <div className="flex-1 min-w-0">
              <h2 className="text-xs font-black text-[#14213D]">
                Grounded Gemini AI Support for Ugandan Youth
              </h2>
              <p className="text-[11px] text-[#68758A] leading-relaxed mt-0.5">
                Amani responds with the deep intelligence, practical clarity, and empathy of Google's Gemini. Ask anything—from personal struggles to study pressure or coping skills.
              </p>
            </div>
          </div>
        </div>

        {/* Chat message bubbles */}
        {messages.map((msg) => {
          const isUser = msg.sender === 'user';
          const isAmani = msg.sender === 'amani';

          return (
            <motion.div
              key={msg.id}
              initial={{ opacity: 0, y: 8 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.2 }}
              className={`flex flex-col ${isUser ? 'items-end' : 'items-start'}`}
            >
              <div
                className={`max-w-[88%] sm:max-w-[82%] rounded-2xl p-3.5 text-sm leading-relaxed shadow-2xs ${
                  isUser
                    ? 'bg-[#1456B8] text-white rounded-tr-xs font-medium'
                    : 'bg-white border border-[#E3E9F2] text-[#14213D] rounded-tl-xs'
                }`}
              >
                {isAmani && (
                  <div className="flex items-center justify-between mb-2 pb-1.5 border-b border-slate-100 text-[#1456B8] font-black text-[11px]">
                    <div className="flex items-center gap-1.5">
                      <Sparkles size={13} className="text-[#1B9A72]" />
                      <span>Amani</span>
                    </div>
                    <div className="flex items-center gap-1 text-slate-400">
                      <button
                        type="button"
                        onClick={() => handleSpeak(msg.id, msg.text)}
                        className="p-1 hover:text-[#1456B8] transition-colors cursor-pointer"
                        title={speakingId === msg.id ? 'Stop reading' : 'Listen to response'}
                      >
                        {speakingId === msg.id ? (
                          <VolumeX size={14} className="text-[#1456B8]" />
                        ) : (
                          <Volume2 size={14} />
                        )}
                      </button>
                      <button
                        type="button"
                        onClick={() => handleCopy(msg.id, msg.text)}
                        className="p-1 hover:text-[#1456B8] transition-colors cursor-pointer"
                        title="Copy message"
                      >
                        {copiedId === msg.id ? (
                          <Check size={14} className="text-emerald-600" />
                        ) : (
                          <Copy size={14} />
                        )}
                      </button>
                    </div>
                  </div>
                )}

                <div className="space-y-1">
                  {isAmani ? renderRichText(msg.text) : <p className="text-xs">{msg.text}</p>}
                </div>

                <div
                  className={`text-[10px] mt-2 flex items-center justify-end ${
                    isUser ? 'text-blue-200' : 'text-[#8C98A9]'
                  }`}
                >
                  {msg.time}
                </div>
              </div>
            </motion.div>
          );
        })}

        {/* Loading Bubble */}
        {loading && (
          <motion.div
            initial={{ opacity: 0, y: 6 }}
            animate={{ opacity: 1, y: 0 }}
            className="flex items-start gap-2"
          >
            <div className="w-7 h-7 rounded-full bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] flex items-center justify-center text-white text-xs shrink-0 shadow-xs">
              <Sparkles size={14} className="animate-spin" />
            </div>
            <div className="bg-white border border-[#E3E9F2] rounded-2xl rounded-tl-xs px-4 py-3 shadow-2xs">
              <div className="flex items-center gap-1.5">
                <span className="w-2 h-2 rounded-full bg-[#1456B8] animate-bounce"></span>
                <span className="w-2 h-2 rounded-full bg-[#1B9A72] animate-bounce [animation-delay:0.2s]"></span>
                <span className="w-2 h-2 rounded-full bg-[#1456B8] animate-bounce [animation-delay:0.4s]"></span>
                <span className="text-xs text-[#68758A] ml-2 font-semibold">
                  Amani is formulating a response...
                </span>
              </div>
            </div>
          </motion.div>
        )}

        <div ref={messagesEndRef} />
      </div>

      {/* Suggested Quick Prompts */}
      {messages.length < 5 && (
        <div className="px-4 pb-2">
          <p className="text-[11px] font-black text-[#68758A] uppercase tracking-wider mb-2 flex items-center gap-1">
            <Compass size={12} /> Suggested conversations
          </p>
          <div className="flex gap-2 overflow-x-auto pb-1.5 no-scrollbar scroll-smooth">
            {QUICK_PROMPTS.map((prompt, idx) => (
              <button
                key={idx}
                type="button"
                onClick={() => handleSend(prompt)}
                disabled={loading}
                className="shrink-0 text-xs font-semibold text-[#14213D] bg-white border border-[#D7E1EE] hover:border-[#1456B8] hover:bg-[#F2F7FF] rounded-full px-3.5 py-1.5 transition-all shadow-2xs cursor-pointer active:scale-95 disabled:opacity-50"
              >
                {prompt}
              </button>
            ))}
          </div>
        </div>
      )}

      {/* Input Bar */}
      <div
        className={`bg-white border-t border-[#E3E9F2] p-3 sticky bottom-0 z-20 ${
          isEmbeddedTab ? 'mb-0' : ''
        }`}
      >
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleSend();
          }}
          className="flex items-center gap-2"
        >
          <input
            ref={inputRef}
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Ask Amani anything or share your thoughts..."
            disabled={loading}
            className="flex-1 bg-[#F7F9FC] border border-[#D7E1EE] focus:border-[#1456B8] focus:bg-white rounded-full px-4 py-2.5 text-sm text-[#14213D] outline-none transition-all placeholder:text-[#8C98A9] font-medium shadow-2xs"
          />
          <button
            type="submit"
            disabled={!input.trim() || loading}
            className="w-10 h-10 rounded-full bg-[#1456B8] hover:bg-[#0D3E83] disabled:opacity-40 disabled:hover:bg-[#1456B8] text-white flex items-center justify-center shrink-0 shadow-sm transition-all cursor-pointer active:scale-95"
            title="Send message"
          >
            <Send size={18} />
          </button>
        </form>

        <div className="flex items-center justify-between mt-2 px-1 text-[10px] text-[#8C98A9]">
          <span>Amani is an empathetic AI guide powered by Gemini</span>
          <button
            type="button"
            onClick={() => navigation.navigate('Counsellors')}
            className="text-[#1456B8] font-bold hover:underline cursor-pointer"
          >
            Book Human Counsellor
          </button>
        </div>
      </div>

      {/* Confirmation Modal to Clear Chat */}
      <AnimatePresence>
        {showClearConfirm && (
          <div className="fixed inset-0 bg-black/40 backdrop-blur-xs z-50 flex items-center justify-center p-4">
            <motion.div
              initial={{ scale: 0.95, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.95, opacity: 0 }}
              className="bg-white rounded-2xl p-5 max-w-xs w-full shadow-xl border border-slate-200"
            >
              <h3 className="text-base font-black text-[#14213D] mb-1">
                Reset Amani's conversation?
              </h3>
              <p className="text-xs text-[#68758A] leading-relaxed mb-4">
                This will clear the current chat thread on your device and start a fresh session with Amani.
              </p>
              <div className="flex gap-2">
                <button
                  type="button"
                  onClick={() => setShowClearConfirm(false)}
                  className="flex-1 py-2 rounded-xl text-xs font-black text-[#68758A] bg-slate-100 hover:bg-slate-200 transition-colors cursor-pointer"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  onClick={handleClearChat}
                  className="flex-1 py-2 rounded-xl text-xs font-black text-white bg-[#C83B35] hover:bg-red-700 transition-colors cursor-pointer shadow-xs"
                >
                  Clear
                </button>
              </div>
            </motion.div>
          </div>
        )}
      </AnimatePresence>

      {/* Info Modal About Amani */}
      <AnimatePresence>
        {showInfoModal && (
          <div className="fixed inset-0 bg-black/40 backdrop-blur-xs z-50 flex items-center justify-center p-4">
            <motion.div
              initial={{ scale: 0.95, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.95, opacity: 0 }}
              className="bg-white rounded-2xl p-5 max-w-sm w-full shadow-xl border border-slate-200"
            >
              <div className="flex items-center justify-between pb-3 border-b border-slate-100 mb-3">
                <div className="flex items-center gap-2">
                  <div className="w-8 h-8 rounded-full bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] flex items-center justify-center text-white">
                    <Sparkles size={16} />
                  </div>
                  <div>
                    <h3 className="text-sm font-black text-[#14213D]">
                      About Amani
                    </h3>
                    <p className="text-[10px] text-[#1B9A72] font-extrabold uppercase">
                      Swahili for Peace & Harmony
                    </p>
                  </div>
                </div>
                <button
                  type="button"
                  onClick={() => setShowInfoModal(false)}
                  className="p-1 text-slate-400 hover:text-slate-700 rounded-full cursor-pointer"
                >
                  <X size={18} />
                </button>
              </div>

              <div className="space-y-2.5 text-xs text-[#526077] leading-relaxed">
                <p>
                  <strong>Amani</strong> is MindBridge Uganda’s intelligent wellbeing companion powered by Google Gemini.
                </p>
                <ul className="list-disc ml-4 space-y-1">
                  <li>Formulated to respond with structured, deep, and empathetic answers</li>
                  <li>Guides you through step-by-step calming and anxiety techniques</li>
                  <li>Understands Ugandan youth cultural contexts and expressions</li>
                  <li>Directly connected to MindBridge’s verified counsellors and emergency lines</li>
                </ul>
                <div className="bg-[#F2F7FF] p-2.5 rounded-xl border border-[#D7E6F8] text-[11px] text-[#1456B8]">
                  <strong>Privacy First:</strong> Your chat messages are kept private on your device.
                </div>
              </div>

              <button
                type="button"
                onClick={() => setShowInfoModal(false)}
                className="mt-4 w-full py-2.5 bg-[#1456B8] hover:bg-[#0D3E83] text-white text-xs font-black rounded-xl transition-colors cursor-pointer shadow-xs"
              >
                Continue Chatting
              </button>
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </div>
  );
}
