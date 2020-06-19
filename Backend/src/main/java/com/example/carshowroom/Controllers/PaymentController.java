package com.example.carshowroom.Controllers;

import com.example.carshowroom.Data.OrderDetails;
import com.example.carshowroom.Data.PaymentErrors;
import com.example.carshowroom.Data.PaypalData;
import com.example.carshowroom.Database.Invoice;
import com.example.carshowroom.Services.InvoiceService;
import com.example.carshowroom.Services.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;

@RestController
@CrossOrigin
@RequestMapping("/paypal")
public class PaymentController
{

    private PayPalService payPalService;
    private InvoiceService invoiceService;
    private APIContext apiContext;

    public PaymentController(PayPalService payPalService, InvoiceService invoiceService, APIContext apiContext)
    {
        this.payPalService = payPalService;
        this.invoiceService = invoiceService;
        this.apiContext = apiContext;
    }

    @PostMapping("/pay")
    public PaypalData pay(@RequestBody OrderDetails orderDetails, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest )
    {
        //todo pass good url
        PaymentErrors paymentErrors = null;
        try
        {
            paymentErrors = invoiceService.validateFormData(orderDetails);
            if(!paymentErrors.isErrorsEmpty())
            {
                throw new Exception("Niewłasciwe dane w formularzu");
            }
            Long invoiceId = invoiceService.createInvoice(orderDetails);
            Payment payment = payPalService.createPayment(orderDetails, invoiceId);
            for(Links link: payment.getLinks())
            {
                if(link.getRel().equals("approval_url"))
                {
                    return new PaypalData(link.getHref());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new PaypalData(null, paymentErrors);
    }

    @GetMapping("/pay/cancel")
    public String cancelPay(@RequestParam Long invoiceId ,HttpServletResponse httpServletResponse)
    {
        //todo pass good url
        invoiceService.cancelInvoice(invoiceId);
        httpServletResponse.setHeader("Location", "http://localhost:4200/");
        httpServletResponse.setStatus(302);
        return "redirect:localhost:4200/";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             HttpServletResponse httpServletResponse)
    {
        //todo pass good url
        try
        {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved"))
            {
                httpServletResponse.setHeader("Location", "http://localhost:4200/");
                httpServletResponse.setStatus(302);
                return "redirect:localhost:4200/";
            }
        } catch (PayPalRESTException e)
        {
            httpServletResponse.setHeader("Location", "http://localhost:4200/");
            httpServletResponse.setStatus(302);
        }
        return "redirect:localhost:4200/";
    }
}
