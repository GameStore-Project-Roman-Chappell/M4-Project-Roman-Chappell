package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findByTitle(String title);

    @Query("from Game g where g.title like %:title%")
    List<Game> findByTitleLike(@Param("title") String title);

    List<Game> findByStudio(String studio);
    List<Game> findByEsrbRating(String esrbRating);
}