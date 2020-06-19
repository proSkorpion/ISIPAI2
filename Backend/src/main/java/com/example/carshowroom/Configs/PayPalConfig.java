package com.example.carshowroom.Configs;

import com.paypal.base.rest.APIContext;

import java.util.Map;

import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class PayPalConfig
{
    @Value("${paypal.mode}")
    private String mode;

    @Value("${paypal.client.app}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String secretId;


    @Bean
    public APIContext apiContext()
    {
        APIContext apiContext = new APIContext(clientId, secretId, mode);
        return apiContext;
    }
}
