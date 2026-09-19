import React from 'react';
import { Phone, ShieldAlert, HeartHandshake } from 'lucide-react';
import { Card, Header, PrimaryButton, Screen, SmartImage } from '../components/UI';
import { useNavigation } from '../../navigation/NavigationContext';

export default function CrisisScreen() {
  const navigation = useNavigation();

  const hotlines = [
    { name: 'National Emergency Toll-Free', number: '112', desc: 'Police, Ambulance & Fire Services' },
    { name: 'National Police Control', number: '999', desc: 'Direct emergency police dispatch' },
    { name: 'National Mental Health Helpline', number: '0800100066', display: '0800 100 066', desc: 'Free confidential psychosocial support' },
    { name: 'Sauti Child & Youth Helpline', number: '116', desc: 'Free helpline for young people & children' },
  ];

  return (
    <Screen>
      <Header
        title="Emergency Help"
        subtitle="Immediate crisis support"
        onBack={() => navigation.goBack()}
      />

      <div className="h-40 rounded-2xl overflow-hidden mb-4 border border-[#F3D0CD]">
        <SmartImage
          source="/assets/crisis-illustration.png"
          fallback="/assets/mental-illustration.png"
          className="w-full h-full object-cover"
          alt="Crisis support illustration"
        />
      </div>

      <Card className="bg-[#FFF0EF] border-[#F3D0CD] p-4 mb-4">
        <div className="flex items-start gap-3">
          <div className="w-10 h-10 rounded-full bg-red-100 text-[#C83B35] flex items-center justify-center flex-shrink-0">
            <ShieldAlert size={22} />
          </div>
          <div>
            <h2 className="text-sm font-black text-[#14213D]">
              Are you or someone else in danger?
            </h2>
            <p className="text-xs text-[#68758A] mt-1 leading-relaxed">
              MindBridge is not an emergency response service. If you are experiencing thoughts of self-harm, medical urgency or violence, call immediately.
            </p>
          </div>
        </div>
      </Card>

      <div className="mb-4">
        <h2 className="text-sm font-black text-[#14213D] mb-2.5">
          Uganda Emergency Lines
        </h2>
        <div className="space-y-2.5">
          {hotlines.map((h) => (
            <div
              key={h.number}
              className="bg-white rounded-2xl p-3.5 border border-[#E3E9F2] flex items-center justify-between shadow-2xs"
            >
              <div className="flex-1 min-w-0 pr-2">
                <p className="text-xs font-black text-[#14213D] truncate">{h.name}</p>
                <p className="text-[11px] text-[#68758A]">{h.desc}</p>
                <p className="text-sm font-black text-[#C83B35] mt-1 font-mono">{h.display || h.number}</p>
              </div>
              <a
                href={`tel:${h.number}`}
                className="w-11 h-11 rounded-xl bg-[#FFF0EF] text-[#C83B35] hover:bg-red-100 active:scale-95 flex items-center justify-center flex-shrink-0 transition-colors cursor-pointer"
                title={`Call ${h.name}`}
              >
                <Phone size={18} />
              </a>
            </div>
          ))}
        </div>
      </div>

      <Card className="p-4 bg-white border border-[#E3E9F2] mb-4">
        <div className="flex items-start gap-3">
          <HeartHandshake size={20} className="text-[#1456B8] flex-shrink-0 mt-0.5" />
          <div>
            <h3 className="text-xs font-black text-[#14213D]">When you need immediate grounding</h3>
            <p className="text-xs text-[#68758A] mt-1 leading-relaxed">
              Take three deep breaths. Feel your feet flat on the ground. Reach out to someone you trust or a helpline operator who is trained to help you through this moment.
            </p>
          </div>
        </div>
      </Card>

      <PrimaryButton
        title="Find a professional counsellor"
        icon="chatbubble-ellipses"
        onPress={() => navigation.navigate('Counsellors')}
      />
    </Screen>
  );
}
