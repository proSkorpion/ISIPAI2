package com.example.carshowroom.OAuth2.User;

import com.example.carshowroom.Database.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OAuthUserImp implements OAuth2User
{
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public OAuthUserImp(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static OAuthUserImp create(User user)
    {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new OAuthUserImp(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static OAuthUserImp create(User user, Map<String, Object> attributes)
    {
        OAuthUserImp userPrincipal = OAuthUserImp.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getName()
    {
        return id.toString();
    }

    public void setAttributes(Map<String, Object> attributes)
    {
        this.attributes = attributes;
    }


}
