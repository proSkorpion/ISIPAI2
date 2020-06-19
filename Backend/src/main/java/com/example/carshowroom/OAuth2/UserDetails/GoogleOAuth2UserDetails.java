package com.example.carshowroom.OAuth2.UserDetails;

import java.util.Map;

public class GoogleOAuth2UserDetails extends OAuth2UserDetails
{

    public GoogleOAuth2UserDetails(Map<String, Object> attributes)
    {
        super(attributes);
    }

    @Override
    public String getName()
    {
        return attributes.get("given_name").toString();
    }

    @Override
    public String getSurname()
    {
        return attributes.get("family_name").toString();
    }

    @Override
    public String getEmail()
    {
        return  attributes.get("email").toString();
    }
}
