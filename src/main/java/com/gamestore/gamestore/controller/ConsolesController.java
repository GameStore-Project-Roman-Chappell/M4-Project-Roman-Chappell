package com.gamestore.gamestore.controller;

import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.exception.UnprocessableRequestException;
import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/consoles")
public class ConsolesController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles(@RequestParam(required = false) String manufacturer) {
         if (manufacturer != null) {
            return serviceLayer.findAllConsolesByManufacturer(manufacturer);
        }else {
            return serviceLayer.findAllConsoles();
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Console findConsoleById(@PathVariable int id) {
        Console returnConsole = serviceLayer.findConsole(id);

        if(returnConsole == null){
            throw new ProductNotFoundException("No Console with Id " + id + " was found");
        }
        return returnConsole;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@Valid @RequestBody Console console) {
        return serviceLayer.saveConsole(console);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id) {
        serviceLayer.deleteConsole(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateConsole(@PathVariable int id, @RequestBody Console updatedConsole) {
        if (updatedConsole.getId() == 0) {
            updatedConsole.setId(id);
        }
        if (updatedConsole.getId() != id) {
            throw new UnprocessableRequestException("Console Id in request must match URL path id");

        }
        serviceLayer.updateConsole(updatedConsole);
    }

}


