package com.example.carshowroom.Repositories;

import com.example.carshowroom.Database.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice, Long>
{

}
