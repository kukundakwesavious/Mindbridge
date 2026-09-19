import React, { useState } from 'react';
import { Star, CheckCircle2, Phone, AlertCircle } from 'lucide-react';
import { Avatar, Card, Header, IconBox, PrimaryButton, Screen } from '../components/UI';
import { useReferralViewModel } from '../../viewmodels/useReferralViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { ReferralType, Session } from '../../types';

interface ReferralDetailScreenProps {
  session: Session;
  setSession: (s: Session) => void;
}

export default function ReferralDetailScreen({ session, setSession }: ReferralDetailScreenProps) {
  const navigation = useNavigation();
  const item = navigation.params?.type as ReferralType;
  const vm = useReferralViewModel({ session, setSession });
  const [submitted, setSubmitted] = useState(false);

  if (!item) {
    return (
      <Screen>
        <Header title="Referral Pathway" onBack={() => navigation.goBack()} />
        <p className="text-sm text-[#68758A]">No referral pathway selected.</p>
      </Screen>
    );
  }

  const isFaith = item.id === 'faith';
  const isEmergency = item.id === 'emergency';
  const faithLeader = vm.faithLeaders[0];

  const handleRequest = async () => {
    await vm.createReferral(item.title, isFaith ? faithLeader : null);
    setSubmitted(true);
  };

  return (
    <Screen>
      <Header
        title={item.title}
        subtitle={item.subtitle}
        onBack={() => navigation.goBack()}
      />

      <div className="flex items-center gap-3 mb-4">
        <IconBox icon={item.icon} tone={item.tone} size={48} />
        <div>
          <h1 className="text-lg font-black text-[#14213D]">{item.title}</h1>
          <p className="text-xs text-[#68758A]">{item.subtitle}</p>
        </div>
      </div>

      {submitted ? (
        <div className="bg-white rounded-2xl p-6 border border-[#E3E9F2] text-center shadow-lg animate-fade-in my-6">
          <div className="w-16 h-16 rounded-full bg-[#E8F7F1] text-[#1B9A72] flex items-center justify-center mx-auto mb-4">
            <CheckCircle2 size={36} />
          </div>
          <h2 className="text-xl font-black text-[#14213D]">Referral Requested</h2>
          <p className="text-xs text-[#68758A] mt-2 mb-6 leading-relaxed">
            Your request for <span className="font-bold text-[#14213D]">{isFaith ? faithLeader.name : item.title}</span> has been logged securely. A coordinator will facilitate the confidential link.
          </p>
          <PrimaryButton
            title="Back to referrals"
            onPress={() => navigation.goBack()}
          />
        </div>
      ) : isEmergency ? (
        <div className="space-y-4">
          <Card className="bg-[#FFF0EF] border-[#F3D0CD] p-5">
            <div className="flex items-start gap-3">
              <div className="w-10 h-10 rounded-full bg-red-100 text-[#C83B35] flex items-center justify-center flex-shrink-0">
                <AlertCircle size={24} />
              </div>
              <div>
                <h2 className="text-sm font-black text-[#14213D]">
                  Urgent & Crisis Support
                </h2>
                <p className="text-xs text-[#68758A] mt-1 leading-relaxed">
                  If you or someone around you is in immediate physical danger or severe distress, contact national emergency services immediately.
                </p>
              </div>
            </div>
          </Card>

          <a
            href="tel:112"
            className="w-full min-h-[52px] rounded-[15px] bg-[#C83B35] hover:bg-[#b0322d] text-white flex items-center justify-center gap-2 font-extrabold text-[15px] shadow-sm shadow-red-500/20 cursor-pointer"
          >
            <Phone size={18} />
            Call Emergency (112)
          </a>

          <button
            type="button"
            onClick={() => navigation.navigate('Crisis')}
            className="w-full min-h-[50px] rounded-[15px] border border-[#1456B8] text-[#1456B8] hover:bg-[#EAF2FF] flex items-center justify-center font-extrabold text-sm cursor-pointer"
          >
            View all emergency contacts & hotlines
          </button>
        </div>
      ) : (
        <div className="space-y-4">
          <Card className="p-4">
            <h2 className="text-xs font-black uppercase tracking-wider text-[#14213D] mb-1">
              How this works
            </h2>
            <p className="text-xs text-[#68758A] leading-relaxed">
              MindBridge maintains formal connections with certified community organizations and clinical centers. Requests do not require disclosing sensitive medical history.
            </p>
          </Card>

          {isFaith && faithLeader && (
            <Card className="p-4">
              <div className="flex items-center gap-3">
                <Avatar initials={faithLeader.avatar} size={54} />
                <div className="flex-1 min-w-0">
                  <h3 className="text-sm font-black text-[#14213D]">
                    {faithLeader.name}
                  </h3>
                  <p className="text-xs text-[#68758A] mt-0.5">
                    {faithLeader.role} • {faithLeader.area}
                  </p>
                  <div className="flex items-center gap-1 mt-1">
                    <Star size={12} className="text-[#D99B00] fill-[#D99B00]" />
                    <span className="text-xs font-bold text-[#14213D]">
                      {faithLeader.rating}
                    </span>
                    <span className="text-[11px] text-[#68758A]">
                      ({faithLeader.reviews} reviews)
                    </span>
                  </div>
                </div>
              </div>
            </Card>
          )}

          <PrimaryButton
            title={isFaith ? `Request referral to ${faithLeader?.name}` : `Submit ${item.title} request`}
            icon="shield-checkmark"
            loading={vm.saving}
            onPress={handleRequest}
          />
        </div>
      )}
    </Screen>
  );
}
