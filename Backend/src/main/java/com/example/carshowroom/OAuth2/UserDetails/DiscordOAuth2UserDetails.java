package com.example.carshowroom.OAuth2.UserDetails;

import java.util.Map;

public class DiscordOAuth2UserDetails extends OAuth2UserDetails
{

    public DiscordOAuth2UserDetails(Map<String, Object> attributes)
    {
        super(attributes);
    }

    @Override
    public String getName()
    {
        return attributes.get("username").toString();
    }

    @Override
    public String getSurname()
    {
        return attributes.get("username").toString();
    }

    @Override
    public String getEmail()
    {
        return attributes.get("email").toString();
    }
}
