import { requireNativeModule, requireOptionalNativeModule } from 'expo-modules-core';
import SmsRetrieverModuleMock from './SmsRetrieverModuleMock';

const moduleName = 'SmsRetriever';
var module;

if ((globalThis.expo?.modules?.[moduleName] ?? null) != null){
    module = requireNativeModule(moduleName);
} else {
    module = new SmsRetrieverModuleMock();
}

export default module;