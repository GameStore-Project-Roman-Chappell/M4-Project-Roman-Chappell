package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}