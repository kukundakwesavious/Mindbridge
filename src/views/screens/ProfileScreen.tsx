import React from 'react';
import { Globe, Bell, ChevronRight, LogOut, Shield } from 'lucide-react';
import { Avatar, Card, Header, OutlineButton, Screen } from '../components/UI';
import { useProfileViewModel } from '../../viewmodels/useProfileViewModel';
import { useAuthViewModel } from '../../viewmodels/useAuthViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface ProfileScreenProps {
  session: Session;
  setSession: (s: Session | null) => void;
}

export default function ProfileScreen({ session, setSession }: ProfileScreenProps) {
  const navigation = useNavigation();
  const { update } = useProfileViewModel({
    session,
    setSession: setSession as (s: Session) => void,
  });
  const { logout } = useAuthViewModel(setSession);

  const initials = (session?.displayName || 'Friend')
    .slice(0, 2)
    .toUpperCase();

  const toggleNotifications = () => {
    update({ notifications: !session.notifications });
  };

  const handleLogout = async () => {
    await logout();
    navigation.replace('Welcome');
  };

  return (
    <Screen>
      <Header
        title="Profile & Settings"
        onBack={navigation.canGoBack() ? () => navigation.goBack() : undefined}
      />

      {/* User Card */}
      <Card className="flex items-center gap-3.5 p-4 mb-4">
        <Avatar initials={initials} size={60} />
        <div className="flex-1 min-w-0">
          <h2 className="text-base font-black text-[#14213D] truncate">
            {session?.displayName || 'Friend'}
          </h2>
          <p className="text-xs font-mono text-[#1456B8] mt-0.5">
            ID: {session?.anonymousId || 'MB-GUEST'}
          </p>
          <div className="flex items-center gap-1.5 mt-1 text-[11px] text-[#1B9A72] font-extrabold">
            <Shield size={13} />
            <span>Anonymous Profile</span>
          </div>
        </div>
      </Card>

      {/* Settings Options */}
      <div className="space-y-2.5 mb-6">
        <Card
          onClick={() => navigation.navigate('Language')}
          className="flex items-center justify-between p-4 mb-0"
        >
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-[#EAF2FF] text-[#1456B8] flex items-center justify-center">
              <Globe size={20} />
            </div>
            <div>
              <p className="text-sm font-extrabold text-[#14213D]">App Language</p>
              <p className="text-xs text-[#68758A] mt-0.5">{session.language || 'English'}</p>
            </div>
          </div>
          <ChevronRight size={18} className="text-[#68758A]" />
        </Card>

        <Card
          onClick={toggleNotifications}
          className="flex items-center justify-between p-4 mb-0"
        >
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-[#E8F7F1] text-[#1B9A72] flex items-center justify-center">
              <Bell size={20} />
            </div>
            <div>
              <p className="text-sm font-extrabold text-[#14213D]">Session reminders</p>
              <p className="text-xs text-[#68758A] mt-0.5">
                {session.notifications ? 'Enabled' : 'Disabled'}
              </p>
            </div>
          </div>
          <div
            className={`w-11 h-6 rounded-full transition-colors flex items-center p-0.5 ${
              session.notifications ? 'bg-[#1456B8]' : 'bg-slate-300'
            }`}
          >
            <div
              className={`w-5 h-5 rounded-full bg-white transition-transform ${
                session.notifications ? 'translate-x-5' : 'translate-x-0'
              }`}
            />
          </div>
        </Card>
      </div>

      {/* About Box */}
      <Card className="bg-[#F8FAFD] border-[#E3E9F2] p-4 mb-6">
        <h3 className="text-xs font-black uppercase tracking-wider text-[#14213D] mb-1">
          MindBridge Uganda v1.1.0
        </h3>
        <p className="text-xs text-[#68758A] leading-relaxed">
          Designed for confidential, accessible community care and mental health empowerment for young people across Uganda.
        </p>
      </Card>

      {/* Logout / Reset */}
      <OutlineButton
        title="Log out & clear session"
        icon="close-circle"
        danger
        onPress={handleLogout}
      />
    </Screen>
  );
}
