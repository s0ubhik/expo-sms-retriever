import { Text, View } from 'react-native';
import { useEffect, useState } from 'react';

import * as SmsRetriever from 'expo-sms-retriever';

export default function App() {
  const [hash, sethash] = useState('');
  const [otp, setotp] = useState('');

  useEffect(() => {
    
    /* get hash */
    SmsRetriever.getHash().then(sethash);

    /* start listening */
    SmsRetriever.addListener((message) => {
      console.log(message);
  
      setotp("OTP is " + message.otp);

      /* stop lsitening */
      SmsRetriever.removeListener();
    });

  }, []);

  return (
    <View style={{flex: 1, backgroundColor: '#fff', alignItems: 'center'}}>
      <Text style={{fontSize: 20, fontWeight: 'bold', marginBottom: 10, marginTop: 60}}>Expo SMS Retriver</Text>
      <Text style={{fontFamily: 'monospace'}}>Author: s0ubhik</Text>
      <Text  style={{fontFamily: 'monospace'}}>github.com/s0ubhik/expo-sms-retriever</Text>
      <Text style={{marginTop: 20, marginBottom: 30, fontFamily: 'monospace'}}>Hash: {hash}</Text>
      <Text style={{fontSize: 17,  fontWeight: 'bold', color: otp.length == 0 ? 'black': 'blue' }}>{otp.length == 0 ? 'Waiting for OTP ...' : otp}</Text>
    </View>
  );
}
