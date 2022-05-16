package com.gamestore.gamestore.controllers;

import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GamesController {

    @RequestMapping(value="/games", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return ServiceLayer.findAllGames();
    }

    @RequestMapping(value="/games/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Game findGameById(@PathVariable int id) {
        Game returnGame = ServiceLayer.findGame(id);
        return returnGame;
    }

    @RequestMapping(value = "games", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return ServiceLayer.saveGame(game);
    }

    @RequestMapping(value="games/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        return ServiceLayer.deleteGame(id);
    }

    @RequestMapping(value="games/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateGame(@PathVariable int id, @RequestBody Game updatedGame) {
        if (updatedGame.getId() == 0) {
            updatedGame.setId(id);
        }
        if (updatedGame.getId() != id) {
            throw new InvalidRequestException("Request ID must match table id");
        }
        ServiceLayer.updategame(updatedGame);
    }
}
