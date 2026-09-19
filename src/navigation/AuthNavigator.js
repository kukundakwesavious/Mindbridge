import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SplashScreen from '../views/screens/SplashScreen';
import WelcomeScreen from '../views/screens/WelcomeScreen';
import SignUpScreen from '../views/screens/SignUpScreen';
import { colors } from '../theme/theme';
const Stack=createNativeStackNavigator();
export default function AuthNavigator({setSession}) { return <Stack.Navigator initialRouteName="Splash" screenOptions={{headerShown:false,contentStyle:{backgroundColor:colors.background}}}><Stack.Screen name="Splash" component={SplashScreen}/><Stack.Screen name="Welcome" component={WelcomeScreen}/><Stack.Screen name="SignUp">{props=><SignUpScreen {...props} setSession={setSession}/>}</Stack.Screen></Stack.Navigator>; }
