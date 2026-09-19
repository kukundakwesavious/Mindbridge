import React, { useEffect } from 'react';
import { useNavigation } from '../../navigation/NavigationContext';

export default function SplashScreen() {
  const navigation = useNavigation();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigation.replace('Welcome');
    }, 2000);
    return () => clearTimeout(timer);
  }, [navigation]);

  return (
    <div
      onClick={() => navigation.replace('Welcome')}
      className="min-h-screen w-full flex flex-col items-center justify-center p-8 relative cursor-pointer select-none bg-gradient-to-b from-[#F4F9FF] via-[#EAF3FF] to-white"
    >
      <div className="flex flex-col items-center text-center animate-fade-in">
        <img
          src="/assets/app-icon.png"
          alt="MindBridge Icon"
          className="w-28 h-28 rounded-[30px] shadow-lg shadow-blue-500/15 mb-6 object-cover"
        />
        <h1 className="text-4xl font-black text-[#1456B8] tracking-tight">
          Mind<span className="text-[#1B9A72]">Bridge</span>
        </h1>
        <p className="text-sm font-semibold text-[#68758A] mt-2">
          Better minds. Stronger communities.
        </p>
      </div>

      <div className="absolute bottom-10 text-xs font-medium text-[#68758A] text-center px-4">
        Mental wellbeing support for Ugandan youth
      </div>
    </div>
  );
}
