package com.example.carshowroom.Services;

import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.UserRepo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService
{
    private UserRepo userRepo;

    public ValidationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String validateName(String name)
    {
        if(name == null || name.length() == 0)
        {
            return "Musisz wpisać imię";
        }
        if(name.length() > 25)
        {
            return "Twoje imię jest za długie";
        }
        if(!name.matches("[\\\\x20.a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{3,25}"))
        {
            return "Twoje imię jest błędne";
        }
        else
        {
            return null;
        }
    }

    public String validateSurname(String surname)
    {
        if(surname == null || surname.length() == 0)
        {
            return "Musisz wpisać nazwisko";
        }
        if(surname.length() > 25)
        {
            return "Twoje nazwisko jest za długie";
        }
        if(!surname.matches("[\\\\x20.a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]{3,25}"))
        {
            return "Twoje nazwisko jest błędne";
        }
        else
        {
            return null;
        }
    }

    public String validateEmail(String email)
    {
        if(email == null || email.length() == 0)
        {
            return "Musisz wpisać email użytkownika";
        }
        if(email.length() > 30)
        {
            return "Podałeś za długi email";
        }
        EmailValidator emailValidator = EmailValidator.getInstance(true, true);
        if(!emailValidator.isValid(email))
        {
            return "Nieprawidłowy email";
        }
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isEmpty())
        {
            return null;
        }
        else
        {
            return "Użytkownik o takim emailu już istnieje";
        }
    }

    public String validateUsername(String login)
    {
        if(login == null || login.length() == 0)
        {
            return "Musisz wpisać login użytkownika";
        }
        if(login.length() > 20)
        {
            return "Podałeś za długi login";
        }
        Optional<User> user = userRepo.findByUsername(login);
        if(user.isEmpty())
        {
            return null;
        }
        else
        {
            return "Użytkownik o takim nicku już istnieje";
        }
    }

    public static String validatePassword(String password)
    {
        if(password == null || password.length() == 0)
        {
            return "Musisz wpisać hasło";
        }
        if(password.length() > 20)
        {
            return "Hasło jest za długie";
        }
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}"))
        {
            return "Hasło musi zaczynąć się od litery, posiadać jedną cyfrę, posiadać jedną dużą literę, składać się z przynajmniej 8 znaków oraz nie można użyć spacji";
        }
        else
        {
            return null;
        }
    }
}
