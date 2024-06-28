import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { SmsRetrieverViewProps } from './SmsRetriever.types';

const NativeView: React.ComponentType<SmsRetrieverViewProps> =
  requireNativeViewManager('SmsRetriever');

export default function SmsRetrieverView(props: SmsRetrieverViewProps) {
  return <NativeView {...props} />;
}
