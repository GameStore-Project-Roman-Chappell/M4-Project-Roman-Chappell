package com.gamestore.gamestore.service;

import com.gamestore.gamestore.model.Console;
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

}
