import React, { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Smartphone, Maximize2, Minimize2 } from 'lucide-react';
import { getStoredSession } from './services/storage';
import { Session } from './types';
import { NavigationProvider, useNavigation } from './navigation/NavigationContext';
import { AndroidStatusBar, AndroidNavBar } from './views/components/AndroidSystemBar';

// Screens
import SplashScreen from './views/screens/SplashScreen';
import WelcomeScreen from './views/screens/WelcomeScreen';
import SignUpScreen from './views/screens/SignUpScreen';
import MainTabsScreen from './views/screens/MainTabsScreen';
import CounsellorListScreen from './views/screens/CounsellorListScreen';
import CounsellorDetailScreen from './views/screens/CounsellorDetailScreen';
import BookingScreen from './views/screens/BookingScreen';
import SessionsScreen from './views/screens/SessionsScreen';
import ChatScreen from './views/screens/ChatScreen';
import SessionRoomScreen from './views/screens/SessionRoomScreen';
import LibraryScreen from './views/screens/LibraryScreen';
import ContentDetailScreen from './views/screens/ContentDetailScreen';
import ReferralsScreen from './views/screens/ReferralsScreen';
import ReferralDetailScreen from './views/screens/ReferralDetailScreen';
import CrisisScreen from './views/screens/CrisisScreen';
import PeerSupportScreen from './views/screens/PeerSupportScreen';
import ProfileScreen from './views/screens/ProfileScreen';
import LanguageScreen from './views/screens/LanguageScreen';
import AmaniChatScreen from './views/screens/AmaniChatScreen';

function ScreenRenderer({
  session,
  setSession,
}: {
  session: Session | null;
  setSession: React.Dispatch<React.SetStateAction<Session | null>>;
}) {
  const { currentScreen, replace } = useNavigation();

  // If session logged out and current screen requires session, redirect to Welcome
  useEffect(() => {
    if (!session && !['Splash', 'Welcome', 'SignUp'].includes(currentScreen)) {
      replace('Welcome');
    }
  }, [session, currentScreen, replace]);

  const renderCurrentView = () => {
    switch (currentScreen) {
      case 'Splash':
        return <SplashScreen />;
      case 'Welcome':
        return <WelcomeScreen />;
      case 'SignUp':
        return <SignUpScreen setSession={setSession} />;
      case 'Main':
        return session ? (
          <MainTabsScreen session={session} setSession={setSession} />
        ) : (
          <WelcomeScreen />
        );
      case 'AmaniChat':
        return <AmaniChatScreen session={session} />;
      case 'Counsellors':
        return <CounsellorListScreen />;
      case 'CounsellorDetail':
        return <CounsellorDetailScreen />;
      case 'Booking':
        return session ? (
          <BookingScreen session={session} setSession={(s) => setSession(s)} />
        ) : (
          <WelcomeScreen />
        );
      case 'Sessions':
        return <SessionsScreen session={session} />;
      case 'Chat':
        return session ? (
          <ChatScreen session={session} setSession={(s) => setSession(s)} />
        ) : (
          <WelcomeScreen />
        );
      case 'SessionRoom':
        return <SessionRoomScreen />;
      case 'Library':
        return <LibraryScreen />;
      case 'ContentDetail':
        return <ContentDetailScreen />;
      case 'Referrals':
        return <ReferralsScreen session={session} />;
      case 'ReferralDetail':
        return session ? (
          <ReferralDetailScreen session={session} setSession={(s) => setSession(s)} />
        ) : (
          <WelcomeScreen />
        );
      case 'Crisis':
        return <CrisisScreen />;
      case 'PeerSupport':
        return session ? (
          <PeerSupportScreen session={session} setSession={(s) => setSession(s)} />
        ) : (
          <WelcomeScreen />
        );
      case 'Profile':
        return session ? (
          <ProfileScreen session={session} setSession={setSession} />
        ) : (
          <WelcomeScreen />
        );
      case 'Language':
        return session ? (
          <LanguageScreen session={session} setSession={(s) => setSession(s)} />
        ) : (
          <WelcomeScreen />
        );
      default:
        return session ? (
          <MainTabsScreen session={session} setSession={setSession} />
        ) : (
          <WelcomeScreen />
        );
    }
  };

  return (
    <AnimatePresence mode="wait">
      <motion.div
        key={currentScreen}
        initial={{ opacity: 0, x: 20 }}
        animate={{ opacity: 1, x: 0 }}
        exit={{ opacity: 0, x: -20 }}
        transition={{ duration: 0.2, ease: [0.2, 0, 0, 1] }}
        className="w-full flex-1 flex flex-col"
      >
        {renderCurrentView()}
      </motion.div>
    </AnimatePresence>
  );
}

