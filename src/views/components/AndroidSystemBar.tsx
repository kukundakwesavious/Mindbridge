import React, { useState, useEffect } from 'react';
import {
  Wifi,
  Signal,
  Battery,
  ChevronLeft,
  Circle,
  Square,
  Smartphone,
  Maximize2,
  Minimize2,
  Bell,
  Sparkles
} from 'lucide-react';
import { useNavigation } from '../../navigation/NavigationContext';

interface AndroidSystemBarProps {
  theme?: 'light' | 'dark';
}

export function AndroidStatusBar({ theme = 'light' }: AndroidSystemBarProps) {
  const [time, setTime] = useState('');

  useEffect(() => {
    const updateTime = () => {
      const now = new Date();
      setTime(
        now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false })
      );
    };
    updateTime();
    const interval = setInterval(updateTime, 30000);
    return () => clearInterval(interval);
  }, []);

  const textColor = theme === 'dark' ? 'text-white' : 'text-[#14213D]';

  return (
    <div
      className={`w-full px-5 py-1.5 flex items-center justify-between text-[11px] font-bold tracking-tight select-none z-50 ${textColor}`}
    >
      {/* Left: Time and app notification icon */}
      <div className="flex items-center gap-2">
        <span className="font-black text-xs">{time || '10:45'}</span>
        <div className="flex items-center gap-1 opacity-75">
          <div className="w-1.5 h-1.5 rounded-full bg-[#1456B8]"></div>
          <Bell size={10} className="text-[#1456B8]" />
        </div>
      </div>

      {/* Center: Camera Punch Hole on Phone Frame */}
      <div className="hidden sm:flex items-center justify-center">
        <div className="w-3.5 h-3.5 rounded-full bg-black/80 ring-1 ring-slate-800 flex items-center justify-center">
          <div className="w-1 h-1 rounded-full bg-blue-900/60"></div>
        </div>
      </div>

      {/* Right: Connectivity and Battery */}
      <div className="flex items-center gap-1.5 text-xs">
        <span className="text-[10px] font-extrabold text-[#1456B8]">5G</span>
        <Signal size={12} strokeWidth={2.5} />
        <Wifi size={13} strokeWidth={2.5} />
        <div className="flex items-center gap-1">
          <span className="text-[10px] font-black">94%</span>
          <Battery size={15} strokeWidth={2.2} className="fill-current" />
        </div>
      </div>
    </div>
  );
}

interface AndroidNavBarProps {
  navType?: 'gesture' | 'buttons';
  onToggleNavType?: () => void;
}

export function AndroidNavBar({
  navType = 'gesture',
  onToggleNavType,
}: AndroidNavBarProps) {
  const { goBack, canGoBack, navigate, setActiveTab } = useNavigation();

  if (navType === 'buttons') {
    return (
      <div className="w-full h-11 bg-black/95 text-slate-300 flex items-center justify-around px-8 select-none z-50">
        {/* Back Button */}
        <button
          type="button"
          onClick={() => {
            if (canGoBack()) goBack();
          }}
          className="p-2 hover:text-white active:scale-90 transition-transform cursor-pointer"
          title="Android Back"
        >
          <ChevronLeft size={20} />
        </button>

        {/* Home Button */}
        <button
          type="button"
          onClick={() => {
            navigate('Main');
            setActiveTab('Home');
          }}
          className="p-2 hover:text-white active:scale-90 transition-transform cursor-pointer"
          title="Android Home"
        >
          <Circle size={16} strokeWidth={2.5} />
        </button>

        {/* Recents Button */}
        <button
          type="button"
          onClick={() => {
            if (onToggleNavType) onToggleNavType();
          }}
          className="p-2 hover:text-white active:scale-90 transition-transform cursor-pointer"
          title="Android Recents / Switch Nav"
        >
          <Square size={15} strokeWidth={2.5} />
        </button>
      </div>
    );
  }

  // Modern Gesture Bar
  return (
    <div
      onClick={onToggleNavType}
      className="w-full h-5 flex items-center justify-center cursor-pointer select-none z-50 bg-transparent hover:bg-black/5 transition-colors"
      title="Tap to switch to 3-button navigation"
    >
      <div className="w-32 h-1 bg-[#14213D]/40 hover:bg-[#14213D]/70 rounded-full transition-colors"></div>
    </div>
  );
}
