import { FaithLeader, ReferralType } from '../types';

export const referralTypes: ReferralType[] = [
  {
    id: 'community',
    title: 'Community Leader',
    subtitle: 'Trusted community support',
    icon: 'users',
    tone: 'green',
  },
  {
    id: 'faith',
    title: 'Faith Leader',
    subtitle: 'Spiritual and emotional support',
    icon: 'building',
    tone: 'gold',
  },
  {
    id: 'facility',
    title: 'Health Facility',
    subtitle: 'Referral to professional care',
    icon: 'activity',
    tone: 'blue',
  },
  {
    id: 'emergency',
    title: 'Emergency Help',
    subtitle: 'For urgent situations',
    icon: 'alert-circle',
    tone: 'red',
  },
];

export const faithLeaders: FaithLeader[] = [
  {
    id: 'f1',
    name: 'Pastor John Musinguzi',
    role: 'Faith Leader',
    area: 'Kigezi Region',
    rating: 4.6,
    reviews: 52,
    available: true,
    avatar: 'JM',
  },
];
