package com.example.carshowroom.OAuth2.UserDetails;

import com.example.carshowroom.Data.AuthProvider;

import javax.naming.AuthenticationException;
import java.util.Map;

public class OAuth2UserDetailsFactory
{
    public static OAuth2UserDetails getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws AuthenticationException
    {
        if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString()))
        {
            return new FacebookOAuth2UserDetails(attributes);
        }
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString()))
        {
            return new GoogleOAuth2UserDetails(attributes);
        }
        if(registrationId.equalsIgnoreCase(AuthProvider.discord.toString()))
        {
            return new DiscordOAuth2UserDetails(attributes);
        }
        else
        {
            throw new AuthenticationException("Nie możesz sie zalogować za pomocą:" + registrationId);
        }
    }
}
