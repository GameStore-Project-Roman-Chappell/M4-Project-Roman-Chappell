package com.gamestore.gamestore.controller;

import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ConsolesController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(value="/consoles", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles() {
        return serviceLayer.findAllConsoles();
    }

    @RequestMapping(value="/consoles/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Console findConsoleById(@PathVariable int id) {
        Console returnConsole = serviceLayer.findConsole(id);
        return returnConsole;
        }

    @RequestMapping(value = "consoles", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody Console console) {
        return serviceLayer.saveConsole(console);
    }

    @RequestMapping(value="consoles/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id) {
        serviceLayer.deleteConsole(id);
    }

    @RequestMapping(value="consoles/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateConsole(@PathVariable int id, @RequestBody Console updatedConsole) {
        if (updatedConsole.getId() == 0) {
            updatedConsole.setId(id);
        }
        if (updatedConsole.getId() != id) {
            throw new Exception("Console Id in request must match URL path id");

        }
        serviceLayer.updateConsole(updatedConsole);
    }

}


