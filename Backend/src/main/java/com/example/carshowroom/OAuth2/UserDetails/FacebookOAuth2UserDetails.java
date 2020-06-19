package com.example.carshowroom.OAuth2.UserDetails;

import java.util.Map;

public class FacebookOAuth2UserDetails extends OAuth2UserDetails
{
    public FacebookOAuth2UserDetails(Map<String, Object> attributes)
    {
        super(attributes);
    }

    @Override
    public String getName()
    {
        return (String) attributes.get("first_name");
    }

    @Override
    public String getSurname()
    {
        return (String) attributes.get("last_name");
    }

    @Override
    public String getEmail()
    {
        return (String) attributes.get("email");
    }
}
