import React, { useEffect, useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { colors } from '../../theme/theme';
import { Avatar, Header, PrimaryButton, Screen, SmartImage } from '../components/UI';
import { media } from '../../data/media';

export default function SessionRoomScreen({ navigation, route }) {
  const { counsellor, mode='Video call' } = route.params;
  const [seconds, setSeconds] = useState(0);
  useEffect(()=>{ const t=setInterval(()=>setSeconds(v=>v+1),1000); return()=>clearInterval(t); },[]);
  const mins=String(Math.floor(seconds/60)).padStart(2,'0'); const secs=String(seconds%60).padStart(2,'0');
  return <Screen scroll={false} contentStyle={{padding:16}}><Header title={mode} subtitle={counsellor.name} onBack={()=>navigation.goBack()}/><View style={s.stage}><SmartImage source={counsellor.image || media.therapySession} fallback={require('../../../assets/crisis-illustration.png')} style={s.image}/><View style={s.overlay}><Text style={s.live}>{mode==='Video call'?'LIVE SESSION':'VOICE SESSION'}</Text><Text style={s.timer}>{mins}:{secs}</Text><Text style={s.name}>{counsellor.name}</Text></View></View><View style={s.controls}><Control icon={mode==='Video call'?'videocam':'volume-high'} label={mode==='Video call'?'Camera':'Speaker'}/><Control icon="mic" label="Mute"/><Control icon="chatbubble-ellipses" label="Chat"/></View><PrimaryButton title="End session" danger icon="call" onPress={()=>navigation.goBack()}/><Text style={s.note}>Demo session room. A production release should connect this surface to an authenticated, encrypted voice/video provider.</Text></Screen>
}
function Control({icon,label}){return <View style={s.control}><Pressable style={s.circle}><Ionicons name={icon} size={22} color={colors.text}/></Pressable><Text style={s.controlText}>{label}</Text></View>}
const s=StyleSheet.create({stage:{height:430,borderRadius:25,overflow:'hidden',backgroundColor:'#0C1B33',position:'relative'},image:{width:'100%',height:'100%',opacity:.78},overlay:{position:'absolute',left:18,right:18,bottom:20},live:{alignSelf:'flex-start',backgroundColor:'#C83B35',color:'#fff',fontSize:10,fontWeight:'900',paddingHorizontal:9,paddingVertical:5,borderRadius:8},timer:{color:'#fff',fontSize:34,fontWeight:'900',marginTop:8},name:{color:'#fff',fontSize:14,fontWeight:'800',marginTop:2},controls:{flexDirection:'row',justifyContent:'space-around',paddingVertical:22},control:{alignItems:'center',gap:6},circle:{width:56,height:56,borderRadius:28,backgroundColor:colors.surface,alignItems:'center',justifyContent:'center',borderWidth:1,borderColor:colors.border},controlText:{fontSize:10,color:colors.muted,fontWeight:'800'},note:{fontSize:10,lineHeight:15,color:colors.muted,textAlign:'center',marginTop:12}});
