import React from 'react';
import { ShieldCheck, Wifi, Users, ArrowRight } from 'lucide-react';
import { Logo, PrimaryButton, Screen, SmartImage } from '../components/UI';
import { media } from '../../data/media';
import { useNavigation } from '../../navigation/NavigationContext';

export default function WelcomeScreen() {
  const navigation = useNavigation();

  return (
    <Screen contentClassName="pt-6 justify-between">
      <div>
        <div className="mb-4">
          <Logo />
        </div>

        <div className="w-full h-56 sm:h-64 rounded-3xl overflow-hidden my-4 shadow-sm">
          <SmartImage
            source={media.phoneUser}
            fallback="/assets/welcome-illustration.png"
            className="w-full h-full object-cover"
            alt="Young person on phone"
          />
        </div>

        <h1 className="text-3xl font-black text-[#14213D] tracking-tight mt-3">
          You are not alone.
        </h1>
        <p className="text-[15px] leading-relaxed text-[#68758A] mt-2 mb-6">
          Connect with verified professionals, trusted community support and practical wellbeing resources—privately and at your own pace.
        </p>

        <div className="space-y-3 mb-8">
          <div className="flex items-center gap-3 text-sm font-bold text-[#14213D]">
            <div className="w-8 h-8 rounded-full bg-[#EAF2FF] flex items-center justify-center text-[#1456B8]">
              <ShieldCheck size={18} />
            </div>
            <span>Privacy first</span>
          </div>

          <div className="flex items-center gap-3 text-sm font-bold text-[#14213D]">
            <div className="w-8 h-8 rounded-full bg-[#EAF2FF] flex items-center justify-center text-[#1456B8]">
              <Wifi size={18} />
            </div>
            <span>Designed for low bandwidth</span>
          </div>

          <div className="flex items-center gap-3 text-sm font-bold text-[#14213D]">
            <div className="w-8 h-8 rounded-full bg-[#EAF2FF] flex items-center justify-center text-[#1456B8]">
              <Users size={18} />
            </div>
            <span>Built for communities</span>
          </div>
        </div>
      </div>

      <div className="pt-2">
        <PrimaryButton
          title="Get started"
          icon="arrow-forward"
          onPress={() => navigation.navigate('SignUp')}
        />
      </div>
    </Screen>
  );
}
