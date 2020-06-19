package com.example.carshowroom.Services;

import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest
{
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private ValidationService validationService;

    private Optional<User> getUserFromDatabase()
    {
        return  Optional.of(new User("Arcmistrz", "adsa2233@interia.pl"));
    }

    private Optional<User> getNullUserFromDatabaseByEmail()
    {
        return Optional.ofNullable(null);
    }

    @Test
    void should_name_be_invalid()
    {
        //given
        String nullName = "";
        String tooLongName = "Adammmmmmmmmmmmmmmmmmmmmmm";
        String invalidName = "Adam%";
        //when
        String message = validationService.validateName(nullName);
        String message2 = validationService.validateName(tooLongName);
        String message3 = validationService.validateName(invalidName);
        //then
        Assertions.assertEquals(message, "Musisz wpisać imię");
        Assertions.assertEquals(message2, "Twoje imię jest za długie");
        Assertions.assertEquals(message3, "Twoje imię jest błędne");
    }

    @Test
    void should_name_be_valid()
    {
        //given
        String validName = "Michał";
        //when
        String message = validationService.validateName(validName);
        //then
        Assertions.assertEquals(message, null);
    }

    @Test
    void should_surname_be_invalid()
    {
        //given
        String nullSurname = "";
        String tooLongSurname = "Wójcikkkkkkkkkkkkkkkkkkkkk";
        String invalidSurname = "Wójcik!2@";
        //when
        String message = validationService.validateSurname(nullSurname);
        String message2 = validationService.validateSurname(tooLongSurname);
        String message3 = validationService.validateSurname(invalidSurname);
        //then
        Assertions.assertEquals(message, "Musisz wpisać nazwisko");
        Assertions.assertEquals(message2, "Twoje nazwisko jest za długie");
        Assertions.assertEquals(message3, "Twoje nazwisko jest błędne");
    }

    @Test
    void should_surname_be_valid()
    {
        //given
        String validSurname = "Wójcik";
        //when
        String message = validationService.validateSurname(validSurname);
        //then
        Assertions.assertEquals(message, null);
    }

    @Test
    void should_email_be_invalid()
    {
        //given
        given(userRepo.findByEmail(Mockito.any(String.class))).willReturn(getUserFromDatabase());
        //when
        String message = validationService.validateEmail("adsa2233@interia.pl");
        String message2 = validationService.validateEmail("aaaaaaaaaaaaadsa2233@interia.pl");
        String message3 = validationService.validateEmail("");
        //then
        Assertions.assertEquals(message, "Użytkownik o takim emailu już istnieje");
        Assertions.assertEquals(message2, "Podałeś za długi email");
        Assertions.assertEquals(message3, "Musisz wpisać email użytkownika");
    }


    @Test
    void should_email_be_valid()
    {
        //given
        given(userRepo.findByEmail(Mockito.any(String.class))).willReturn(getNullUserFromDatabaseByEmail());
        //when
        String message = validationService.validateEmail("adsa2222@interia.pl");
        //then
        Assertions.assertEquals(message, null);
    }

    @Test
    void should_username_be_invalid()
    {
        //given
        given(userRepo.findByUsername(Mockito.anyString())).willReturn(getUserFromDatabase());
        //when
        String message = validationService.validateUsername("Arcmistrz");
        String message2 = validationService.validateUsername("Warkaaaaaaaaaaaaaaaaa");
        String message3 = validationService.validateUsername("");
        //then
        Assertions.assertEquals(message, "Użytkownik o takim nicku już istnieje");
        Assertions.assertEquals(message2, "Podałeś za długi login");
        Assertions.assertEquals(message3, "Musisz wpisać login użytkownika");
    }


    @Test
    void should_username_be_valid()
    {
        //given
        given(userRepo.findByUsername(Mockito.anyString())).willReturn(getNullUserFromDatabaseByEmail());
        //when
        String message = validationService.validateUsername("ZippZapp");
        //then
        Assertions.assertEquals(message, null);
    }

    @Test
    void should_password_be_invalid()
    {
        //given
        String password = "kielce10";
        String password2 = "1kielce";
        String password3 = "kielce";
        String password4 = "Kielce";
        String password5 = "kie lce10";
        String password6 = "";
        String password7 = "Kielce111111111111111";
        String invalidPasswordMessage = "Hasło musi zaczynąć się od litery, posiadać jedną cyfrę, posiadać jedną dużą literę," +
                " składać się z przynajmniej 8 znaków oraz nie można użyć spacji";
        //when
        String message = validationService.validatePassword(password);
        String message2 = validationService.validatePassword(password2);
        String message3 = validationService.validatePassword(password3);
        String message4 = validationService.validatePassword(password4);
        String message5 = validationService.validatePassword(password5);
        String message6 = validationService.validatePassword(password6);
        String message7 = validationService.validatePassword(password7);
        //then
        Assertions.assertEquals(message, invalidPasswordMessage);
        Assertions.assertEquals(message2, invalidPasswordMessage);
        Assertions.assertEquals(message3, invalidPasswordMessage);
        Assertions.assertEquals(message4, invalidPasswordMessage);
        Assertions.assertEquals(message5, invalidPasswordMessage);
        Assertions.assertEquals(message6, "Musisz wpisać hasło");
        Assertions.assertEquals(message7, "Hasło jest za długie");
    }

    @Test
    void should_password_be_valid()
    {
        //giver
        String password = "Kielce10";
        //when
        String message = validationService.validatePassword(password);
        //then
        Assertions.assertEquals(message, null);
    }
}