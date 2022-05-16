package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.SalesTaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesTaxRateRepository extends JpaRepository<SalesTaxRate, String> {
    SalesTaxRate findByState(String state);
}