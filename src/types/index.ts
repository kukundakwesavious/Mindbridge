export interface Booking {
  id: string;
  counsellorId: string;
  counsellorName: string;
  mode: 'Text chat' | 'Voice call' | 'Video call' | string;
  date: string;
  time: string;
  status: string;
  createdAt: string;
}

export interface ReferralRequest {
  id: string;
  type: string;
  provider: string;
  status: string;
  createdAt: string;
}

export interface ChatMessage {
  id: string;
  sender: 'user' | 'counsellor';
  text: string;
  time: string;
}

export interface PeerPost {
  id: string;
  author: string;
  text: string;
  createdAt: string;
}

export interface Session {
  anonymousId: string;
  displayName: string;
  email?: string;
  authProvider?: 'anonymous' | 'credentials' | 'google';
  avatar?: string;
  district?: string;
  university?: string;
  language: string;
  notifications: boolean;
  onboardingComplete: boolean;
  bookedSessions: Booking[];
  referrals: ReferralRequest[];
  chatThreads: Record<string, ChatMessage[]>;
  peerPosts: PeerPost[];
}

export interface Counsellor {
  id: string;
  name: string;
  role: string;
  languages: string[];
  speciality: string;
  rating: number;
  reviews: number;
  available: boolean;
  avatar: string;
  image?: { uri: string; credit?: string } | string;
}

export interface ContentItem {
  id: string;
  title: string;
  type: string;
  time: string;
  category: string;
  icon: string;
  tone: string;
  summary: string;
  body: string[];
}

export interface ReferralType {
  id: string;
  title: string;
  subtitle: string;
  icon: string;
  tone: string;
}

export interface FaithLeader {
  id: string;
  name: string;
  role: string;
  area: string;
  rating: number;
  reviews: number;
  available: boolean;
  avatar: string;
}

export interface AmaniChatMessage {
  id: string;
  sender: 'user' | 'amani';
  text: string;
  time: string;
  isHelpfulTip?: boolean;
}

export type ScreenName =
  | 'Splash'
  | 'Welcome'
  | 'SignUp'
  | 'Main'
  | 'AmaniChat'
  | 'Counsellors'
  | 'CounsellorDetail'
  | 'Booking'
  | 'Chat'
  | 'ContentDetail'
  | 'ReferralDetail'
  | 'Language'
  | 'Crisis'
  | 'PeerSupport'
  | 'SessionRoom';

export type MainTab = 'Home' | 'Sessions' | 'Amani' | 'Library' | 'Referrals' | 'Profile';
