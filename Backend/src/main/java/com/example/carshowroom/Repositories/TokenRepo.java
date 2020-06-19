package com.example.carshowroom.Repositories;

import com.example.carshowroom.Database.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long>
{
    Optional<Token> findByValue(String value);
}
