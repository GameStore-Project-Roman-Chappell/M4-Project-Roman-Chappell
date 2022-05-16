package com.gamestore.gamestore.controller;

import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GamesController {

    @Autowired
    private ServiceLayer serviceLayer;


    @RequestMapping(value="/games", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return serviceLayer.findAllGames();
    }

    @RequestMapping(value="/games/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Game findGameById(@PathVariable int id) {
        Game returnGame = serviceLayer.findGame(id);
        return returnGame;
    }

    @RequestMapping(value = "games", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return serviceLayer.saveGame(game);
    }

    @RequestMapping(value="games/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        serviceLayer.deleteGame(id);
    }

    @RequestMapping(value="games/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateGame(@PathVariable int id, @RequestBody Game updatedGame) {
        if (updatedGame.getId() == 0) {
            updatedGame.setId(id);
        }
        if (updatedGame.getId() != id) {
            throw new Exception("Game Id in request must match URL path id");
        }
        serviceLayer.updateGame(updatedGame);
    }
}
