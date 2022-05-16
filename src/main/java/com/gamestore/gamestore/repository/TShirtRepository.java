package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TShirtRepository extends JpaRepository<TShirt, Integer> {
    List<TShirt> findAllBySize(String size);
    List<TShirt> findAllByColor(String color);
    List<TShirt> findAllByColorAndSize(String color, String size);
}