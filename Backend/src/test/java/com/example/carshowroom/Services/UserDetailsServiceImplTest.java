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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest
{
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void should_load_user_by_username()
    {
        //given
        given(userRepo.findByUsername(Mockito.anyString())).willReturn(getUserFromDatabaseByUsername());
        //when
        UserDetails user = userDetailsService.loadUserByUsername("Arcmistrz");
        //then
        Assertions.assertEquals(user.getUsername(), "Arcmistrz");
    }

    private Optional<User> getUserFromDatabaseByUsername()
    {
        return Optional.of(new User("Arcmistrz", "adsa2233@interia.pl"));
    }

    @Test
    void should__not_load_user_by_username()
    {
        //given
        given(userRepo.findByUsername(Mockito.anyString())).willReturn(getNullUserFromDatabaseByUsername());
        //then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {userDetailsService.loadUserByUsername("Arcmistrz");} );
    }

    private Optional<User> getNullUserFromDatabaseByUsername()
    {
        return Optional.ofNullable(null);
    }

}