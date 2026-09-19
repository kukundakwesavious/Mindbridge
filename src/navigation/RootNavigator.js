import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import AuthNavigator from './AuthNavigator';
import MainTabs from './MainTabs';
import CounsellorListScreen from '../views/screens/CounsellorListScreen';
import CounsellorDetailScreen from '../views/screens/CounsellorDetailScreen';
import BookingScreen from '../views/screens/BookingScreen';
import ChatScreen from '../views/screens/ChatScreen';
import ContentDetailScreen from '../views/screens/ContentDetailScreen';
import ReferralDetailScreen from '../views/screens/ReferralDetailScreen';
import LanguageScreen from '../views/screens/LanguageScreen';
import CrisisScreen from '../views/screens/CrisisScreen';
import PeerSupportScreen from '../views/screens/PeerSupportScreen';
import SessionRoomScreen from '../views/screens/SessionRoomScreen';
import { colors } from '../theme/theme';
const Stack=createNativeStackNavigator();
export default function RootNavigator({session,setSession}) {
  if(!session) return <AuthNavigator setSession={setSession}/>;
  return <Stack.Navigator screenOptions={{headerShown:false,contentStyle:{backgroundColor:colors.background}}}>
    <Stack.Screen name="Main">{props=><MainTabs {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="Counsellors" component={CounsellorListScreen}/>
    <Stack.Screen name="CounsellorDetail" component={CounsellorDetailScreen}/>
    <Stack.Screen name="Booking">{props=><BookingScreen {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="Chat">{props=><ChatScreen {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="ContentDetail" component={ContentDetailScreen}/>
    <Stack.Screen name="ReferralDetail">{props=><ReferralDetailScreen {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="Language">{props=><LanguageScreen {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="Crisis" component={CrisisScreen}/>
    <Stack.Screen name="PeerSupport">{props=><PeerSupportScreen {...props} session={session} setSession={setSession}/>}</Stack.Screen>
    <Stack.Screen name="SessionRoom" component={SessionRoomScreen}/>
  </Stack.Navigator>;
}
