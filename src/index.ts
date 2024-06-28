import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to SmsRetriever.web.ts
// and on native platforms to SmsRetriever.ts
import SmsRetrieverModule from './SmsRetrieverModule';
import SmsRetrieverView from './SmsRetrieverView';
import { ChangeEventPayload, SmsRetrieverViewProps } from './SmsRetriever.types';

// Get the native constant value.
export const PI = SmsRetrieverModule.PI;

export function hello(): string {
  return SmsRetrieverModule.hello();
}

export async function setValueAsync(value: string) {
  return await SmsRetrieverModule.setValueAsync(value);
}

const emitter = new EventEmitter(SmsRetrieverModule ?? NativeModulesProxy.SmsRetriever);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { SmsRetrieverView, SmsRetrieverViewProps, ChangeEventPayload };
