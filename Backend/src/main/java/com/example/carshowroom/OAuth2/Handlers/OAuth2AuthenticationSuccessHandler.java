package com.example.carshowroom.OAuth2.Handlers;

import com.example.carshowroom.OAuth2.Components.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.carshowroom.Configs.OAuthProperties;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.UserRepo;
import com.example.carshowroom.Services.JwtGeneratorService;
import com.example.carshowroom.Services.ManageCookies;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.example.carshowroom.OAuth2.Components.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private JwtGeneratorService jwtGeneratorService;
    private UserRepo userRepo;
    private OAuthProperties OAuthProperties;


    public OAuth2AuthenticationSuccessHandler(HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
                                              JwtGeneratorService jwtGeneratorService, UserRepo userRepo, OAuthProperties OAuthProperties)
    {
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.jwtGeneratorService = jwtGeneratorService;
        this.userRepo = userRepo;
        this.OAuthProperties = OAuthProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted())
        {
            logger.debug("Nie można przenieśc użytkownika pod stronę" + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        Optional<String> redirectUri = ManageCookies.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get()))
        {
            throw new AuthorizationServiceException("Przepraszamy, dostalismy niezautoryzowany adres url i nie mozemy przeprowadzić zauthenitifikowania uzytkownika");
        }

        String targetUrl = redirectUri.orElse("http://localhost:4200/");

        Optional<User> user = userRepo.findByUserId(Long.parseLong(authentication.getName()));
        String token = jwtGeneratorService.generateToken(user.get());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .queryParam("username", user.get().getUsername())
                .build().toUriString();

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response)
    {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri)
    {
        URI clientRedirectUri = URI.create(uri);

        URI authorizedURI = URI.create(OAuthProperties.getAuthorizedRedirectUri());
        if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedURI.getPort() == clientRedirectUri.getPort())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
