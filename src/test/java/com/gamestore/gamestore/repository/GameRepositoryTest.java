package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() {
        gameRepository.deleteAll();
    }

    @Test
    public void shouldAddAndDeleteGameFromRepository() {

        Game game = new Game("GTA V", "18+", "Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world." , "Rockstar Games",new BigDecimal(29.95).setScale(2,BigDecimal.ROUND_HALF_DOWN), 5);
        game = gameRepository.save(game);

        Optional<Game> gameOpt = gameRepository.findById(game.getId());

        assertEquals(gameOpt.get(), game);

        gameRepository.deleteById(game.getId());

        gameOpt = gameRepository.findById(game.getId());

        assertFalse(gameOpt.isPresent());
    }

    @Test
    public void shouldFindAllGamesAsListFromRepository() {
        Game game1 = new Game("GTA V", "18+", "Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world." , "Rockstar Games",new BigDecimal("29.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        game1 = gameRepository.save(game1);


        Game game2 = new Game("Elden Ring", "M", "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between." , "Bandai Namco",new BigDecimal("58.99").setScale(2, RoundingMode.HALF_DOWN), 12);
        game2 = gameRepository.save(game2);

        List<Game> gameList = gameRepository.findAll();
        assertEquals(2, gameList.size());
        assertEquals( game1, gameList.get(0));
        assertEquals( game2, gameList.get(1));

    }

    @Test
    public void shouldUpdateGame() {
        Game game = new Game("Elden Ring", "M", "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between." , "Bandai Namco",new BigDecimal("58.99").setScale(2, RoundingMode.HALF_DOWN), 12);
        game = gameRepository.save(game);

        game.setEsrbRating("18+");
        game.setQuantity(53);
        gameRepository.save(game);

        Optional<Game> gameOpt = gameRepository.findById(game.getId());
        assertEquals(game, gameOpt.get());
    }

    @Test
    public void shouldReturnGamesByStudio() {
        Game game1 = new Game("GTA V", "18+", "Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world." , "Rockstar Games",new BigDecimal("29.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        gameRepository.save(game1);

        Game game2 = new Game("Elden Ring", "M", "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between." , "Bandai Namco",new BigDecimal("58.99").setScale(2, RoundingMode.HALF_DOWN), 12);
        gameRepository.save(game2);

        Game game3 = new Game("Red Dead Redemption", "M", "Western Shooter RPG." , "Rockstar Games",new BigDecimal("49.95").setScale(2, RoundingMode.HALF_DOWN), 4);
        gameRepository.save(game3);


        List<Game> gameList = gameRepository.findByStudio("Rockstar Games");
        assertEquals(2, gameList.size());
        assertEquals( game1, gameList.get(0));
        assertEquals( game3, gameList.get(1));
    }

    @Test
    public void shouldReturnGamesByESRB() {
        Game game1 = new Game("GTA V", "18+", "Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world." , "Rockstar Games",new BigDecimal("29.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        gameRepository.save(game1);

        Game game2 = new Game("Elden Ring", "M", "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between." , "Bandai Namco",new BigDecimal("58.99").setScale(2, RoundingMode.HALF_DOWN), 12);
        gameRepository.save(game2);

        Game game3 = new Game("Red Dead Redemption", "M", "Western Shooter RPG." , "Rockstar Games",new BigDecimal("49.95").setScale(2, RoundingMode.HALF_DOWN), 4);
        gameRepository.save(game3);


        List<Game> gameList = gameRepository.findByEsrbRating("M");
        assertEquals(2, gameList.size());
        assertEquals( game2, gameList.get(0));
        assertEquals( game3, gameList.get(1));
    }

    @Test
    public void shouldReturnGamesByTitleLike() {
        Game game1 = new Game("GTA V", "18+", "Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world." , "Rockstar Games",new BigDecimal("29.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        gameRepository.save(game1);

        Game game2 = new Game("Elden Ring", "M", "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between." , "Bandai Namco",new BigDecimal("58.99").setScale(2, RoundingMode.HALF_DOWN), 12);
        gameRepository.save(game2);

        Game game3 = new Game("Red Dead Redemption", "M", "Western Shooter RPG." , "Rockstar Games",new BigDecimal("49.95").setScale(2, RoundingMode.HALF_DOWN), 4);
        gameRepository.save(game3);

        Game game4 = new Game("GTA Vice City", "18+", "The next Grand Theft Auto series installment." , "Rockstar Games",new BigDecimal("19.95").setScale(2, RoundingMode.HALF_DOWN), 2);
        gameRepository.save(game4);



        List<Game> gameList = gameRepository.findByTitleLike("GTA");
        assertEquals(2, gameList.size());
        assertEquals( game1, gameList.get(0));
        assertEquals( game4, gameList.get(1));

        List<Game> gameList2 = gameRepository.findByTitleLike("Ring");
        assertEquals(1, gameList2.size());
        assertEquals( game2, gameList2.get(0));
    }
}