import React, { useEffect, useState } from 'react';
import { Video, Volume2, Mic, MicOff, MessageCircle, Phone, VideoOff } from 'lucide-react';
import { Header, PrimaryButton, Screen, SmartImage } from '../components/UI';
import { media } from '../../data/media';
import { useNavigation } from '../../navigation/NavigationContext';
import { Counsellor } from '../../types';

export default function SessionRoomScreen() {
  const navigation = useNavigation();
  const counsellor = navigation.params?.counsellor as Counsellor;
  const mode = (navigation.params?.mode as string) || 'Video call';

  const [seconds, setSeconds] = useState(0);
  const [muted, setMuted] = useState(false);
  const [videoOff, setVideoOff] = useState(false);

  useEffect(() => {
    const timer = setInterval(() => setSeconds((v) => v + 1), 1000);
    return () => clearInterval(timer);
  }, []);

  const mins = String(Math.floor(seconds / 60)).padStart(2, '0');
  const secs = String(seconds % 60).padStart(2, '0');

  if (!counsellor) {
    return (
      <Screen>
        <Header title={mode} onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">No active session found.</p>
      </Screen>
    );
  }

  return (
    <Screen scroll={false} contentClassName="p-4 justify-between">
      <div>
        <Header
          title={mode}
          subtitle={counsellor.name}
          onBack={() => navigation.goBack()}
        />

        {/* Video / Call Stage */}
        <div className="relative h-80 sm:h-96 rounded-3xl overflow-hidden bg-[#0C1B33] shadow-md mb-6">
          <SmartImage
            source={counsellor.image || media.therapySession}
            fallback="/assets/crisis-illustration.png"
            className={`w-full h-full object-cover transition-opacity duration-300 ${
              videoOff && mode === 'Video call' ? 'opacity-15' : 'opacity-85'
            }`}
            alt="Counsellor session video stream"
          />

          {videoOff && mode === 'Video call' && (
            <div className="absolute inset-0 flex items-center justify-center text-white/70">
              <p className="text-sm font-bold">Camera turned off</p>
            </div>
          )}

          <div className="absolute inset-0 bg-gradient-to-t from-[#0C1B33]/90 via-transparent to-[#0C1B33]/30 p-5 flex flex-col justify-between">
            <div>
              <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-lg bg-[#C83B35] text-white text-[10px] font-black tracking-wider">
                <span className="w-2 h-2 rounded-full bg-white animate-pulse" />
                {mode === 'Video call' ? 'LIVE VIDEO' : 'LIVE AUDIO'}
              </span>
            </div>

            <div>
              <p className="text-3xl sm:text-4xl font-black text-white tracking-tight font-mono">
                {mins}:{secs}
              </p>
              <p className="text-sm font-bold text-slate-200 mt-1">
                {counsellor.name}
              </p>
            </div>
          </div>
        </div>

        {/* Action Controls */}
        <div className="flex items-center justify-around py-2 mb-6">
          {mode === 'Video call' ? (
            <div className="flex flex-col items-center gap-1.5">
              <button
                type="button"
                onClick={() => setVideoOff((v) => !v)}
                className={`w-14 h-14 rounded-full flex items-center justify-center border shadow-xs transition-all cursor-pointer ${
                  videoOff
                    ? 'bg-slate-200 border-slate-300 text-slate-700'
                    : 'bg-white border-[#E3E9F2] text-[#14213D] hover:bg-slate-50'
                }`}
              >
                {videoOff ? <VideoOff size={22} /> : <Video size={22} />}
              </button>
              <span className="text-[11px] font-bold text-[#68758A]">
                {videoOff ? 'Start Video' : 'Stop Video'}
              </span>
            </div>
          ) : (
            <div className="flex flex-col items-center gap-1.5">
              <button
                type="button"
                className="w-14 h-14 rounded-full bg-white border border-[#E3E9F2] text-[#14213D] flex items-center justify-center shadow-xs cursor-pointer"
              >
                <Volume2 size={22} />
              </button>
              <span className="text-[11px] font-bold text-[#68758A]">Speaker</span>
            </div>
          )}

          <div className="flex flex-col items-center gap-1.5">
            <button
              type="button"
              onClick={() => setMuted((m) => !m)}
              className={`w-14 h-14 rounded-full flex items-center justify-center border shadow-xs transition-all cursor-pointer ${
                muted
                  ? 'bg-red-50 border-red-200 text-[#C83B35]'
                  : 'bg-white border-[#E3E9F2] text-[#14213D] hover:bg-slate-50'
              }`}
            >
              {muted ? <MicOff size={22} /> : <Mic size={22} />}
            </button>
            <span className="text-[11px] font-bold text-[#68758A]">
              {muted ? 'Unmute' : 'Mute'}
            </span>
          </div>

          <div className="flex flex-col items-center gap-1.5">
            <button
              type="button"
              onClick={() => navigation.navigate('Chat', { counsellor })}
              className="w-14 h-14 rounded-full bg-white border border-[#E3E9F2] text-[#14213D] flex items-center justify-center shadow-xs hover:bg-slate-50 cursor-pointer"
            >
              <MessageCircle size={22} />
            </button>
            <span className="text-[11px] font-bold text-[#68758A]">Chat</span>
          </div>
        </div>
      </div>

      <div className="space-y-3">
        <PrimaryButton
          title="End session"
          danger
          icon="call"
          onPress={() => navigation.goBack()}
        />
        <p className="text-[10px] leading-relaxed text-[#68758A] text-center px-2">
          Demo session room. A production release should connect this surface to an authenticated, encrypted voice/video provider.
        </p>
      </div>
    </Screen>
  );
}
