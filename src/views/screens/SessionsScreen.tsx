import React from 'react';
import { Card, EmptyState, Header, IconBox, Screen } from '../components/UI';
import { counsellors } from '../../models/counsellors';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface SessionsScreenProps {
  session: Session | null | undefined;
}

export default function SessionsScreen({ session }: SessionsScreenProps) {
  const navigation = useNavigation();
  const items = session?.bookedSessions || [];

  return (
    <Screen>
      <Header
        title="My sessions"
        subtitle="Your saved appointments"
        onBack={navigation.canGoBack() ? () => navigation.goBack() : undefined}
      />

      {items.length > 0 ? (
        <div className="space-y-3">
          {items.map((x) => {
            const c = counsellors.find((v) => v.id === x.counsellorId);
            const iconName =
              x.mode === 'Text chat'
                ? 'chatbubble-ellipses'
                : x.mode === 'Voice call'
                ? 'call'
                : 'videocam';

            return (
              <Card key={x.id} className="p-4">
                <div className="flex items-center gap-3">
                  <IconBox icon={iconName} tone="blue" size={46} />
                  <div className="flex-1 min-w-0">
                    <h2 className="text-sm font-black text-[#14213D] truncate">
                      {x.counsellorName}
                    </h2>
                    <p className="text-xs text-[#68758A] mt-0.5">
                      {x.date} • {x.time}
                    </p>
                    <p className="text-xs font-bold text-[#1456B8] mt-0.5">
                      {x.mode} • <span className="capitalize">{x.status}</span>
                    </p>
                  </div>
                </div>

                {c && x.mode === 'Text chat' && (
                  <button
                    type="button"
                    onClick={() => navigation.navigate('Chat', { counsellor: c })}
                    className="w-full h-10 rounded-xl bg-[#EAF2FF] hover:bg-[#d8e8ff] active:scale-[0.985] text-[#1456B8] text-xs font-black flex items-center justify-center mt-3 transition-all cursor-pointer"
                  >
                    Open secure chat
                  </button>
                )}

                {c && (x.mode === 'Voice call' || x.mode === 'Video call') && (
                  <button
                    type="button"
                    onClick={() =>
                      navigation.navigate('SessionRoom', {
                        counsellor: c,
                        mode: x.mode,
                      })
                    }
                    className="w-full h-10 rounded-xl bg-[#EAF2FF] hover:bg-[#d8e8ff] active:scale-[0.985] text-[#1456B8] text-xs font-black flex items-center justify-center mt-3 transition-all cursor-pointer"
                  >
                    Open session room
                  </button>
                )}
              </Card>
            );
          })}
        </div>
      ) : (
        <EmptyState
          icon="calendar"
          title="No sessions yet"
          text="Book a session with a verified counsellor when you are ready."
        />
      )}
    </Screen>
  );
}
