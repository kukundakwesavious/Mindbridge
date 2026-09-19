import React, { createContext, useContext, useState, useCallback } from 'react';
import { MainTab, ScreenName } from '../types';

interface NavigationState {
  screen: ScreenName;
  params?: any;
}

interface NavigationContextType {
  currentScreen: ScreenName;
  params: any;
  activeTab: MainTab;
  setActiveTab: (tab: MainTab) => void;
  navigate: (screen: ScreenName, params?: any) => void;
  goBack: () => void;
  replace: (screen: ScreenName, params?: any) => void;
  canGoBack: () => boolean;
}

const NavigationContext = createContext<NavigationContextType | null>(null);

export function NavigationProvider({
  children,
  initialScreen = 'Main',
}: {
  children: React.ReactNode;
  initialScreen?: ScreenName;
}) {
  const [history, setHistory] = useState<NavigationState[]>([
    { screen: initialScreen },
  ]);
  const [activeTab, setActiveTab] = useState<MainTab>('Home');

  const current = history[history.length - 1] || { screen: initialScreen };

  const navigate = useCallback((screen: ScreenName, params?: any) => {
    // If navigating to Main tabs directly
    if (['Home', 'Sessions', 'Amani', 'Library', 'Referrals', 'Profile'].includes(screen)) {
      setActiveTab(screen as MainTab);
      setHistory([{ screen: 'Main' }]);
      return;
    }
    setHistory((prev) => [...prev, { screen, params }]);
  }, []);

  const replace = useCallback((screen: ScreenName, params?: any) => {
    setHistory((prev) => {
      const next = [...prev];
      next[next.length - 1] = { screen, params };
      return next;
    });
  }, []);

  const goBack = useCallback(() => {
    setHistory((prev) => {
      if (prev.length <= 1) return prev;
      return prev.slice(0, prev.length - 1);
    });
  }, []);

  const canGoBack = useCallback(() => history.length > 1, [history.length]);

  return (
    <NavigationContext.Provider
      value={{
        currentScreen: current.screen,
        params: current.params,
        activeTab,
        setActiveTab,
        navigate,
        goBack,
        replace,
        canGoBack,
      }}
    >
      {children}
    </NavigationContext.Provider>
  );
}

export function useNavigation() {
  const context = useContext(NavigationContext);
  if (!context) {
    throw new Error('useNavigation must be used within NavigationProvider');
  }
  return context;
}
