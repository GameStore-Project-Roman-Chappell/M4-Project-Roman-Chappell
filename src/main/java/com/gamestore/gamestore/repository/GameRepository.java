package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findByTitle(String title);

    List<Game> findByTitleLike(String title);
    List<Game> findByStudio(String studio);
    List<Game> findByEsrbRating(String esrbRating);
}