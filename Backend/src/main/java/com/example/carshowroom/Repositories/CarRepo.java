package com.example.carshowroom.Repositories;


import com.example.carshowroom.Database.Car;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarRepo extends JpaRepository<Car, Long>
{
   Optional<Set<Car>> findByInvoice_InvoiceId(Long invoiceId);
   List<Car> findAll(Sort sort);
}
