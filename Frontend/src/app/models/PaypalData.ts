import { PaymentErrors } from './PaymentErrors';

export interface PaypalData
{
  link: string;
  paymentErrors: PaymentErrors;
}
