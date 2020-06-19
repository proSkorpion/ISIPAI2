package com.example.carshowroom.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthProperties
{
    @Value("${app.auth.tokenSecret}")
    private String secretToken;

    @Value("${app.auth.tokenExpirationMsec}")
    private String tokenExpirationMsec;

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String authorizedRedirectUri;


    public String getSecretToken()
    {
        return secretToken;
    }

    public String getTokenExpirationMsec()
    {
        return tokenExpirationMsec;
    }

    public String getAuthorizedRedirectUri()
    {
        return authorizedRedirectUri;
    }
}
