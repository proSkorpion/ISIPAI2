package com.example.carshowroom.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManageCookiesTest
{
    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @Test
    void should_get_cookie()
    {
        //given
        given(servletRequest.getCookies()).willReturn(getCookies());
        //when
        Optional<Cookie> cookie = ManageCookies.getCookie(servletRequest, "jwt");
        //then
        Assertions.assertEquals(cookie.isEmpty(), false);
        Assertions.assertEquals(cookie.get().getName(), "jwt");
    }

    @Test
    void should_not_get_cookie()
    {
        //given
        given(servletRequest.getCookies()).willReturn(getCookies());
        //when
        Optional<Cookie> cookie = ManageCookies.getCookie(servletRequest, "class");
        //then
        Assertions.assertEquals(cookie.isEmpty(), true);
    }

    private Cookie[] getCookies()
    {
        Cookie cookie = new Cookie("name", "value");
        Cookie cookie2 = new Cookie("jwt", "sdadsadsadsdasd.sdadsadsdasd.saddsdads");
        Cookie[] cookies = {cookie, cookie2};
        return cookies;
    }

    @Test
    void should_add_cookie()
    {
        //given
        String name = "jwt";
        String value = "dsdasdsa.sdadsad.sdasdasd";
        int maxAge = 180;
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
        //when
        ManageCookies.addCookie(servletResponse, name, value, maxAge);
        verify(servletResponse).addCookie(captor.capture());
        Cookie cookie2 = captor.getValue();
        //then
        Assertions.assertEquals(cookie2.getName(), cookie.getName());
        Assertions.assertEquals(cookie2.getValue(), cookie.getValue());
        Assertions.assertEquals(cookie2.getMaxAge(), cookie.getMaxAge());
    }

    @Test
    void should_delete_cookie()
    {
        //given
        given(servletRequest.getCookies()).willReturn(getCookies());
        Cookie cookie = new Cookie("jwt", "dsadasdas.dsadsads.dsadsadas");
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
        //when
        ManageCookies.deleteCookie(servletRequest, servletResponse, "jwt");
        verify(servletResponse).addCookie(captor.capture());
        Cookie cookie2 = captor.getValue();
        //then
        Assertions.assertEquals(cookie2.getValue(), cookie.getValue());
        Assertions.assertEquals(cookie2.getName(), cookie.getName());
        Assertions.assertEquals(cookie2.getMaxAge(), cookie.getMaxAge());
    }

    @Test
    void should_not_delete_cookie()
    {
        //given
        given(servletRequest.getCookies()).willReturn(getCookies());
        ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
        //when
        ManageCookies.deleteCookie(servletRequest, servletResponse, "class");
        //then
        Assertions.assertThrows(WantedButNotInvoked.class, () -> verify(servletResponse).addCookie(captor.capture()));
    }


    @Test
    void should_serialize_object()
    {
        //given
        String name = "Adam";
        //when
        String serialize = ManageCookies.serialize(name);
        //then
        Assertions.assertEquals(serialize, Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(name)));
    }

    @Test
    void should_deserialize_object()
    {
        //given
        String name = "Adam";
        String encode = Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(name));
        Cookie cookie = new Cookie("name", encode);
        //when
        String deserialize = ManageCookies.deserialize(cookie, String.class);
        //then
        Assertions.assertEquals(deserialize, "Adam");
    }

}