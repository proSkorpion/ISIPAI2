package com.example.carshowroom.Services;

import com.example.carshowroom.Data.*;
import com.example.carshowroom.Database.Token;
import com.example.carshowroom.Database.TokenBlackList;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.RoleRepo;
import com.example.carshowroom.Repositories.TokenBlackListRepo;
import com.example.carshowroom.Repositories.TokenRepo;
import com.example.carshowroom.Repositories.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService
{
    private UserRepo userRepo;
    private MailService mailService;
    private TokenRepo tokenRepo;
    private PasswordEncoder passwordEncoder;
    private ValidationService validationService;
    private AuthenticationManager authenticationManager;
    private RoleRepo roleRepo;
    private UserDetailsServiceImpl userDetailsService;
    private JwtGeneratorService jwtGeneratorService;
    private TokenBlackListRepo tokenBlackListRepo;


    public UserService(UserRepo userRepo, MailService mailService, TokenRepo tokenRepo,
                       PasswordEncoder passwordEncoder, ValidationService validationService,
                       AuthenticationManager authenticationManager, RoleRepo roleRepo, UserDetailsServiceImpl userDetailsService,
                       JwtGeneratorService jwtGeneratorService, TokenBlackListRepo tokenBlackListRepo)
    {
        this.userRepo = userRepo;
        this.mailService = mailService;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.authenticationManager = authenticationManager;
        this.roleRepo = roleRepo;
        this.userDetailsService = userDetailsService;
        this.jwtGeneratorService = jwtGeneratorService;
        this.tokenBlackListRepo = tokenBlackListRepo;
    }

    public ValidationErrors addUser(User user)
    {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.setNameError(validationService.validateName(user.getName()));
        validationErrors.setSurnameError(validationService.validateSurname(user.getSurname()));
        validationErrors.setEmailError(validationService.validateEmail(user.getEmail()));
        validationErrors.setUsernameError(validationService.validateUsername(user.getUsername()));
        validationErrors.setPasswordError(validationService.validatePassword(user.getPassword()));

        if (validationErrors.getEmailError() == null &&
                validationErrors.getUsernameError() == null &&
                validationErrors.getPasswordError() == null &&
                validationErrors.getNameError() == null &&
                validationErrors.getSurnameError() == null)
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(roleRepo.findByName("ROLE_USER").get());
            user.setProvider(AuthProvider.local);
            userRepo.save(user);
            return validationErrors;
        }
        else
        {
            return validationErrors;
        }
    }


    public void sendToken(User user) throws MessagingException
    {
        Optional<User> databaseUser = userRepo.findByUsername(user.getUsername());
        if (!databaseUser.isEmpty())
        {
            String tokenValue = UUID.randomUUID().toString();
            Token token = new Token();
            token.setValue(tokenValue);
            token.setUser(databaseUser.get());
            tokenRepo.save(token);

            String tokenUrl = "https://localhost:443/user/token?value=" + tokenValue;
            String content = "Uprzejmie dziękujemy za zarejestrowanie się na naszym serwisie carShowroom.pl." +
                    " Prosze potwierdzic e-mail klikajac na podany link: " + tokenUrl;
            mailService.sendMail(user.getEmail(), "Potwierdz konto", content, true);
        }
    }

    public void confirmAccount(String value)
    {
        Optional<Token> token = tokenRepo.findByValue(value);
        if(token.isPresent())
        {
            User user = token.get().getUser();
            user.setEnabled(true);
            userRepo.save(user);
            tokenRepo.delete(token.get());
        }
    }

    public AuthenticationResponse logInUser(AuthenticationRequest authenticationRequest)
    {
        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e)
        {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse("Nieprawidłowe hasło lub login", null);
            return authenticationResponse;
        }
        Optional<User> user = userRepo.findByUsername(authenticationRequest.getUsername());
        if(user.isPresent())
        {
            String token = jwtGeneratorService.generateToken(user.get());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, user.get().getUsername());
            return authenticationResponse;
        }
        else
        {
            throw new RuntimeException("Nie ma takiego uzytkownika w bazie danych");
        }
    }

    public void sendFeedback(FeedbackData feedbackData)
    {
        try
        {
            mailService.sendMail("webdesigneradam3@gmail.com",
                    feedbackData.getEmail(),
                    feedbackData.getSubject(),
                    feedbackData.getContent(),
                    false);
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    public void logoutUser(HttpServletRequest httpServletRequest)
    {
        String jwt = null;
        String header = httpServletRequest.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer "))
        {
            jwt = header.substring(7);
        }

        if(SecurityContextHolder.getContext().getAuthentication() != null)
        {
            TokenBlackList tokenBlackList = new TokenBlackList(jwt);
            tokenBlackListRepo.save(tokenBlackList);
        }
    }

    public void arrangeTestDrive(MeetingForm meetingForm)
    {
        String message = "Moje imie i nazwisko to "+ meetingForm.getName()+" "+ meetingForm.getSurname()+" a numer telefonu to "+ meetingForm.getPhone()+
                ". Chciałbym się umówić na jazdę " +
                "próbną samochodem "+ meetingForm.getCarName()+". Proszę o szybką odpowiedź";
        try
        {
            mailService.sendMail("webdesigneradam3@gmail.com",
                    meetingForm.getEmail(),
                    "Pyatnie o jazdę próbną",
                    message,
                    false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
