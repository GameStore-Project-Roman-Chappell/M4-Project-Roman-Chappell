package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.ProcessingFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingFeeRepository extends JpaRepository<ProcessingFee, String> {
    ProcessingFee findByProductType(String productType);
}