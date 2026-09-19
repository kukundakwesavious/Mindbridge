import React, { useState } from 'react';
import { Check } from 'lucide-react';
import { Card, Header, PrimaryButton, Screen } from '../components/UI';
import { languages } from '../../models/content';
import { useProfileViewModel } from '../../viewmodels/useProfileViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface LanguageScreenProps {
  session: Session;
  setSession: (s: Session) => void;
}

export default function LanguageScreen({ session, setSession }: LanguageScreenProps) {
  const navigation = useNavigation();
  const { update, saving } = useProfileViewModel({ session, setSession });
  const [selected, setSelected] = useState(session.language || 'English');

  const handleSave = async () => {
    await update({ language: selected });
    navigation.goBack();
  };

  return (
    <Screen>
      <Header
        title="Select language"
        subtitle="Choose your preferred language"
        onBack={() => navigation.goBack()}
      />

      <div className="space-y-2.5 mb-6">
        {languages.map((lang) => {
          const isSelected = selected === lang;
          return (
            <Card
              key={lang}
              onClick={() => setSelected(lang)}
              className={`flex items-center justify-between p-4 mb-0 transition-colors ${
                isSelected ? 'border-[#1456B8] bg-[#F4F8FF]' : 'border-[#E3E9F2]'
              }`}
            >
              <div>
                <p className="text-sm font-black text-[#14213D]">{lang}</p>
                <p className="text-xs text-[#68758A] mt-0.5">
                  {lang === 'English'
                    ? 'Default system language'
                    : lang === 'Luganda'
                    ? 'Olusomereddwa mu Luganda'
                    : 'Ebirikuhindurwa omu Runyankore'}
                </p>
              </div>

              <div
                className={`w-6 h-6 rounded-full border flex items-center justify-center transition-colors ${
                  isSelected
                    ? 'bg-[#1456B8] border-[#1456B8] text-white'
                    : 'border-[#CBD5E1]'
                }`}
              >
                {isSelected && <Check size={14} />}
              </div>
            </Card>
          );
        })}
      </div>

      <PrimaryButton
        title="Save language"
        loading={saving}
        onPress={handleSave}
      />
    </Screen>
  );
}
