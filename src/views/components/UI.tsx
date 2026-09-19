import React, { useState } from 'react';
import {
  ArrowLeft,
  ShieldCheck,
  Users,
  MessageCircle,
  Calendar,
  BookOpen,
  FileText,
  GitBranch,
  User,
  Star,
  Video,
  Phone,
  Mic,
  Volume2,
  Leaf,
  Heart,
  Sun,
  X,
  Search,
  Bell,
  ChevronRight,
  Building,
  Activity,
  AlertCircle,
  Wifi,
  ArrowRight,
  ArrowUp,
  Globe,
  Loader2,
  LucideIcon,
} from 'lucide-react';
import { colors } from '../../theme/theme';
import { fallbackAssets } from '../../data/media';

interface ScreenProps {
  children: React.ReactNode;
  scroll?: boolean;
  className?: string;
  contentClassName?: string;
}

export function Screen({ children, scroll = true, className = '', contentClassName = '' }: ScreenProps) {
  return (
    <div
      className={`min-h-screen w-full bg-[#F7F9FC] flex flex-col justify-start items-center ${className}`}
    >
      <div
        className={`w-full max-w-md min-h-screen bg-[#F7F9FC] flex flex-col relative shadow-xl shadow-slate-900/5 sm:border-x sm:border-slate-200/80 ${
          scroll ? 'overflow-y-auto' : 'overflow-hidden'
        }`}
      >
        <div className={`flex-1 flex flex-col p-4 pb-24 ${contentClassName}`}>
          {children}
        </div>
      </div>
    </div>
  );
}

interface HeaderProps {
  title: string;
  subtitle?: string;
  onBack?: () => void;
  right?: React.ReactNode;
}

export function Header({ title, subtitle, onBack, right }: HeaderProps) {
  return (
    <div className="flex items-center gap-3 pb-4 mb-2">
      {onBack && (
        <button
          type="button"
          onClick={onBack}
          aria-label="Go back"
          className="w-10 h-10 rounded-full flex items-center justify-center bg-white border border-[#E3E9F2] hover:bg-slate-50 active:scale-95 transition-all text-[#14213D] shadow-sm flex-shrink-0 cursor-pointer"
        >
          <ArrowLeft size={20} />
        </button>
      )}
      <div className="flex-1 min-w-0">
        <h1 className="text-xl font-black text-[#14213D] tracking-tight truncate leading-tight">
          {title}
        </h1>
        {subtitle && (
          <p className="text-xs text-[#68758A] mt-0.5 font-medium truncate">
            {subtitle}
          </p>
        )}
      </div>
      {right}
    </div>
  );
}

export function Logo({ small = false }: { small?: boolean }) {
  return (
    <div className="flex items-center gap-2">
      <img
        src="/assets/mindbridge-logo.png"
        alt="MindBridge Logo"
        onError={(e) => {
          // Fallback to app icon if logo fails
          (e.currentTarget as HTMLImageElement).src = '/assets/app-icon.png';
        }}
        className={small ? 'w-8 h-8 object-contain' : 'w-10 h-10 object-contain'}
      />
      <span className={`font-black tracking-tight text-[#1456B8] ${small ? 'text-lg' : 'text-2xl'}`}>
        Mind<span className="text-[#1B9A72]">Bridge</span>
      </span>
    </div>
  );
}

interface SmartImageProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  source?: { uri?: string } | string;
  fallback?: string;
}

export function SmartImage({ source, fallback, className = '', alt = '', ...props }: SmartImageProps) {
  const uri = typeof source === 'string' ? source : source?.uri;
  const fallbackSrc = fallback || fallbackAssets.heroTherapy;
  const [src, setSrc] = useState(uri || fallbackSrc);

  return (
    <img
      src={src}
      alt={alt}
      onError={() => {
        if (src !== fallbackSrc) {
          setSrc(fallbackSrc);
        }
      }}
      className={`object-cover ${className}`}
      {...props}
    />
  );
}

interface AvatarProps {
  image?: { uri?: string } | string;
  initials: string;
  size?: number;
}

export function Avatar({ image, initials, size = 54 }: AvatarProps) {
  const uri = typeof image === 'string' ? image : image?.uri;
  if (uri) {
    return (
      <div
        style={{ width: size, height: size, borderRadius: size * 0.3 }}
        className="overflow-hidden bg-slate-100 flex-shrink-0 border border-[#E3E9F2]"
      >
        <SmartImage
          source={uri}
          fallback={fallbackAssets.therapySession}
          className="w-full h-full object-cover"
        />
      </div>
    );
  }

  return (
    <div
      style={{ width: size, height: size, borderRadius: size * 0.3 }}
      className="bg-[#EAF2FF] flex items-center justify-center text-[#1456B8] font-black text-sm border border-[#D0E2FF] flex-shrink-0"
    >
      {initials}
    </div>
  );
}

