package com.example.carshowroom.Services;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

public class ManageCookies
{
    public static Optional<Cookie> getCookie(HttpServletRequest httpServletRequest, String name)
    {
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals(name))
                {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse httpServletResponse, String name, String value, int maxAge)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        httpServletResponse.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String name)
    {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals(name))
                {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    httpServletResponse.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object object)
    {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> tclass)
    {
        return tclass.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}
