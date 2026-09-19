import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';
import RootNavigator from './src/navigation/RootNavigator';
import { appRepository } from './src/services/appRepository';
import { colors } from './src/theme/theme';

export default function App(){
  const [session,setSession]=useState(undefined);
  useEffect(()=>{appRepository.getSession().then(saved=>setSession(saved || null));},[]);
  if(session===undefined) return <View style={{flex:1,backgroundColor:colors.background}}/>;
  return <NavigationContainer><StatusBar style="dark"/><RootNavigator session={session} setSession={setSession}/></NavigationContainer>;
}
