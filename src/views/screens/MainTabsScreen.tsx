import React from 'react';
import { Sparkles } from 'lucide-react';
import HomeScreen from './HomeScreen';
import SessionsScreen from './SessionsScreen';
import LibraryScreen from './LibraryScreen';
import ReferralsScreen from './ReferralsScreen';
import ProfileScreen from './ProfileScreen';
import AmaniChatScreen from './AmaniChatScreen';
import { BottomTabs } from '../components/BottomTabs';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface MainTabsScreenProps {
  session: Session;
  setSession: (s: Session | null) => void;
}

export default function MainTabsScreen({ session, setSession }: MainTabsScreenProps) {
  const { activeTab, setActiveTab, navigate } = useNavigation();

  return (
    <div className="relative min-h-screen w-full flex flex-col items-center">
      <div className="w-full flex-1 flex flex-col">
        {activeTab === 'Home' && <HomeScreen session={session} />}
        {activeTab === 'Sessions' && <SessionsScreen session={session} />}
        {activeTab === 'Amani' && <AmaniChatScreen session={session} isEmbeddedTab={true} />}
        {activeTab === 'Library' && <LibraryScreen />}
        {activeTab === 'Referrals' && <ReferralsScreen session={session} />}
        {activeTab === 'Profile' && (
          <ProfileScreen session={session} setSession={setSession} />
        )}
      </div>

      {/* Floating Amani Chat FAB (only shown when not already on Amani tab) */}
      {activeTab !== 'Amani' && (
        <div className="fixed bottom-[84px] right-4 z-40 max-w-md w-full pointer-events-none flex justify-end px-4">
          <button
            type="button"
            onClick={() => setActiveTab('Amani')}
            className="pointer-events-auto flex items-center gap-2 bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] text-white px-3.5 py-2.5 rounded-full shadow-lg hover:shadow-xl hover:scale-105 active:scale-95 transition-all cursor-pointer border border-white/20 group"
            title="Chat with Amani"
          >
            <div className="relative">
              <Sparkles size={18} className="animate-pulse" />
              <span className="absolute -top-1 -right-1 w-2 h-2 bg-emerald-300 rounded-full"></span>
            </div>
            <span className="text-xs font-black tracking-tight pr-0.5">
              Amani AI
            </span>
          </button>
        </div>
      )}

      <BottomTabs activeTab={activeTab} onSelectTab={setActiveTab} />
    </div>
  );
}
