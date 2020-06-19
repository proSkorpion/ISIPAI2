package com.example.carshowroom.Services;

import com.example.carshowroom.Data.OrderDetails;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService
{
    private APIContext apiContext;
    private final String successPaymentUrl = "https://localhost:443/paypal/pay/success";
    private final String cancelPaymentUrl = "https://localhost:443/paypal/pay/cancel?invoiceId=";

    public PayPalService(APIContext apiContext)
    {
        this.apiContext = apiContext;
    }

    public Payment createPayment(OrderDetails orderDetails, Long invoiceId) throws PayPalRESTException
    {
        Amount amount = new Amount();
        amount.setCurrency("PLN");
        amount.setTotal(orderDetails.getTotal().toString());

        Transaction transaction = new Transaction();
        transaction.setDescription(orderDetails.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelPaymentUrl+invoiceId);
        redirectUrls.setReturnUrl(successPaymentUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException
    {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
