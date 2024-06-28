# Expo SMS Retriever

![Static Badge](https://img.shields.io/badge/Kotlin-8A2BE2?logo=kotlin&logoColor=white) ![Static Badge](https://img.shields.io/badge/Expo-8A2BE2?logo=expo&logoColor=white&color=blue) ![Static Badge](https://img.shields.io/badge/Android-8A2BE2?logo=android&logoColor=white&color=green) ![NPM Version](https://img.shields.io/npm/v/expo-sms-retriever?logoColor=green) ![NPM License](https://img.shields.io/npm/l/expo-sms-retriever)

Native expo SMS retriever module for android

With the SMS Retriever API, you can automate SMS-based user verification in your Android app. This eliminates the need for users to manually enter verification codes and avoids the necessity of additional app permissions.


# Installation
Add the package to your npm dependencies

```
npm install expo-sms-retriever
```

OR
```
yarn add expo-sms-retriever
```

# Usage

### getHash
```ts
async getHash() : Promise<string>
```
Returns a unique identifier that helps verify the authenticity of the app that receives the SMS. 

### addListener
```ts
addListener(callback) -> callback(<MessageEventPayload>)
```
Add a listener callback. The callback function is called with `MessageEventPayload`. 

### removeListener
```ts
removeListener()
```
Removes all listener.

### start
```ts
async start() : Promise<boolean>
```

Starts the Google GSM [SmsRetrieverClient](https://developers.google.com/android/reference/com/google/android/gms/auth/api/phone/SmsRetrieverClient) and registers required Native listeners.
It waits for a matching SMS message until timeout (5 minutes).

> NOTE: This method is called by `addListener` so isn't needed to be called if you want to get the message / OTP once. If you want to keep recieving messages you can call the start method inside the callback to reinitate the `SmsRetrieverClient`

### stop
```ts
async stop() : Promise<boolean>
```
Unregisters the Native listeners.

> NOTE: This method is called by `removeListener` method so isn't needed to be called seperately.


### MessageEventPayload
```js
{
    message: "<string>", // actual message
    otp: "<string" // parsed otp value
}
```

### Example
Check out the basic usage, we used `getHash` method to get hash, added a listener updated the otp and finally clear the listeners.

```tsx
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
```

### Behavoiur in Expo Go
If the module could not find the `SmsRetrieverModule` then it will use a mocked module. The Hash will be set to `NOHASH` and OTP value to `123456`

You may modify `/src/SmsRetrieverModuleMock.ts` in case you want to change these values.


# License
Distributed under the MIT License. See LICENSE for more information.

