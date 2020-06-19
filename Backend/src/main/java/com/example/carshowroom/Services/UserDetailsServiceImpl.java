package com.example.carshowroom.Services;

import com.example.carshowroom.Repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    private UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if(userRepo.findByUsername(username).isEmpty())
        {
            throw new UsernameNotFoundException(username);
        }
        else
        {
            return userRepo.findByUsername(username).get();
        }
    }
}
