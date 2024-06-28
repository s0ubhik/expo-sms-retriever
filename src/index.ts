import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

import SmsRetrieverModule from './SmsRetrieverModule';
import { MessageEventPayload } from './SmsRetriever.types';

export async function getHash(): Promise<string> {
  const hash = await SmsRetrieverModule.getHash();
  return (hash.length > 0) ? hash[0] : '';
}

export async function start(): Promise<Boolean> {
  return await SmsRetrieverModule.start();
}

export async function stop(): Promise<Boolean> {
  return await SmsRetrieverModule.stop();
}

const emitter = new EventEmitter(SmsRetrieverModule ?? NativeModulesProxy.SmsRetriever);

export function addListener(listener: (event: MessageEventPayload) => void): Subscription {
  emitter.removeAllListeners('onMessage');
  SmsRetrieverModule.start();
  return emitter.addListener<MessageEventPayload>('onMessage', listener);
}

export function removeListener() {
  SmsRetrieverModule.stop();
  return emitter.removeAllListeners('onMessage');
}

export { MessageEventPayload };