package com.example.carshowroom.Services;

import com.example.carshowroom.Data.AuthProvider;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.OAuth2.User.OAuthUserImp;
import com.example.carshowroom.Repositories.RoleRepo;
import com.example.carshowroom.Repositories.UserRepo;
import com.example.carshowroom.OAuth2.UserDetails.OAuth2UserDetails;
import com.example.carshowroom.OAuth2.UserDetails.OAuth2UserDetailsFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class OAuth2UserServiceImp extends DefaultOAuth2UserService
{
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public OAuth2UserServiceImp(UserRepo userRepo, RoleRepo roleRepo)
    {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try
        {
            return processOAuth2User(userRequest, oAuth2User);
        }
        catch (Exception ex)
        {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws AuthenticationException
    {
        OAuth2UserDetails oAuth2UserDetails =
                OAuth2UserDetailsFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserDetails.getEmail()))
        {
            throw new AuthenticationException("Email nie zostal znaleziony w atrybutach dostarczonych przez dostawce OAuth2");
        }

        Optional<User> userOptional = userRepo.findByEmail(oAuth2UserDetails.getEmail());
        User user;
        if (userOptional.isPresent())
        {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())))
            {
                throw new AuthenticationException("Wyglada na to ze zalogowales sie za pomoca konta " +
                        user.getProvider() + ". Uzyj twojego konta " + user.getProvider() +
                        " do zalogowania sie");
            }
            user = updateExistingUser(user, oAuth2UserDetails);
        }
        else
        {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserDetails);
        }

        return OAuthUserImp.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetails oAuth2UserDetails) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setName(oAuth2UserDetails.getName());
        user.setSurname(oAuth2UserDetails.getSurname());
        user.setEmail(oAuth2UserDetails.getEmail());
        user.setEnabled(true);
        user.setRole(roleRepo.findByName("ROLE_USER").get());
        userRepo.save(user);
        user.setUsername(oAuth2UserDetails.getName() + user.getUserId());
        return userRepo.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserDetails oAuth2UserDetails) {
        existingUser.setEmail(oAuth2UserDetails.getEmail());
        return userRepo.save(existingUser);
    }
}