function AndroidMobileShell({
  session,
  setSession,
}: {
  session: Session | null;
  setSession: React.Dispatch<React.SetStateAction<Session | null>>;
}) {
  const { goBack, canGoBack } = useNavigation();
  const [navType, setNavType] = useState<'gesture' | 'buttons'>('gesture');
  const [isFramed, setIsFramed] = useState(false);

  // Listen for physical Android back key / keyboard Esc
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape' && canGoBack()) {
        goBack();
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [canGoBack, goBack]);

  return (
    <div
      className={`min-h-screen w-full flex flex-col items-center justify-center transition-colors ${
        isFramed ? 'bg-slate-900 py-6 px-3' : 'bg-[#F7F9FC]'
      }`}
    >
      {/* Desktop Device Mode Switcher bar */}
      <div className="hidden lg:flex items-center gap-3 mb-3 text-xs font-bold text-slate-400">
        <span className="flex items-center gap-1.5 text-slate-300">
          <Smartphone size={14} className="text-[#1456B8]" />
          MindBridge Android Edition
        </span>
        <span className="text-slate-600">•</span>
        <button
          type="button"
          onClick={() => setIsFramed(!isFramed)}
          className="flex items-center gap-1.5 px-2.5 py-1 rounded-md bg-white/10 hover:bg-white/20 text-slate-200 transition-colors cursor-pointer"
          title="Toggle Android Device Frame"
        >
          {isFramed ? <Minimize2 size={13} /> : <Maximize2 size={13} />}
          <span>{isFramed ? 'Full View' : 'Phone Frame'}</span>
        </button>
        <span className="text-slate-600">•</span>
        <button
          type="button"
          onClick={() => setNavType(navType === 'gesture' ? 'buttons' : 'gesture')}
          className="px-2.5 py-1 rounded-md bg-white/10 hover:bg-white/20 text-slate-200 transition-colors cursor-pointer"
        >
          Nav: {navType === 'gesture' ? 'Gesture Bar' : '3-Button Bar'}
        </button>
      </div>

      {/* Android Device Container */}
      <div
        className={`w-full max-w-md flex flex-col bg-[#F7F9FC] relative overflow-hidden transition-all duration-300 ${
          isFramed
            ? 'rounded-[44px] ring-[12px] ring-slate-800 shadow-[0_25px_60px_-15px_rgba(0,0,0,0.7)] border-4 border-slate-700 min-h-[844px] max-h-[92vh]'
            : 'min-h-screen'
        }`}
      >
        {/* Android Status Bar */}
        <div className="sticky top-0 z-40 bg-white/90 backdrop-blur-md border-b border-slate-100">
          <AndroidStatusBar />
        </div>

        {/* Screen Content */}
        <div className="flex-1 flex flex-col overflow-y-auto">
          <ScreenRenderer session={session} setSession={setSession} />
        </div>

        {/* Android Navigation Bar */}
        <div className="sticky bottom-0 z-40 bg-white/90 backdrop-blur-md border-t border-slate-100">
          <AndroidNavBar
            navType={navType}
            onToggleNavType={() =>
              setNavType(navType === 'gesture' ? 'buttons' : 'gesture')
            }
          />
        </div>
      </div>
    </div>
  );
}

export default function App() {
  const [session, setSession] = useState<Session | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function load() {
      try {
        const stored = await getStoredSession();
        setSession(stored);
      } catch (e) {
        console.error('Session load error', e);
      } finally {
        setLoading(false);
      }
    }
    load();
  }, []);

  if (loading) {
    return (
      <div className="min-h-screen w-full bg-[#F7F9FC] flex flex-col items-center justify-center">
        <div className="flex flex-col items-center gap-3">
          <img
            src="/assets/app-icon.png"
            alt="MindBridge"
            className="w-16 h-16 rounded-2xl animate-pulse shadow-md"
          />
          <p className="text-xs font-bold text-[#68758A]">Loading MindBridge Uganda...</p>
        </div>
      </div>
    );
  }

  // If already logged in, start directly on Main; otherwise start on Splash
  const initialScreen = session?.onboardingComplete ? 'Main' : 'Splash';

  return (
    <NavigationProvider initialScreen={initialScreen}>
      <AndroidMobileShell session={session} setSession={setSession} />
    </NavigationProvider>
  );
}
