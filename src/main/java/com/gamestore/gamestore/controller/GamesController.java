package com.gamestore.gamestore.controller;

import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.exception.UnprocessableRequestException;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    private ServiceLayer serviceLayer;


    @RequestMapping( method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames(@RequestParam(required = false) String title,
                                  @RequestParam(required=false) String studio,
                                  @RequestParam(required=false) String esrb) {

        if (title != null) {
            return serviceLayer.findGamesByTitleLike(title);
        }
        if (studio != null) {
            return serviceLayer.findGamesByStudio(studio);
        }
        if (esrb != null) {
            return serviceLayer.findGamesByESRB(esrb);
        }
        return serviceLayer.findAllGames();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Game findGameById(@PathVariable int id) {
        Game game = serviceLayer.findGame(id);
        if(game == null){
            throw new ProductNotFoundException("No Game with Id " + id + " was found");
        }
        return game;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return serviceLayer.saveGame(game);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        serviceLayer.deleteGame(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateGame(@PathVariable int id, @RequestBody Game updatedGame) {
        if (updatedGame.getId() == 0) {
            updatedGame.setId(id);
        }
        if (updatedGame.getId() != id) {
            throw new UnprocessableRequestException("Game Id in request must match URL path id");
        }
        serviceLayer.updateGame(updatedGame);
    }
}
