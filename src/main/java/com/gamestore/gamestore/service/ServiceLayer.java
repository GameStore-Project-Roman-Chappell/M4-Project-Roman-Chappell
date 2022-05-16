package com.gamestore.gamestore.service;

import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.repository.ConsoleRepository;
import com.gamestore.gamestore.repository.GameRepository;
import com.gamestore.gamestore.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private TShirtRepository tShirtRepository;
    private GameRepository gameRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, TShirtRepository tShirtRepository, GameRepository gameRepository) {
        this.consoleRepository = consoleRepository;
        this.tShirtRepository = tShirtRepository;
        this.gameRepository = gameRepository;
    }

//    Consoles CRUD
    public Console saveConsole(Console console) {
        return consoleRepository.save(console);
    }

    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }

    public Console findConsole(int id) {
        Optional<Console> console = consoleRepository.findById(id);
        return console.isPresent() ? console.get() : null;
    }

    public void updateConsole(Console console) {
        consoleRepository.save(console);
    }

    public void deleteConsole(int id) {
        consoleRepository.deleteById(id);
    }


//    TShirts CRUD
    //CREATE
    public TShirt saveTShirt(TShirt tShirt) {
        return tShirtRepository.save(tShirt);
    }

    //READ METHODS
    public List<TShirt> findAllTShirts() {
        return tShirtRepository.findAll();
    }

    public List<TShirt> findTShirtsByColorAndSize(String color, String size){
        return tShirtRepository.findAllByColorAndSize(color,size);
    }
    public List<TShirt> findTShirtsByColor(String color){
        return tShirtRepository.findAllByColor(color);
    }
    public List<TShirt> findTShirtsBySize(String size){
        return tShirtRepository.findAllBySize(size);
    }

    public TShirt findTShirtById(int id) {
        Optional<TShirt> tShirt = tShirtRepository.findById(id);
        return tShirt.isPresent() ? tShirt.get() : null;
    }

    // UPDATE METHOD
    public void updateTShirt(TShirt tShirt) {
        tShirtRepository.save(tShirt);
    }
    // DELETE METHOD
    public void deleteTShirt(int id) {
        tShirtRepository.deleteById(id);
    }

//    Games CRUD
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public Game findGame(int id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.isPresent() ? game.get() : null;
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }
}
