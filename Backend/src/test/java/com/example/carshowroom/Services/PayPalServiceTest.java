package com.example.carshowroom.Services;

import com.example.carshowroom.Data.OrderDetails;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PayPalServiceTest
{
    @Autowired
    private PayPalService payPalService;

    @Test
    void should_create_sale_payment() throws PayPalRESTException
    {
        //given
        OrderDetails orderDetails = new OrderDetails(new BigDecimal(10), "sale", "Sale test");
        //when
        Payment payment = payPalService.createPayment(orderDetails, Long.valueOf(1));
        Optional<Links> approval_url = payment.getLinks().stream().filter(links -> links.getRel().equals("approval_url")).findFirst();
        //then
        Assertions.assertEquals(approval_url.isPresent(), true);
    }

    @Test
    void should_create_order_payment() throws PayPalRESTException
    {
        //given
        OrderDetails orderDetails = new OrderDetails(new BigDecimal(20), "order", "Sale test");
        //when
        Payment payment = payPalService.createPayment(orderDetails, Long.valueOf(1));
        Optional<Links> approval_url = payment.getLinks().stream().filter(links -> links.getRel().equals("approval_url")).findFirst();
        //then
        Assertions.assertEquals(approval_url.isPresent(), true);
    }

    @Test
    void should_create_authorize_payment() throws PayPalRESTException
    {
        //given
        OrderDetails orderDetails = new OrderDetails(new BigDecimal(30), "authorize", "Sale test");
        //when
        Payment payment = payPalService.createPayment(orderDetails, Long.valueOf(1));
        Optional<Links> approval_url = payment.getLinks().stream().filter(links -> links.getRel().equals("approval_url")).findFirst();
        //then
        Assertions.assertEquals(approval_url.isPresent(), true);
    }

}