import React, { useState } from 'react';
import {
  ShieldCheck,
  Lock,
  Mail,
  User,
  Eye,
  EyeOff,
  MapPin,
  GraduationCap,
  Sparkles,
  ArrowRight,
  CheckCircle2,
  AlertCircle,
  HelpCircle,
  X,
  Smartphone
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';
import { useAuthViewModel } from '../../viewmodels/useAuthViewModel';
import { useNavigation } from '../../navigation/NavigationContext';
import { Session } from '../../types';

interface SignUpScreenProps {
  setSession: (s: Session | null) => void;
}

const UGANDA_DISTRICTS = [
  'Kampala',
  'Wakiso',
  'Mbarara',
  'Kabale',
  'Gulu',
  'Jinja',
  'Mukono',
  'Mbale',
  'Arua',
  'Fort Portal',
  'Masaka',
  'Lira',
  'Soroti',
  'Other District',
];

const UGANDA_UNIVERSITIES = [
  'Kabale University (KAB)',
  'Makerere University (MAK)',
  'Kyambogo University (KYU)',
  'Mbarara University of Science & Tech (MUST)',
  'Uganda Christian University (UCU)',
  'Uganda Martyrs University (UMU)',
  'Islamic University in Uganda (IUIU)',
  'Gulu University',
  'Busitema University',
  'Muni University',
  'Other Institution / Not in University',
];

export default function SignUpScreen({ setSession }: SignUpScreenProps) {
  const navigation = useNavigation();
  const { loading, register, registerWithCredentials, registerWithGoogle } =
    useAuthViewModel(setSession);

  const [mode, setMode] = useState<'signup' | 'signin'>('signup');
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [district, setDistrict] = useState('Kampala');
  const [university, setUniversity] = useState('Kabale University (KAB)');
  const [agreeTerms, setAgreeTerms] = useState(true);
  const [error, setError] = useState('');

  // Google Sign-In Sheet / Modal state
  const [showGoogleModal, setShowGoogleModal] = useState(false);
  const [googleLoading, setGoogleLoading] = useState(false);

  // Compute password strength
  const getPasswordStrength = () => {
    if (!password) return { score: 0, label: '', color: 'bg-slate-200' };
    let score = 0;
    if (password.length >= 6) score += 1;
    if (password.length >= 8) score += 1;
    if (/[A-Z]/.test(password)) score += 1;
    if (/[0-9]/.test(password)) score += 1;
    if (/[^A-Za-z0-9]/.test(password)) score += 1;

    if (score <= 2) return { score: 1, label: 'Weak', color: 'bg-amber-400' };
    if (score <= 4) return { score: 2, label: 'Good', color: 'bg-blue-500' };
    return { score: 3, label: 'Strong', color: 'bg-emerald-500' };
  };

  const strength = getPasswordStrength();

  // Validate and submit credentials
  const handleSubmit = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    setError('');

    if (mode === 'signup') {
      if (!name.trim()) {
        setError('Please enter your full name or preferred nickname.');
        return;
      }
      if (!email.trim() || !email.includes('@')) {
        setError('Please enter a valid email address.');
        return;
      }
      if (password.length < 6) {
        setError('Password must be at least 6 characters long.');
        return;
      }
      if (!agreeTerms) {
        setError('Please agree to MindBridge terms and confidentiality policy.');
        return;
      }

      const newSession = await registerWithCredentials({
        displayName: name.trim(),
        email: email.trim(),
        district,
        university,
      });
      if (newSession) {
        navigation.navigate('Main');
      }
    } else {
      // Sign In mode
      if (!email.trim() || !email.includes('@')) {
        setError('Please enter your account email address.');
        return;
      }
      if (!password) {
        setError('Please enter your password.');
        return;
      }

      const newSession = await registerWithCredentials({
        displayName: email.split('@')[0],
        email: email.trim(),
        district,
        university,
      });
      if (newSession) {
        navigation.navigate('Main');
      }
    }
  };

  // Google Sign-In Action
  const handleGoogleSelect = async (account: {
    name: string;
    email: string;
    avatar: string;
  }) => {
    setGoogleLoading(true);
    try {
      await new Promise((res) => setTimeout(res, 600)); // Smooth auth simulation
      const newSession = await registerWithGoogle({
        displayName: account.name,
        email: account.email,
        avatar: account.avatar,
        university: 'Kabale University (KAB)',
      });
      setShowGoogleModal(false);
      if (newSession) {
        navigation.navigate('Main');
      }
    } finally {
      setGoogleLoading(false);
    }
  };

  // Anonymous Instant Access
  const handleAnonymousGuest = async () => {
    const guestSession = await register('Anonymous Friend');
    if (guestSession) {
      navigation.navigate('Main');
    }
  };

  return (
    <div className="min-h-screen bg-[#F7F9FC] flex flex-col justify-between max-w-md mx-auto border-x border-[#E3E9F2] shadow-sm relative">
      {/* Top App Bar with back button */}
      <div className="sticky top-0 z-20 bg-white/95 backdrop-blur-xs border-b border-[#E3E9F2] px-4 py-3 flex items-center justify-between">
        <button
          type="button"
          onClick={() => navigation.goBack()}
          className="p-1.5 -ml-1 text-[#68758A] hover:text-[#14213D] rounded-full hover:bg-slate-100 transition-colors cursor-pointer"
        >
          <X size={20} />
        </button>
        <div className="flex items-center gap-1.5">
          <div className="w-7 h-7 rounded-lg bg-[#1456B8] flex items-center justify-center text-white font-black text-xs">
            MB
          </div>
          <span className="text-sm font-black text-[#14213D] tracking-tight">
            MindBridge Uganda
          </span>
        </div>
        <button
          type="button"
          onClick={handleAnonymousGuest}
          className="text-xs font-black text-[#1456B8] hover:underline cursor-pointer"
        >
          Skip
        </button>
      </div>

      {/* Main Content Area */}
      <div className="flex-1 px-5 py-6 overflow-y-auto">
        {/* Hero Header */}
        <div className="text-center mb-6">
          <div className="inline-flex items-center gap-1.5 bg-[#EBF3FC] text-[#1456B8] px-3 py-1 rounded-full text-xs font-black mb-2">
            <Sparkles size={13} className="text-[#1B9A72]" />
            Uganda Youth Wellbeing
          </div>
          <h1 className="text-2xl font-black text-[#14213D] tracking-tight">
            {mode === 'signup' ? 'Create your account' : 'Welcome back'}
          </h1>
          <p className="text-xs text-[#68758A] mt-1 max-w-xs mx-auto leading-relaxed">
            {mode === 'signup'
              ? 'Access verified counsellors, the Amani AI companion, and safe mental health support.'
              : 'Sign in to access your booked sessions, confidential messages, and self-care library.'}
          </p>
        </div>

        {/* Mode Switcher Tabs */}
        <div className="flex bg-[#EAEFF7] p-1 rounded-xl mb-5">
          <button
            type="button"
            onClick={() => {
              setMode('signup');
              setError('');
            }}
            className={`flex-1 py-2 text-xs font-black rounded-lg transition-all cursor-pointer ${
              mode === 'signup'
                ? 'bg-white text-[#1456B8] shadow-xs'
                : 'text-[#68758A] hover:text-[#14213D]'
            }`}
          >
            Create Account
          </button>
          <button
            type="button"
            onClick={() => {
              setMode('signin');
              setError('');
            }}
            className={`flex-1 py-2 text-xs font-black rounded-lg transition-all cursor-pointer ${
              mode === 'signin'
                ? 'bg-white text-[#1456B8] shadow-xs'
                : 'text-[#68758A] hover:text-[#14213D]'
            }`}
          >
            Sign In
          </button>
        </div>

        {/* Continue with Google Button */}
        <div className="mb-5">
          <button
            type="button"
            onClick={() => setShowGoogleModal(true)}
            className="w-full flex items-center justify-center gap-3 bg-white hover:bg-[#F9FBFE] border border-[#D7E1EE] hover:border-[#1456B8] text-[#14213D] font-bold text-sm py-3 px-4 rounded-xl shadow-2xs hover:shadow-xs transition-all cursor-pointer group active:scale-[0.99]"
          >
            {/* Google Multicolor SVG Icon */}
            <svg className="w-5 h-5" viewBox="0 0 24 24">
              <path
                fill="#4285F4"
                d="M23.745 12.27c0-.7-.06-1.4-.19-2.07H12v4.51h6.6c-.29 1.52-1.14 2.82-2.4 3.68v3.05h3.88c2.27-2.09 3.665-5.17 3.665-9.17z"
              />
              <path
                fill="#34A853"
                d="M12 24c3.24 0 5.95-1.08 7.93-2.91l-3.88-3.05c-1.08.72-2.45 1.16-4.05 1.16-3.12 0-5.77-2.1-6.72-4.93H1.25v3.15C3.26 21.36 7.33 24 12 24z"
              />
              <path
                fill="#FBBC05"
                d="M5.28 14.27c-.25-.72-.38-1.49-.38-2.27s.13-1.55.38-2.27V6.58H1.25C.45 8.18 0 9.98 0 12s.45 3.82 1.25 5.42l4.03-3.15z"
              />
              <path
                fill="#EA4335"
                d="M12 4.75c1.77 0 3.35.61 4.6 1.8l3.42-3.42C17.95 1.19 15.24 0 12 0 7.33 0 3.26 2.64 1.25 6.58l4.03 3.15c.95-2.83 3.6-4.98 6.72-4.98z"
              />
            </svg>
            <span>Continue with Google</span>
          </button>
        </div>

        {/* Separator */}
        <div className="relative flex items-center justify-center mb-5">
          <div className="border-t border-[#E3E9F2] w-full"></div>
          <span className="bg-[#F7F9FC] px-3 text-[11px] font-bold text-[#8C98A9] uppercase tracking-wider relative z-10">
            or with email & credentials
          </span>
        </div>

        {/* Form Inputs */}
        <form onSubmit={handleSubmit} className="space-y-4">
          {mode === 'signup' && (
            <div>
              <label className="block text-xs font-black text-[#14213D] mb-1.5">
                Full Name / Preferred Nickname
              </label>
              <div className="relative flex items-center">
                <span className="absolute left-3.5 text-[#68758A]">
                  <User size={18} />
                </span>
                <input
                  type="text"
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                    if (error) setError('');
                  }}
                  placeholder="e.g. Sandra Natukunda"
                  className="w-full bg-white border border-[#D7E1EE] focus:border-[#1456B8] rounded-xl pl-10 pr-4 py-2.5 text-sm text-[#14213D] outline-none transition-colors placeholder:text-[#8C98A9] font-medium"
                />
              </div>
            </div>
          )}

          <div>
            <label className="block text-xs font-black text-[#14213D] mb-1.5">
              Email Address
            </label>
            <div className="relative flex items-center">
              <span className="absolute left-3.5 text-[#68758A]">
                <Mail size={18} />
              </span>
              <input
                type="email"
                value={email}
                onChange={(e) => {
                  setEmail(e.target.value);
                  if (error) setError('');
                }}
                placeholder="you@example.com or university email"
                className="w-full bg-white border border-[#D7E1EE] focus:border-[#1456B8] rounded-xl pl-10 pr-4 py-2.5 text-sm text-[#14213D] outline-none transition-colors placeholder:text-[#8C98A9] font-medium"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between mb-1.5">
              <label className="text-xs font-black text-[#14213D]">
                Password
              </label>
              {mode === 'signin' && (
                <button
                  type="button"
                  onClick={() => alert('Password reset link sent to your email!')}
                  className="text-[11px] font-bold text-[#1456B8] hover:underline"
                >
                  Forgot?
                </button>
              )}
            </div>
            <div className="relative flex items-center">
              <span className="absolute left-3.5 text-[#68758A]">
                <Lock size={18} />
              </span>
              <input
                type={showPassword ? 'text' : 'password'}
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  if (error) setError('');
                }}
                placeholder="••••••••"
                className="w-full bg-white border border-[#D7E1EE] focus:border-[#1456B8] rounded-xl pl-10 pr-11 py-2.5 text-sm text-[#14213D] outline-none transition-colors placeholder:text-[#8C98A9] font-medium"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3.5 text-[#68758A] hover:text-[#14213D] cursor-pointer"
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>

            {/* Password Strength Indicator for Signup */}
            {mode === 'signup' && password.length > 0 && (
              <div className="mt-2">
                <div className="flex items-center justify-between text-[10px] font-bold text-[#68758A] mb-1">
                  <span>Security strength:</span>
                  <span
                    className={
                      strength.score === 1
                        ? 'text-amber-600'
                        : strength.score === 2
                        ? 'text-blue-600'
                        : 'text-emerald-600'
                    }
                  >
                    {strength.label}
                  </span>
                </div>
                <div className="w-full h-1.5 bg-slate-200 rounded-full overflow-hidden flex">
                  <div
                    className={`h-full transition-all duration-300 ${strength.color}`}
                    style={{
                      width:
                        strength.score === 1
                          ? '33%'
                          : strength.score === 2
                          ? '66%'
                          : '100%',
                    }}
                  ></div>
                </div>
              </div>
            )}
          </div>

          {mode === 'signup' && (
            <>
              {/* District / Region */}
              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-black text-[#14213D] mb-1.5 flex items-center gap-1">
                    <MapPin size={13} className="text-[#1456B8]" /> District
                  </label>
                  <select
                    value={district}
                    onChange={(e) => setDistrict(e.target.value)}
                    className="w-full bg-white border border-[#D7E1EE] focus:border-[#1456B8] rounded-xl px-3 py-2.5 text-xs text-[#14213D] outline-none font-medium cursor-pointer"
                  >
                    {UGANDA_DISTRICTS.map((d) => (
                      <option key={d} value={d}>
                        {d}
                      </option>
                    ))}
                  </select>
                </div>

                {/* University / School */}
                <div>
                  <label className="block text-xs font-black text-[#14213D] mb-1.5 flex items-center gap-1">
                    <GraduationCap size={13} className="text-[#1B9A72]" /> Campus
                  </label>
                  <select
                    value={university}
                    onChange={(e) => setUniversity(e.target.value)}
                    className="w-full bg-white border border-[#D7E1EE] focus:border-[#1456B8] rounded-xl px-3 py-2.5 text-xs text-[#14213D] outline-none font-medium cursor-pointer truncate"
                  >
                    {UGANDA_UNIVERSITIES.map((u) => (
                      <option key={u} value={u}>
                        {u}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Terms Checkbox */}
              <div className="pt-1">
                <label className="flex items-start gap-2.5 cursor-pointer select-none">
                  <input
                    type="checkbox"
                    checked={agreeTerms}
                    onChange={(e) => setAgreeTerms(e.target.checked)}
                    className="mt-0.5 w-4 h-4 rounded text-[#1456B8] focus:ring-[#1456B8] border-[#D7E1EE] cursor-pointer"
                  />
                  <span className="text-[11px] text-[#68758A] leading-tight">
                    I agree to MindBridge's{' '}
                    <span className="text-[#1456B8] font-bold">Terms of Care</span> and{' '}
                    <span className="text-[#1456B8] font-bold">Confidentiality Standards</span>.
                  </span>
                </label>
              </div>
            </>
          )}

          {/* Error Message */}
          {error && (
            <div className="bg-red-50 border border-red-200 text-[#C83B35] rounded-xl p-3 text-xs flex items-center gap-2">
              <AlertCircle size={16} className="shrink-0" />
              <span>{error}</span>
            </div>
          )}

          {/* Primary Submit Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full mt-2 bg-[#1456B8] hover:bg-[#0D3E83] disabled:opacity-50 text-white font-black text-sm py-3 px-4 rounded-xl shadow-sm transition-all flex items-center justify-center gap-2 cursor-pointer active:scale-[0.99]"
          >
            {loading ? (
              <span className="flex items-center gap-2">
                <span className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></span>
                Processing...
              </span>
            ) : (
              <>
                <span>
                  {mode === 'signup'
                    ? 'Create MindBridge Account'
                    : 'Sign In to Account'}
                </span>
                <ArrowRight size={17} />
              </>
            )}
          </button>
        </form>

        {/* Anonymous Quick Access Banner */}
        <div className="mt-6 pt-5 border-t border-[#E3E9F2] text-center">
          <p className="text-[11px] text-[#68758A] mb-2.5">
            Prefer not to share credentials right now?
          </p>
          <button
            type="button"
            onClick={handleAnonymousGuest}
            className="inline-flex items-center gap-2 text-xs font-black text-[#1B9A72] hover:text-[#136e52] bg-[#E8F6F1] hover:bg-[#DCF2EB] px-4 py-2 rounded-full transition-colors cursor-pointer"
          >
            <ShieldCheck size={16} />
            <span>Continue 100% Anonymously (Guest Mode)</span>
          </button>
        </div>
      </div>

      {/* Google Account Selector Modal / Bottom Sheet */}
      <AnimatePresence>
        {showGoogleModal && (
          <div className="fixed inset-0 z-50 bg-black/50 backdrop-blur-xs flex items-end sm:items-center justify-center p-0 sm:p-4">
            <motion.div
              initial={{ y: '100%', opacity: 0 }}
              animate={{ y: 0, opacity: 1 }}
              exit={{ y: '100%', opacity: 0 }}
              transition={{ type: 'spring', damping: 25, stiffness: 280 }}
              className="bg-white rounded-t-3xl sm:rounded-2xl max-w-sm w-full p-5 shadow-2xl border border-slate-200"
            >
              <div className="flex items-center justify-between pb-3 border-b border-slate-100 mb-4">
                <div className="flex items-center gap-2.5">
                  <svg className="w-5 h-5" viewBox="0 0 24 24">
                    <path
                      fill="#4285F4"
                      d="M23.745 12.27c0-.7-.06-1.4-.19-2.07H12v4.51h6.6c-.29 1.52-1.14 2.82-2.4 3.68v3.05h3.88c2.27-2.09 3.665-5.17 3.665-9.17z"
                    />
                    <path
                      fill="#34A853"
                      d="M12 24c3.24 0 5.95-1.08 7.93-2.91l-3.88-3.05c-1.08.72-2.45 1.16-4.05 1.16-3.12 0-5.77-2.1-6.72-4.93H1.25v3.15C3.26 21.36 7.33 24 12 24z"
                    />
                    <path
                      fill="#FBBC05"
                      d="M5.28 14.27c-.25-.72-.38-1.49-.38-2.27s.13-1.55.38-2.27V6.58H1.25C.45 8.18 0 9.98 0 12s.45 3.82 1.25 5.42l4.03-3.15z"
                    />
                    <path
                      fill="#EA4335"
                      d="M12 4.75c1.77 0 3.35.61 4.6 1.8l3.42-3.42C17.95 1.19 15.24 0 12 0 7.33 0 3.26 2.64 1.25 6.58l4.03 3.15c.95-2.83 3.6-4.98 6.72-4.98z"
                    />
                  </svg>
                  <h3 className="text-sm font-black text-[#14213D]">
                    Sign in with Google
                  </h3>
                </div>
                <button
                  type="button"
                  onClick={() => setShowGoogleModal(false)}
                  className="p-1 text-slate-400 hover:text-slate-700 rounded-full cursor-pointer"
                >
                  <X size={18} />
                </button>
              </div>

              <p className="text-xs text-[#68758A] mb-3">
                Choose an account to continue to <strong>MindBridge Uganda</strong>:
              </p>

              {/* Account list */}
              <div className="space-y-2 mb-4">
                {/* User's Academic Google Account */}
                <button
                  type="button"
                  disabled={googleLoading}
                  onClick={() =>
                    handleGoogleSelect({
                      name: 'Kabale Student',
                      email: '2024akcs4333f@kab.ac.ug',
                      avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=120&q=80',
                    })
                  }
                  className="w-full flex items-center gap-3 p-3 rounded-xl border border-slate-200 hover:border-[#1456B8] hover:bg-[#F2F7FF] transition-all text-left cursor-pointer group"
                >
                  <div className="w-10 h-10 rounded-full bg-[#1456B8] text-white flex items-center justify-center font-black text-sm shrink-0 shadow-2xs">
                    KS
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="text-xs font-black text-[#14213D] truncate group-hover:text-[#1456B8]">
                      Kabale University Account
                    </p>
                    <p className="text-[11px] text-[#68758A] truncate">
                      2024akcs4333f@kab.ac.ug
                    </p>
                  </div>
                  <CheckCircle2 size={16} className="text-slate-300 group-hover:text-[#1456B8]" />
                </button>

                {/* General Personal Google Account */}
                <button
                  type="button"
                  disabled={googleLoading}
                  onClick={() =>
                    handleGoogleSelect({
                      name: 'Sandra Mukasa',
                      email: 'sandra.mukasa.ug@gmail.com',
                      avatar: 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=120&q=80',
                    })
                  }
                  className="w-full flex items-center gap-3 p-3 rounded-xl border border-slate-200 hover:border-[#1456B8] hover:bg-[#F2F7FF] transition-all text-left cursor-pointer group"
                >
                  <div className="w-10 h-10 rounded-full bg-[#1B9A72] text-white flex items-center justify-center font-black text-sm shrink-0 shadow-2xs">
                    SM
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="text-xs font-black text-[#14213D] truncate group-hover:text-[#1456B8]">
                      Sandra Mukasa
                    </p>
                    <p className="text-[11px] text-[#68758A] truncate">
                      sandra.mukasa.ug@gmail.com
                    </p>
                  </div>
                  <CheckCircle2 size={16} className="text-slate-300 group-hover:text-[#1456B8]" />
                </button>
              </div>

              <div className="text-[10px] text-[#8C98A9] text-center mb-2">
                Google will share your name, email address, and profile picture with MindBridge Uganda.
              </div>

              {googleLoading && (
                <div className="flex items-center justify-center gap-2 py-2 text-xs font-bold text-[#1456B8]">
                  <span className="w-4 h-4 border-2 border-[#1456B8] border-t-transparent rounded-full animate-spin"></span>
                  Authenticating with Google...
                </div>
              )}
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </div>
  );
}
