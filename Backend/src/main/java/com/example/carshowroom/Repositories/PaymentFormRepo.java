package com.example.carshowroom.Repositories;

import com.example.carshowroom.Database.PaymentForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentFormRepo extends JpaRepository<PaymentForm, Long>
{
    Optional<PaymentForm> findByName(String name);
}
