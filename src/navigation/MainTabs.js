import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Ionicons } from '@expo/vector-icons';
import HomeScreen from '../views/screens/HomeScreen';
import SessionsScreen from '../views/screens/SessionsScreen';
import LibraryScreen from '../views/screens/LibraryScreen';
import ReferralsScreen from '../views/screens/ReferralsScreen';
import ProfileScreen from '../views/screens/ProfileScreen';
import { colors } from '../theme/theme';
const Tab = createBottomTabNavigator();
const icons = { Home:'home-outline', Sessions:'calendar-outline', Library:'book-outline', Referrals:'git-branch-outline', Profile:'person-outline' };
export default function MainTabs({ session, setSession }) {
  return <Tab.Navigator screenOptions={({route}) => ({ headerShown:false, tabBarActiveTintColor:colors.primary, tabBarInactiveTintColor:colors.muted, tabBarStyle:{height:72,paddingTop:7,paddingBottom:10,borderTopColor:colors.border,backgroundColor:colors.surface}, tabBarLabelStyle:{fontSize:10,fontWeight:'800'}, tabBarIcon:({color,size})=><Ionicons name={icons[route.name]} size={size} color={color}/> })}>
    <Tab.Screen name="Home">{props=><HomeScreen {...props} session={session}/>}</Tab.Screen>
    <Tab.Screen name="Sessions">{props=><SessionsScreen {...props} session={session}/>}</Tab.Screen>
    <Tab.Screen name="Library" component={LibraryScreen}/>
    <Tab.Screen name="Referrals">{props=><ReferralsScreen {...props} session={session} setSession={setSession}/>}</Tab.Screen>
    <Tab.Screen name="Profile">{props=><ProfileScreen {...props} session={session} setSession={setSession}/>}</Tab.Screen>
  </Tab.Navigator>;
}
