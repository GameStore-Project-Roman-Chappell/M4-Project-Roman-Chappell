package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Console;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsoleRepository extends JpaRepository<Console, Integer> {
    List<Console> findByManufacturer(String manufacturer);
}