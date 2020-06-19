package com.example.carshowroom.Data;

public class PaypalData
{
    private String link;
    private PaymentErrors paymentErrors;

    public PaypalData()
    {
    }

    public PaypalData(String link)
    {
        this.link = link;
    }

    public PaypalData(String link, PaymentErrors paymentErrors)
    {
        this.link = link;
        this.paymentErrors = paymentErrors;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public PaymentErrors getPaymentErrors()
    {
        return paymentErrors;
    }

    public void setPaymentErrors(PaymentErrors paymentErrors)
    {
        this.paymentErrors = paymentErrors;
    }
}
