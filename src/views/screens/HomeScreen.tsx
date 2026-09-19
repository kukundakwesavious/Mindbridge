import React from 'react';
import { Bell, ChevronRight, ShieldCheck, ArrowRight, Sparkles, MessageSquareHeart } from 'lucide-react';
import { Card, IconBox, Logo, PrimaryButton, Screen, SmartImage } from '../components/UI';
import { media } from '../../data/media';
import { useHomeViewModel } from '../../viewmodels/useHomeViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { ScreenName, Session } from '../../types';

interface HomeScreenProps {
  session: Session | null | undefined;
}

export default function HomeScreen({ session }: HomeScreenProps) {
  const navigation = useNavigation();
  const vm = useHomeViewModel(session);

  const actions: [string, string, string, ScreenName][] = [
    ['chatbubble-ellipses', 'Talk to a Counsellor', 'blue', 'Counsellors'],
    ['people-outline', 'Peer Support', 'green', 'PeerSupport'],
    ['book-outline', 'Psychoeducation', 'purple', 'Library'],
    ['git-branch-outline', 'Referrals', 'teal', 'Referrals'],
  ];

  return (
    <Screen>
      {/* Top Header */}
      <div className="flex items-center justify-between mb-4">
        <Logo small />
        <button
          type="button"
          onClick={() => navigation.navigate('Crisis')}
          className="w-10 h-10 rounded-full flex items-center justify-center bg-white border border-[#E3E9F2] text-[#C83B35] hover:bg-red-50 active:scale-95 transition-all shadow-xs cursor-pointer"
          title="Emergency support"
        >
          <Bell size={20} />
        </button>
      </div>

      {/* Greeting */}
      <div className="mb-4">
        <h1 className="text-2xl sm:text-[27px] font-black text-[#14213D] tracking-tight">
          Hello, {vm.greeting}
        </h1>
        <p className="text-sm text-[#68758A] mt-1 font-medium">
          Take a small step today. Your wellbeing matters.
        </p>
      </div>

      {/* Amani AI Wellbeing Companion Banner */}
      <div
        onClick={() => navigation.navigate('AmaniChat')}
        className="mb-3.5 bg-gradient-to-r from-[#1456B8] via-[#104899] to-[#1B9A72] text-white rounded-2xl p-4 shadow-sm hover:shadow-md transition-all cursor-pointer group active:scale-[0.99] relative overflow-hidden"
      >
        <div className="absolute -right-6 -bottom-6 w-28 h-28 rounded-full bg-white/10 blur-xl pointer-events-none"></div>
        <div className="flex items-center justify-between relative z-10">
          <div className="flex items-center gap-3.5">
            <div className="w-12 h-12 rounded-2xl bg-white/15 backdrop-blur-xs border border-white/20 flex items-center justify-center text-white shrink-0 group-hover:scale-105 transition-transform">
              <Sparkles size={24} className="animate-pulse" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h2 className="text-base font-black tracking-tight text-white">
                  Chat with Amani
                </h2>
                <span className="text-[10px] font-black uppercase tracking-wider bg-white/25 px-2 py-0.5 rounded-full text-white">
                  24/7 AI Support
                </span>
              </div>
              <p className="text-xs text-white/85 mt-0.5 font-medium leading-tight">
                Ask questions, find calming tools & get immediate help anytime.
              </p>
            </div>
          </div>
          <div className="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center shrink-0 group-hover:translate-x-0.5 transition-transform">
            <ChevronRight size={18} className="text-white" />
          </div>
        </div>
      </div>

      {/* Hero Banner */}
      <div className="relative rounded-2xl overflow-hidden h-48 bg-[#0D3E83] mb-3 shadow-sm">
        <SmartImage
          source={media.heroTherapy}
          fallback="/assets/mental-illustration.png"
          className="w-full h-full object-cover opacity-75"
          alt="MindBridge therapy session"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-[#0C1B33]/90 via-[#0C1B33]/40 to-transparent flex flex-col justify-end p-4">
          <h2 className="text-lg font-black text-white">A safe place to be heard</h2>
          <p className="text-xs text-[#F2F7FF] mt-1 leading-relaxed max-w-[280px]">
            Private support, practical resources and trusted referrals.
          </p>
        </div>
      </div>

      {/* Privacy Notice */}
      <Card className="flex items-center gap-3">
        <IconBox icon="shield-checkmark" tone="green" size={44} />
        <div className="flex-1 min-w-0">
          <h3 className="text-sm font-black text-[#14213D]">Private by design</h3>
          <p className="text-xs text-[#68758A] mt-0.5 leading-tight">
            Use an anonymous identity and choose what you share.
          </p>
        </div>
      </Card>

      {/* Action Grid */}
      <div className="mt-2 mb-4">
        <h3 className="text-base font-black text-[#14213D] mb-3">
          How can we support you?
        </h3>
        <div className="grid grid-cols-2 gap-3">
          {actions.map(([icon, title, tone, target]) => (
            <Card
              key={title}
              onClick={() => navigation.navigate(target)}
              className="flex flex-col justify-between min-h-[140px] p-3.5 mb-0"
            >
              <div className="flex items-start justify-between">
                <IconBox icon={icon} tone={tone} size={44} />
                <ChevronRight size={18} className="text-[#68758A]" />
              </div>
              <div>
                <p className="text-sm font-extrabold text-[#14213D] leading-snug">
                  {title}
                </p>
              </div>
            </Card>
          ))}
        </div>
      </div>

      {/* Upcoming Session */}
      {vm.upcoming.length > 0 && (
        <Card className="mb-3">
          <div className="flex items-center justify-between pb-2 border-b border-slate-100">
            <h3 className="text-sm font-black text-[#14213D]">Upcoming session</h3>
            <button
              type="button"
              onClick={() => navigation.navigate('Sessions')}
              className="text-xs font-extrabold text-[#1456B8] hover:underline cursor-pointer flex items-center gap-0.5"
            >
              View all <ArrowRight size={12} />
            </button>
          </div>
          <div className="pt-2">
            <p className="text-sm font-extrabold text-[#14213D]">
              {vm.upcoming[0].counsellorName}
            </p>
            <p className="text-xs text-[#68758A] mt-0.5">
              {vm.upcoming[0].date} • {vm.upcoming[0].time} • {vm.upcoming[0].mode}
            </p>
          </div>
        </Card>
      )}

      {/* Urgent Crisis Card */}
      <Card className="bg-[#FFF0EF] border-[#F3D0CD] mb-3">
        <div className="flex items-start justify-between gap-2 mb-3">
          <div>
            <h3 className="text-[15px] font-black text-[#14213D]">
              Need urgent help?
            </h3>
            <p className="text-xs text-[#68758A] mt-1 leading-relaxed">
              For immediate danger, use the emergency support screen.
            </p>
          </div>
        </div>
        <PrimaryButton
          title="Get emergency help"
          danger
          onPress={() => navigation.navigate('Crisis')}
        />
      </Card>
    </Screen>
  );
}
