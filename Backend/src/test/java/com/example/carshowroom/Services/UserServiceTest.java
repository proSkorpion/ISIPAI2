package com.example.carshowroom.Services;

import com.example.carshowroom.Data.AuthenticationRequest;
import com.example.carshowroom.Data.AuthenticationResponse;
import com.example.carshowroom.Data.FeedbackData;
import com.example.carshowroom.Data.ValidationErrors;
import com.example.carshowroom.Database.Role;
import com.example.carshowroom.Database.Token;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.RoleRepo;
import com.example.carshowroom.Repositories.TokenRepo;
import com.example.carshowroom.Repositories.UserRepo;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
@EnableAsync
class UserServiceTest
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepo tokenRepo;


    private Role role;
    private User setUpUser;
    private Token token;

    @BeforeEach
    private void setUp()
    {
        role = new Role("ROLE_USER");
        roleRepo.save(role);
        setUpUser = new User(role);
        setUpUser.setEmail("adsa3344@interia.pl");
        setUpUser.setUsername("Arcmistrz");
        setUpUser.setEnabled(true);
        setUpUser.setPassword(passwordEncoder.encode("Kielce10"));
        userRepo.save(setUpUser);
        token = new Token();
        token.setUser(setUpUser);
        token.setValue(UUID.randomUUID().toString());
        tokenRepo.save(token);
    }

    @AfterEach
    private void clearDatabase()
    {
        tokenRepo.deleteAll();
        roleRepo.deleteAll();
        userRepo.deleteAll();
    }


    @Test
    void should_add_user()
    {
        //given
        User user = new User("Halo", "adsa2233@interia.pl");
        user.setPassword("Kielce10");
        user.setName("Adam");
        user.setSurname("Słaby");
        //when
        ValidationErrors validationErrors = userService.addUser(user);
        Optional<User> userInDb = userRepo.findById(user.getUserId());
        //then
        Assertions.assertEquals(validationErrors.getNameError(), null);
        Assertions.assertEquals(validationErrors.getSurnameError(), null);
        Assertions.assertEquals(validationErrors.getEmailError(), null);
        Assertions.assertEquals(validationErrors.getPasswordError(), null);
        Assertions.assertEquals(validationErrors.getUsernameError(), null);
        Assertions.assertEquals(userInDb.isPresent(), true);
    }

    @Test
    void should_not_add_user()
    {
        //given
        User user = new User("Halo", "adsa3344@interia.pl");
        user.setPassword("Kielce10");
        //when
        ValidationErrors validationErrors = userService.addUser(user);
        //then
        Assertions.assertNotEquals(validationErrors.getEmailError(), null);
        Assertions.assertNull(user.getUserId());
    }


    @Test
    void should_log_in_user()
    {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("Arcmistrz");
        authenticationRequest.setPassword("Kielce10");
        //when
        AuthenticationResponse authenticationResponse = userService.logInUser(authenticationRequest);
        //then
        Assertions.assertNotEquals(authenticationResponse.getJwt(), "Nie prawiłowe hasło lub login");
    }

    @Test
    void should_not_log_in_user()
    {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("Arcmistrz");
        authenticationRequest.setPassword("Kielce12");
        //when
        AuthenticationResponse authenticationResponse = userService.logInUser(authenticationRequest);
        //then
        Assertions.assertEquals(authenticationResponse.getJwt(), "Nieprawidłowe hasło lub login");
    }

    @Test
    void should_create_and_send_token() throws MessagingException
    {
        //given
        userService.sendToken(setUpUser);
        //when
        List<Token> tokenList = tokenRepo.findAll();
        //then
        Assert.assertThat(tokenList, Matchers.hasSize(2));
    }

    @Test
    void should_not_create_and_send_token() throws MessagingException
    {
        //given
        userService.sendToken(new User(role));
        //when
        List<Token> tokenList = tokenRepo.findAll();
        //then
        Assert.assertThat(tokenList, Matchers.hasSize(1));
    }

    @Test
    void should_confirm_account()
    {
        //given
        User user = userRepo.findById(setUpUser.getUserId()).get();
        user.setEnabled(false);
        userRepo.save(user);
        //when
        userService.confirmAccount(token.getValue());
        //then
        Assertions.assertEquals(userRepo.findByUserId(setUpUser.getUserId()).get().isEnabled(), true);
        Assertions.assertEquals(tokenRepo.findByValue(token.getValue()).isEmpty(), true);
    }

    @Test
    void should_not_confirm_account()
    {
        //given
        User user = userRepo.findById(setUpUser.getUserId()).get();
        user.setEnabled(false);
        userRepo.save(user);
        //when
        userService.confirmAccount(UUID.randomUUID().toString());
        //then
        Assertions.assertEquals(userRepo.findByUserId(setUpUser.getUserId()).get().isEnabled(), false);
        Assertions.assertEquals(tokenRepo.findByValue(token.getValue()).isPresent(), true);
    }

    @Test
    void should_send_feedback()
    {
        //given
        FeedbackData feedbackData = new FeedbackData();
        feedbackData.setSubject("Jakis temat");
        feedbackData.setEmail("adsa3344@interia.pl");
        feedbackData.setContent("Jakis content");
        //then
        Assertions.assertDoesNotThrow(() -> userService.sendFeedback(feedbackData));
    }
}