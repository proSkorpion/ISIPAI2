package com.example.carshowroom.OAuth2.UserDetails;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class OAuth2UserDetailsFactoryTest
{

    @Test
    void should_get_oauth2_user_info_from_facebook() throws AuthenticationException
    {
        //given
        String registrationId = "facebook";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "adsa3344@interia.pl");
        attributes.put("first_name", "Adam");
        //when
        OAuth2UserDetails oAuth2UserInfo = OAuth2UserDetailsFactory.getOAuth2UserInfo(registrationId, attributes);
        //then
        Assertions.assertEquals(FacebookOAuth2UserDetails.class.isInstance(oAuth2UserInfo), true);
        Assertions.assertEquals(oAuth2UserInfo.getEmail(), "adsa3344@interia.pl");
        Assertions.assertEquals(oAuth2UserInfo.getName(), "Adam");
    }

    @Test
    void should_get_oauth2_user_info_from_discord() throws AuthenticationException
    {
        //given
        String registrationId = "discord";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", "Arcmistrz");
        attributes.put("email", "adsa3344@interia.pl");
        //when
        OAuth2UserDetails oAuth2UserInfo = OAuth2UserDetailsFactory.getOAuth2UserInfo(registrationId, attributes);
        //then
        Assertions.assertEquals(DiscordOAuth2UserDetails.class.isInstance(oAuth2UserInfo), true);
        Assertions.assertEquals(oAuth2UserInfo.getName(), "Arcmistrz");
        Assertions.assertEquals(oAuth2UserInfo.getEmail(), "adsa3344@interia.pl");
    }

    @Test
    void should_get_oauth2_user_info_from_google() throws AuthenticationException
    {
        //given
        String registrationId = "google";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("given_name", "Adam");
        attributes.put("email", "adsa3344@interia.pl");
        //when
        OAuth2UserDetails oAuth2UserInfo = OAuth2UserDetailsFactory.getOAuth2UserInfo(registrationId, attributes);
        //then
        Assertions.assertEquals(GoogleOAuth2UserDetails.class.isInstance(oAuth2UserInfo), true);
        Assertions.assertEquals(oAuth2UserInfo.getName(), "Adam");
        Assertions.assertEquals(oAuth2UserInfo.getEmail(), "adsa3344@interia.pl");
    }

    @Test
    void should_not_get_oauth2_user_info_and_throw_exception()
    {
        //given
        String registrationId = "twitter";
        Map<String, Object> attributes = new HashMap<>();
        //then
        Assertions.assertThrows(AuthenticationException.class, () -> OAuth2UserDetailsFactory.getOAuth2UserInfo(registrationId, attributes));
    }
}