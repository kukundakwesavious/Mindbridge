// Real-world photography sources. These are free-to-use Unsplash photos.
// Remote URLs keep the prototype light; production should download/optimize approved assets locally.
export const media = {
  heroTherapy: {
    uri: 'https://images.unsplash.com/photo-1758273241260-f49172d876e3?auto=format&fit=crop&fm=jpg&q=78&w=1200',
    credit: 'Vitaly Gariev / Unsplash',
  },
  phoneUser: {
    uri: 'https://images.unsplash.com/photo-1693849123549-e2f9a89b12f5?auto=format&fit=crop&fm=jpg&q=78&w=900',
    credit: 'rayul / Unsplash',
  },
  ugandaCommunity: {
    uri: 'https://images.unsplash.com/photo-1551357176-3158cabfc336?auto=format&fit=crop&fm=jpg&q=78&w=1200',
    credit: 'Random Institute / Unsplash',
  },
  therapySession: {
    uri: 'https://images.unsplash.com/photo-1758273240403-052b3c99f636?auto=format&fit=crop&fm=jpg&q=78&w=1200',
    credit: 'Vitaly Gariev / Unsplash',
  },
};

export const fallbackAssets = {
  heroTherapy: require('../../assets/mental-illustration.png'),
  phoneUser: require('../../assets/welcome-illustration.png'),
  ugandaCommunity: require('../../assets/referral-illustration.png'),
  therapySession: require('../../assets/privacy-banner.png'),
};
