package com.example.carshowroom.Repositories;

import com.example.carshowroom.Database.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenBlackListRepo extends JpaRepository<TokenBlackList, Long>
{
    Optional<TokenBlackList> findByToken(String token);
}
