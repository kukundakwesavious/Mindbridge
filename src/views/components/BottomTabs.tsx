import React from 'react';
import { Home, Calendar, BookOpen, User, Sparkles } from 'lucide-react';
import { MainTab } from '../../types';

interface BottomTabsProps {
  activeTab: MainTab;
  onSelectTab: (tab: MainTab) => void;
}

export function BottomTabs({ activeTab, onSelectTab }: BottomTabsProps) {
  const tabs = [
    { id: 'Home' as MainTab, label: 'Home', icon: Home },
    { id: 'Sessions' as MainTab, label: 'Sessions', icon: Calendar },
    { id: 'Amani' as MainTab, label: 'Amani AI', icon: Sparkles, isAi: true },
    { id: 'Library' as MainTab, label: 'Library', icon: BookOpen },
    { id: 'Profile' as MainTab, label: 'Profile', icon: User },
  ];

  return (
    <nav
      aria-label="Android Bottom Navigation"
      className="fixed bottom-0 left-0 right-0 z-30 flex justify-center pointer-events-none"
    >
      <div className="w-full max-w-md bg-white/95 backdrop-blur-md border-t border-[#E3E9F2] shadow-[0_-4px_20px_rgba(0,0,0,0.05)] flex items-center justify-around h-[70px] pt-1.5 pb-2 px-1 pointer-events-auto">
        {tabs.map((tab) => {
          const isActive = activeTab === tab.id;
          const Icon = tab.icon;

          if (tab.isAi) {
            return (
              <button
                key={tab.id}
                type="button"
                onClick={() => onSelectTab(tab.id)}
                className="flex-1 flex flex-col items-center justify-center py-1 -mt-3 cursor-pointer group transition-transform active:scale-95"
              >
                <div
                  className={`w-11 h-11 rounded-full flex items-center justify-center transition-all shadow-md ${
                    isActive
                      ? 'bg-gradient-to-tr from-[#1456B8] to-[#1B9A72] ring-3 ring-[#1456B8]/20 scale-105'
                      : 'bg-gradient-to-tr from-[#1456B8] to-[#104899] hover:from-[#1456B8] hover:to-[#1B9A72]'
                  } text-white`}
                >
                  <Sparkles size={20} className={isActive ? 'animate-pulse' : ''} />
                </div>
                <span
                  className={`text-[10px] font-black tracking-tight mt-1 ${
                    isActive ? 'text-[#1456B8]' : 'text-[#68758A]'
                  }`}
                >
                  Amani
                </span>
              </button>
            );
          }

          return (
            <button
              key={tab.id}
              type="button"
              onClick={() => onSelectTab(tab.id)}
              className="flex-1 flex flex-col items-center justify-center py-1 gap-1 cursor-pointer transition-all active:scale-95"
            >
              <div
                className={`px-3 py-1 rounded-full transition-colors ${
                  isActive ? 'bg-[#EBF3FC]' : 'bg-transparent'
                }`}
              >
                <Icon size={20} color={isActive ? '#1456B8' : '#68758A'} />
              </div>
              <span
                className={`text-[10px] font-extrabold tracking-tight ${
                  isActive ? 'text-[#1456B8]' : 'text-[#68758A]'
                }`}
              >
                {tab.label}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
}
