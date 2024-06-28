
class SmsRetrieverModuleMock {
    __expo_module_name__ = 'SmsRetriever';
    getHash = () => {return ['NOHASH']}
    start = () => {}
    stop = () => {}
    addListener = (eventName: string, callback) => { callback({message: 'OTP is 123456', otp: '123456'}) }
    removeListener = (eventName: string, callback) => {}
    removeAllListeners = (eventName: string, callback) => {}
    emit = (eventName: string, data: any) => { console.log(eventName, data)}
}

export default SmsRetrieverModuleMock;