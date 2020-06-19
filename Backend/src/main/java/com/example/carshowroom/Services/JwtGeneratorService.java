package com.example.carshowroom.Services;

import com.example.carshowroom.Configs.OAuthProperties;
import com.example.carshowroom.Database.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorService
{
    private OAuthProperties OAuthProperties;

    public JwtGeneratorService(OAuthProperties OAuthProperties)
    {
        this.OAuthProperties = OAuthProperties;
    }

    public String extractEmail(String token)
    {
        Claims claims = extractAllClaims(token);
        String email = claims.getSubject();
        return email;
    }

    public Date extractExpireDate(String token)
    {
        Claims claims = extractAllClaims(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate;
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser().setSigningKey(OAuthProperties.getSecretToken()).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpire(String token)
    {
        boolean isExpired = extractExpireDate(token).before(new Date());
        return isExpired;
    }

    public String generateToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(OAuthProperties.getTokenExpirationMsec())))
                .signWith(SignatureAlgorithm.HS256, OAuthProperties.getSecretToken()).compact();
    }

    public boolean validateToken(String token, User user)
    {
        String email = extractEmail(token);
        if(email.equals(user.getEmail()) && !isTokenExpire(token))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
