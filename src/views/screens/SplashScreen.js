import React, { useEffect } from 'react';
import { Image, StyleSheet, Text, View } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { colors } from '../../theme/theme';

export default function SplashScreen({ navigation }) {
  useEffect(() => { const t = setTimeout(() => navigation.replace('Welcome'), 1600); return () => clearTimeout(t); }, [navigation]);
  return <LinearGradient colors={['#F4F9FF', '#EAF3FF', '#FFFFFF']} style={s.root}>
    <View style={s.logoWrap}><Image source={require('../../../assets/app-icon.png')} style={s.icon}/><Text style={s.brand}>Mind<Text style={{color: colors.secondary}}>Bridge</Text></Text><Text style={s.tag}>Better minds. Stronger communities.</Text></View>
    <Text style={s.bottom}>Mental wellbeing support for Ugandan youth</Text>
  </LinearGradient>;
}
const s=StyleSheet.create({root:{flex:1,alignItems:'center',justifyContent:'center',padding:30},logoWrap:{alignItems:'center'},icon:{width:112,height:112,borderRadius:30,marginBottom:22},brand:{fontSize:38,fontWeight:'900',color:colors.primary},tag:{fontSize:14,color:colors.muted,marginTop:7},bottom:{position:'absolute',bottom:42,fontSize:12,color:colors.muted}});
