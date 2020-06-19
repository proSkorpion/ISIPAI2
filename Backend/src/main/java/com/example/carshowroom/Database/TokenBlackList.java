package com.example.carshowroom.Database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class TokenBlackList implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;

    private String token;

    public TokenBlackList()
    {
    }

    public TokenBlackList(String token)
    {
        this.token = token;
    }

    public Long getTokenId()
    {
        return tokenId;
    }

    public void setTokenId(Long tokenId)
    {
        this.tokenId = tokenId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