interface PrimaryButtonProps {
  title: string;
  onPress?: () => void;
  icon?: string;
  loading?: boolean;
  danger?: boolean;
  disabled?: boolean;
  className?: string;
}

export function PrimaryButton({
  title,
  onPress,
  icon,
  loading = false,
  danger = false,
  disabled = false,
  className = '',
}: PrimaryButtonProps) {
  return (
    <button
      type="button"
      disabled={disabled || loading}
      onClick={onPress}
      className={`w-full min-h-[52px] rounded-[15px] flex items-center justify-center px-4 font-extrabold text-[15px] transition-all cursor-pointer ${
        danger
          ? 'bg-[#C83B35] hover:bg-[#b0322d] text-white shadow-sm shadow-red-500/20'
          : 'bg-[#1456B8] hover:bg-[#0B3F8A] text-white shadow-sm shadow-blue-500/20'
      } ${disabled ? 'opacity-45 cursor-not-allowed' : 'active:scale-[0.985]'} ${className}`}
    >
      {loading ? (
        <Loader2 className="animate-spin text-white" size={20} />
      ) : (
        <>
          {icon && <span className="mr-2">{renderIcon(icon, 18, 'white')}</span>}
          <span>{title}</span>
        </>
      )}
    </button>
  );
}

interface OutlineButtonProps {
  title: string;
  onPress?: () => void;
  icon?: string;
  danger?: boolean;
  className?: string;
}

export function OutlineButton({ title, onPress, icon, danger = false, className = '' }: OutlineButtonProps) {
  return (
    <button
      type="button"
      onClick={onPress}
      className={`w-full min-h-[50px] rounded-[15px] border-[1.5px] flex items-center justify-center px-4 font-extrabold text-sm transition-all bg-white cursor-pointer ${
        danger
          ? 'border-[#E9B7B4] text-[#C83B35] hover:bg-red-50'
          : 'border-[#1456B8] text-[#1456B8] hover:bg-[#EAF2FF]'
      } active:scale-[0.985] ${className}`}
    >
      {icon && <span className="mr-2">{renderIcon(icon, 18, danger ? colors.red : colors.primary)}</span>}
      <span>{title}</span>
    </button>
  );
}

interface CardProps {
  children: React.ReactNode;
  className?: string;
  onClick?: () => void;
  style?: React.CSSProperties;
}

export function Card({ children, className = '', onClick, style }: CardProps) {
  const Component = onClick ? 'button' : 'div';
  return (
    <Component
      type={onClick ? 'button' : undefined}
      onClick={onClick}
      style={style}
      className={`w-full text-left bg-white rounded-[18px] p-4 border border-[#E3E9F2] mb-3 shadow-[0_5px_14px_rgba(12,27,51,0.045)] transition-all ${
        onClick ? 'hover:border-[#9BC4FF] hover:shadow-md active:opacity-90 cursor-pointer' : ''
      } ${className}`}
    >
      {children}
    </Component>
  );
}

export function IconBox({
  icon,
  tone = 'blue',
  size = 46,
}: {
  icon: string;
  tone?: string;
  size?: number;
}) {
  const map: Record<string, [string, string]> = {
    blue: [colors.primarySoft, colors.primary],
    green: [colors.secondarySoft, colors.secondary],
    purple: [colors.lavender, '#7255C7'],
    orange: [colors.peach, '#D8782A'],
    pink: ['#FFEAF0', '#C74E78'],
    red: [colors.redSoft, colors.red],
    gold: [colors.yellow, '#A97800'],
    teal: ['#E5F8F6', '#198E83'],
  };
  const [bg, fg] = map[tone] || map.blue;

  return (
    <div
      style={{
        width: size,
        height: size,
        borderRadius: 15,
        backgroundColor: bg,
        color: fg,
      }}
      className="flex items-center justify-center flex-shrink-0"
    >
      {renderIcon(icon, Math.round(size * 0.48), fg)}
    </div>
  );
}

export function Field({
  label,
  placeholder,
  value,
  onChangeText,
  type = 'text',
}: {
  label: string;
  placeholder?: string;
  value: string;
  onChangeText: (text: string) => void;
  type?: string;
}) {
  return (
    <div className="mb-4">
      <label className="block text-[13px] font-extrabold text-[#14213D] mb-1.5">
        {label}
      </label>
      <input
        type={type}
        value={value}
        onChange={(e) => onChangeText(e.target.value)}
        placeholder={placeholder}
        className="w-full h-[52px] border border-[#E3E9F2] rounded-[13px] bg-white px-4 text-[#14213D] text-sm focus:outline-none focus:border-[#1456B8] focus:ring-2 focus:ring-blue-100 placeholder-[#9BAABC]"
      />
    </div>
  );
}

