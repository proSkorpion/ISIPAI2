package com.example.carshowroom.Filters;

import com.example.carshowroom.Database.TokenBlackList;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.Repositories.TokenBlackListRepo;
import com.example.carshowroom.Services.JwtGeneratorService;
import com.example.carshowroom.Repositories.UserRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter
{
    private JwtGeneratorService jwtGeneratorService;
    private UserRepo userRepo;
    private TokenBlackListRepo tokenBlackListRepo;

    public JwtFilter(JwtGeneratorService jwtGeneratorService, UserRepo userRepo, TokenBlackListRepo tokenBlackListRepo)
    {
        this.jwtGeneratorService = jwtGeneratorService;
        this.userRepo = userRepo;
        this.tokenBlackListRepo = tokenBlackListRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String header = httpServletRequest.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if(header != null && header.startsWith("Bearer "))
        {
            jwt = header.substring(7);
            email = jwtGeneratorService.extractEmail(jwt);
        }

        Optional<TokenBlackList> token = tokenBlackListRepo.findByToken(jwt);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null && token.isEmpty())
        {
            Optional<User> user = userRepo.findByEmail(email);
            if(!user.isEmpty())
            {
                if(jwtGeneratorService.validateToken(jwt, user.get()))
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(user.get().getEmail(), null,
                                    Collections.singleton(new SimpleGrantedAuthority(user.get().getRole().getName())));
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
