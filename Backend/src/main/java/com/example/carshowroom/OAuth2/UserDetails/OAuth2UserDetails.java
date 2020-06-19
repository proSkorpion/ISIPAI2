package com.example.carshowroom.OAuth2.UserDetails;

import java.util.Map;

public abstract class OAuth2UserDetails
{
    protected Map<String, Object> attributes;

    public OAuth2UserDetails(Map<String, Object> attributes)
    {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    public abstract String getName();

    public abstract String getSurname();

    public abstract String getEmail();
}