export function SearchField({
  value,
  onChangeText,
  placeholder = 'Search',
}: {
  value: string;
  onChangeText: (text: string) => void;
  placeholder?: string;
}) {
  return (
    <div className="h-[49px] rounded-[14px] bg-white border border-[#E3E9F2] px-3.5 flex items-center gap-2 mb-3 shadow-xs">
      <Search size={18} className="text-[#8C98AA] flex-shrink-0" />
      <input
        type="text"
        value={value}
        onChange={(e) => onChangeText(e.target.value)}
        placeholder={placeholder}
        className="flex-1 bg-transparent border-none text-sm text-[#14213D] focus:outline-none placeholder-[#8C98AA]"
      />
      {value && (
        <button
          type="button"
          onClick={() => onChangeText('')}
          className="text-[#8C98AA] hover:text-[#14213D] p-1 cursor-pointer"
        >
          <X size={16} />
        </button>
      )}
    </div>
  );
}

export function Pill({
  label,
  active,
  onPress,
}: {
  label: string;
  active: boolean;
  onPress: () => void;
}) {
  return (
    <button
      type="button"
      onClick={onPress}
      className={`px-4 py-2 rounded-full border text-xs font-extrabold whitespace-nowrap transition-colors cursor-pointer mr-2 mb-1.5 ${
        active
          ? 'bg-[#1456B8] border-[#1456B8] text-white'
          : 'bg-white border-[#E3E9F2] text-[#68758A] hover:border-slate-300'
      }`}
    >
      {label}
    </button>
  );
}

export function EmptyState({
  icon = 'calendar',
  title,
  text,
}: {
  icon?: string;
  title: string;
  text: string;
}) {
  return (
    <div className="flex flex-col items-center justify-center py-14 px-6 text-center">
      <IconBox icon={icon} tone="blue" size={62} />
      <h3 className="text-base font-extrabold text-[#14213D] mt-3.5">{title}</h3>
      <p className="text-xs text-[#68758A] mt-1.5 leading-relaxed max-w-xs">{text}</p>
    </div>
  );
}

export function renderIcon(name: string, size = 18, color?: string) {
  const iconProps = { size, color: color || 'currentColor' };
  switch (name) {
    case 'arrow-back':
      return <ArrowLeft {...iconProps} />;
    case 'arrow-forward':
      return <ArrowRight {...iconProps} />;
    case 'arrow-up':
      return <ArrowUp {...iconProps} />;
    case 'shield-checkmark':
    case 'shield-checkmark-outline':
      return <ShieldCheck {...iconProps} />;
    case 'people-outline':
    case 'users':
      return <Users {...iconProps} />;
    case 'chatbubble-ellipses':
    case 'chatbubble-ellipses-outline':
      return <MessageCircle {...iconProps} />;
    case 'calendar-outline':
    case 'calendar':
      return <Calendar {...iconProps} />;
    case 'book-outline':
      return <BookOpen {...iconProps} />;
    case 'document-text':
    case 'document-text-outline':
      return <FileText {...iconProps} />;
    case 'git-branch-outline':
    case 'git-branch':
      return <GitBranch {...iconProps} />;
    case 'person':
    case 'person-outline':
      return <User {...iconProps} />;
    case 'star':
      return <Star {...iconProps} fill={color || '#D99B00'} />;
    case 'videocam':
    case 'videocam-outline':
      return <Video {...iconProps} />;
    case 'call':
    case 'call-outline':
      return <Phone {...iconProps} />;
    case 'mic':
      return <Mic {...iconProps} />;
    case 'volume-high':
      return <Volume2 {...iconProps} />;
    case 'leaf':
    case 'leaf-outline':
      return <Leaf {...iconProps} />;
    case 'heart':
    case 'heart-outline':
      return <Heart {...iconProps} />;
    case 'sun':
    case 'sunny-outline':
      return <Sun {...iconProps} />;
    case 'building':
    case 'business-outline':
      return <Building {...iconProps} />;
    case 'activity':
    case 'medkit-outline':
      return <Activity {...iconProps} />;
    case 'alert-circle':
    case 'alert-circle-outline':
      return <AlertCircle {...iconProps} />;
    case 'wifi-outline':
      return <Wifi {...iconProps} />;
    case 'notifications-outline':
      return <Bell {...iconProps} />;
    case 'chevron-forward':
      return <ChevronRight {...iconProps} />;
    case 'language-outline':
      return <Globe {...iconProps} />;
    default:
      return <MessageCircle {...iconProps} />;
  }
}
