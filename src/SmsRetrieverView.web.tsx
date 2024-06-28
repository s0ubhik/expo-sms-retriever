import * as React from 'react';

import { SmsRetrieverViewProps } from './SmsRetriever.types';

export default function SmsRetrieverView(props: SmsRetrieverViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
