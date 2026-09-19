import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { colors } from '../../theme/theme';
import { Card, Header, PrimaryButton, Screen } from '../components/UI';
import { languages } from '../../models/content';
import { useProfileViewModel } from '../../viewmodels/useProfileViewModel';
export default function LanguageScreen({navigation,session,setSession}) { const vm=useProfileViewModel({session,setSession}); const [selected,setSelected]=React.useState(session.language||'English'); return <Screen><Header title="Choose your language" subtitle="You can change this later" onBack={()=>navigation.goBack()}/>{languages.map(l=><Card key={l} onPress={()=>setSelected(l)} style={[s.option,selected===l&&s.active]}><View style={s.radio}>{selected===l?<View style={s.dot}/>:null}</View><Text style={s.text}>{l}</Text></Card>)}<PrimaryButton title="Save language" loading={vm.saving} onPress={async()=>{await vm.update({language:selected});navigation.goBack();}}/></Screen> }
const s=StyleSheet.create({option:{flexDirection:'row',alignItems:'center',gap:12},active:{borderColor:colors.primary,backgroundColor:'#F3F7FF'},radio:{width:22,height:22,borderRadius:11,borderWidth:2,borderColor:colors.primary,alignItems:'center',justifyContent:'center'},dot:{width:10,height:10,borderRadius:5,backgroundColor:colors.primary},text:{fontSize:14,fontWeight:'800',color:colors.text}});
