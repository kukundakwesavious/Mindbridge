import { media } from '../data/media';

export const counsellors = [
  { id: 'c1', name: 'Dr. Grace Nakunda', role: 'Psychologist', languages: ['English', 'Luganda'], speciality: 'Youth wellbeing', rating: 4.8, reviews: 124, available: true, avatar: 'GN', image: media.therapySession },
  { id: 'c2', name: 'Mr. Samuel Kato', role: 'Counsellor', languages: ['Luganda', 'Runyankore'], speciality: 'Stress & relationships', rating: 4.7, reviews: 98, available: true, avatar: 'SK', image: media.phoneUser },
  { id: 'c3', name: 'Ms. Patricia Akampurira', role: 'Clinical Psychologist', languages: ['English', 'Luganda'], speciality: 'Anxiety & depression', rating: 4.9, reviews: 86, available: true, avatar: 'PA', image: media.heroTherapy },
  { id: 'c4', name: 'Dr. Ronald Ssegorere', role: 'Counsellor', languages: ['English', 'Runyankore'], speciality: 'HIV psychosocial support', rating: 4.6, reviews: 71, available: false, avatar: 'RS', image: media.therapySession },
];
