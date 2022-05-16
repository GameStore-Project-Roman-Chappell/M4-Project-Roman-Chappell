package com.gamestore.gamestore.controllers;


import com.gamestore.gamestore.model.TShirt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TShirtsController {
    
    @RequestMapping(value="/TShirts", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getAllTShirts() {
        return serviceLayer.findAllTShirts();
    }

    @RequestMapping(value="/TShirts/{id}", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TShirt findTShirtById(@PathVariable int id) {
        TShirt returnTShirt = serviceLayer.findTShirt(id);
        return returnTShirt;
    }

    @RequestMapping(value = "TShirts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt createTShirt(@RequestBody TShirt newTShirt) {
        return serviceLayer.saveTShirt(newTShirt);
    }

    @RequestMapping(value="TShirts/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable int id) {
        return serviceLayer.deleteTShirt(id);
    }

    @RequestMapping(value="TShirts/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTShirt(@PathVariable int id, @RequestBody TShirt updatedTShirt) {
        if (updatedTShirt.getId() == 0) {
            updatedTShirt.setId(id);
        }
        if (updatedTShirt.getId() != id) {
            throw new InvalidRequestException("Request ID must match table id");
        }
        serviceLayer.updateTShirt(updatedTShirt);
    }
}
