package com.example.carshowroom.Services;

import com.example.carshowroom.Configs.OAuthProperties;
import com.example.carshowroom.Database.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class JwtGeneratorServiceTest
{
    private  User user;
    private  JwtGeneratorService jwtGeneratorService;

    @Autowired
    private OAuthProperties oAuthProperties;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    private void setUp()
    {
        jwtGeneratorService = new JwtGeneratorService(oAuthProperties);
        user = new User("Arcmistrz", "adsa2233@interia.pl");
    }

    @Test
    void should_generate_token() throws Exception
    {
        //given
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //when
        String token = jwtGeneratorService.generateToken(user);
        //then
        Assertions.assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("https://localhost:443/car/get/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void should_validate_token()
    {
        //given
        String token = jwtGeneratorService.generateToken(user);
        //when
        boolean isTokenValid = jwtGeneratorService.validateToken(token, user);
        //then
        Assertions.assertEquals(isTokenValid, true);
    }

    @Test
    void should_not_validate_token()
    {
        //given
        User user2 = new User("Fikasz", "adsa3344@interia.pl");
        String token = jwtGeneratorService.generateToken(user);
        //when
        boolean isTokenValid = jwtGeneratorService.validateToken(token, user2);
        //then
        Assertions.assertEquals(isTokenValid, false);
    }


    @Test
    void should_extract_email_from_jwt()
    {
        //given
        String token = jwtGeneratorService.generateToken(user);
        //when
        String email = jwtGeneratorService.extractEmail(token);
        //then
        Assertions.assertEquals(email, "adsa2233@interia.pl");
    }

    @Test
    void should_extract_expire_date()
    {
        //given
        String token = jwtGeneratorService.generateToken(user);
        //when
        Date expireDate = jwtGeneratorService.extractExpireDate(token);
        //then
        Assertions.assertNotNull(expireDate);
    }

}