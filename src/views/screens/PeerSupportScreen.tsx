import React from 'react';
import { Users, Shield, ArrowUp } from 'lucide-react';
import { Card, Header, PrimaryButton, Screen } from '../components/UI';
import { usePeerSupportViewModel } from '../../viewmodels/usePeerSupportViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface PeerSupportScreenProps {
  session: Session;
  setSession: (s: Session) => void;
}

export default function PeerSupportScreen({ session, setSession }: PeerSupportScreenProps) {
  const navigation = useNavigation();
  const vm = usePeerSupportViewModel({ session, setSession });

  return (
    <Screen>
      <Header
        title="Peer Support"
        subtitle="Anonymous youth community"
        onBack={() => navigation.goBack()}
      />

      <Card className="bg-[#EAF2FF] border-[#D0E2FF] p-4 mb-4">
        <div className="flex items-start gap-3">
          <Shield size={20} className="text-[#1456B8] flex-shrink-0 mt-0.5" />
          <div>
            <h2 className="text-xs font-black uppercase tracking-wider text-[#1456B8]">
              Community Guidelines
            </h2>
            <p className="text-xs text-[#14213D] mt-1 leading-relaxed">
              Be kind and supportive. All posts are anonymous. Never share private contacts, physical addresses or harmful instructions.
            </p>
          </div>
        </div>
      </Card>

      {/* Composer */}
      <Card className="p-4 mb-4">
        <label className="block text-xs font-black uppercase tracking-wider text-[#14213D] mb-2">
          Share your thoughts
        </label>
        <textarea
          rows={3}
          value={vm.text}
          onChange={(e) => vm.setText(e.target.value)}
          placeholder="Share how you are doing today anonymously..."
          className="w-full border border-[#E3E9F2] rounded-xl p-3 text-sm text-[#14213D] focus:outline-none focus:border-[#1456B8] placeholder-[#9BAABC] resize-none"
        />
        <div className="mt-3">
          <PrimaryButton
            title="Share anonymously"
            icon="arrow-up"
            disabled={!vm.text.trim()}
            onPress={vm.publish}
          />
        </div>
      </Card>

      {/* Post List */}
      <div className="space-y-3">
        <h3 className="text-xs font-black uppercase tracking-wider text-[#68758A] px-1">
          Recent reflections
        </h3>
        {vm.posts.map((post) => (
          <Card key={post.id} className="p-4">
            <div className="flex items-center gap-2 mb-2">
              <div className="w-7 h-7 rounded-full bg-[#E8F7F1] text-[#1B9A72] flex items-center justify-center text-xs font-bold">
                <Users size={14} />
              </div>
              <div>
                <span className="text-xs font-bold text-[#14213D]">
                  {post.author}
                </span>
                <span className="text-[10px] text-[#68758A] ml-2">
                  {new Date(post.createdAt).toLocaleDateString()}
                </span>
              </div>
            </div>
            <p className="text-sm text-[#14213D] leading-relaxed">
              {post.text}
            </p>
          </Card>
        ))}
      </div>
    </Screen>
  );
}
