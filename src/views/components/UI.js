import React, { useState } from 'react';
import { ActivityIndicator, Image, Pressable, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { colors, radius, spacing, typography } from '../../theme/theme';
import { fallbackAssets } from '../../data/media';

export function Screen({ children, scroll = true, style, contentStyle }) {
  return scroll ? <ScrollView style={[styles.screen, style]} contentContainerStyle={[styles.content, contentStyle]} showsVerticalScrollIndicator={false}>{children}</ScrollView> : <View style={[styles.screen, style]}>{children}</View>;
}

export function Header({ title, subtitle, onBack, right }) {
  return <View style={styles.header}>
    {onBack ? <Pressable accessibilityRole="button" accessibilityLabel="Go back" onPress={onBack} style={styles.back}><Ionicons name="arrow-back" size={21} color={colors.text}/></Pressable> : null}
    <View style={{ flex: 1 }}><Text style={styles.headerTitle}>{title}</Text>{subtitle ? <Text style={styles.headerSub}>{subtitle}</Text> : null}</View>
    {right}
  </View>;
}

export function Logo({ small = false }) {
  return <View style={styles.logo}><Image source={require('../../../assets/mindbridge-logo.png')} style={small ? styles.logoImageSmall : styles.logoImage} resizeMode="contain"/><Text style={[styles.logoText, small && { fontSize: 19 }]}>Mind<Text style={{ color: colors.secondary }}>Bridge</Text></Text></View>;
}

export function SmartImage({ source, fallback, style, resizeMode='cover', ...props }) {
  const [failed, setFailed] = useState(false);
  const uri = typeof source === 'string' ? source : source?.uri;
  const fallbackSource = fallback || fallbackAssets.heroTherapy;
  if (!uri) return <Image source={source || fallbackSource} style={style} resizeMode={resizeMode} {...props}/>;
  return <Image source={{ uri }} style={style} resizeMode={resizeMode} onError={() => setFailed(true)} {...props} />;
}

export function Avatar({ image, initials, size = 54 }) {
  return image ? <SmartImage source={image} fallback={fallbackAssets.therapySession} style={{ width: size, height: size, borderRadius: size * .3 }} /> : <View style={{ width: size, height: size, borderRadius: size * .3, backgroundColor: colors.primarySoft, alignItems: 'center', justifyContent: 'center' }}><Text style={{ fontWeight: '900', color: colors.primary }}>{initials}</Text></View>;
}

export function PrimaryButton({ title, onPress, icon, loading, danger = false, disabled = false }) {
  return <Pressable disabled={disabled || loading} onPress={onPress} style={({ pressed }) => [styles.primary, danger && { backgroundColor: colors.red }, disabled && { opacity: .45 }, pressed && { transform: [{ scale: .985 }] }]}>{loading ? <ActivityIndicator color="white"/> : <>{icon ? <Ionicons name={icon} size={18} color="white" style={{ marginRight: 8 }}/> : null}<Text style={styles.primaryText}>{title}</Text></>}</Pressable>;
}

export function OutlineButton({ title, onPress, icon, danger = false }) {
  return <Pressable onPress={onPress} style={[styles.outline, danger && { borderColor: '#E9B7B4' }]}>{icon ? <Ionicons name={icon} size={18} color={danger ? colors.red : colors.primary} style={{ marginRight: 8 }}/> : null}<Text style={[styles.outlineText, danger && { color: colors.red }]}>{title}</Text></Pressable>;
}

export function Card({ children, style, onPress }) {
  const C = onPress ? Pressable : View;
  return <C onPress={onPress} style={({ pressed }) => [styles.card, style, onPress && pressed && { opacity: .86 }]}>{children}</C>;
}

export function IconBox({ icon, tone='blue', size=46 }) {
  const map = { blue: [colors.primarySoft, colors.primary], green: [colors.secondarySoft, colors.secondary], purple: [colors.lavender, '#7255C7'], orange: [colors.peach, '#D8782A'], pink: ['#FFEAF0', '#C74E78'], red: [colors.redSoft, colors.red], gold: [colors.yellow, '#A97800'], teal: ['#E5F8F6', '#198E83'] };
  const [bg, fg] = map[tone] || map.blue;
  return <View style={{ width: size, height: size, borderRadius: 15, backgroundColor: bg, alignItems: 'center', justifyContent: 'center' }}><Ionicons name={icon} size={size * .48} color={fg}/></View>;
}

export function Field({ label, placeholder, value, onChangeText, secureTextEntry, keyboardType, autoCapitalize='sentences' }) {
  return <View style={{ marginBottom: spacing.md }}><Text style={styles.fieldLabel}>{label}</Text><TextInput value={value} onChangeText={onChangeText} placeholder={placeholder} placeholderTextColor="#9BAABC" secureTextEntry={secureTextEntry} keyboardType={keyboardType} autoCapitalize={autoCapitalize} style={styles.input}/></View>;
}

export function SearchField({ value, onChangeText, placeholder='Search' }) {
  return <View style={styles.search}><Ionicons name="search-outline" size={19} color={colors.muted}/><TextInput value={value} onChangeText={onChangeText} placeholder={placeholder} placeholderTextColor="#8C98AA" style={styles.searchInput}/>{value ? <Pressable onPress={() => onChangeText('')}><Ionicons name="close-circle" size={18} color={colors.muted}/></Pressable> : null}</View>;
}

export function Pill({ label, active, onPress }) {
  return <Pressable onPress={onPress} style={[styles.pill, active && { backgroundColor: colors.primary, borderColor: colors.primary }]}><Text style={[styles.pillText, active && { color: 'white' }]}>{label}</Text></Pressable>;
}

export function EmptyState({ icon='calendar-outline', title, text }) {
  return <View style={styles.empty}><IconBox icon={icon} tone="blue" size={62}/><Text style={styles.emptyTitle}>{title}</Text><Text style={styles.emptyText}>{text}</Text></View>;
}

export const styles = StyleSheet.create({
  screen: { flex: 1, backgroundColor: colors.background }, content: { padding: spacing.md, paddingBottom: 38 },
  header: { flexDirection: 'row', alignItems: 'center', paddingBottom: spacing.md, gap: 10 }, back: { width: 40, height: 40, borderRadius: 20, alignItems: 'center', justifyContent: 'center', backgroundColor: colors.surface, borderWidth: 1, borderColor: colors.border },
  headerTitle: { ...typography.h2 }, headerSub: { ...typography.caption, marginTop: 3 },
  logo: { flexDirection: 'row', alignItems: 'center', gap: 8 }, logoImage: { width: 43, height: 43 }, logoImageSmall: { width: 31, height: 31 }, logoText: { fontSize: 23, fontWeight: '900', color: colors.primary },
  primary: { minHeight: 52, borderRadius: 15, backgroundColor: colors.primary, flexDirection: 'row', alignItems: 'center', justifyContent: 'center', paddingHorizontal: 18 }, primaryText: { color: 'white', fontSize: 15, fontWeight: '800' },
  outline: { minHeight: 50, borderRadius: 15, borderWidth: 1.2, borderColor: colors.primary, flexDirection: 'row', alignItems: 'center', justifyContent: 'center', paddingHorizontal: 18, backgroundColor: colors.surface }, outlineText: { color: colors.primary, fontSize: 14, fontWeight: '800' },
  card: { backgroundColor: colors.surface, borderRadius: radius.md, padding: spacing.md, borderWidth: 1, borderColor: colors.border, marginBottom: spacing.sm, shadowColor: colors.shadow, shadowOpacity: .045, shadowRadius: 14, shadowOffset: { width: 0, height: 5 }, elevation: 1 },
  fieldLabel: { fontSize: 13, fontWeight: '800', color: colors.text, marginBottom: 7 }, input: { height: 52, borderWidth: 1, borderColor: colors.border, borderRadius: 13, backgroundColor: colors.surface, paddingHorizontal: 15, color: colors.text, fontSize: 14 },
  search: { height: 49, borderRadius: 14, backgroundColor: colors.surface, borderWidth: 1, borderColor: colors.border, paddingHorizontal: 14, flexDirection: 'row', alignItems: 'center', gap: 8, marginBottom: 12 }, searchInput: { flex: 1, fontSize: 14, color: colors.text },
  pill: { paddingHorizontal: 15, paddingVertical: 9, borderRadius: radius.pill, borderWidth: 1, borderColor: colors.border, backgroundColor: colors.surface, marginRight: 8 }, pillText: { color: colors.muted, fontWeight: '800', fontSize: 12 },
  empty: { alignItems: 'center', paddingVertical: 55, paddingHorizontal: 25 }, emptyTitle: { ...typography.h3, marginTop: 14 }, emptyText: { ...typography.caption, textAlign: 'center', marginTop: 7, lineHeight: 20 }
});
