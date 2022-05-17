package com.gamestore.gamestore.controller;


import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.exception.UnprocessableRequestException;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tshirts")
public class TShirtsController {

    @Autowired
    private ServiceLayer serviceLayer;
    
    @RequestMapping(method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getAllTShirts(@RequestParam(required = false) String color,
                                      @RequestParam(required=false) String size) {
        if (color != null && size != null) {
            return serviceLayer.findTShirtsByColorAndSize(color, size);
        } else if (color != null) {
            return serviceLayer.findTShirtsByColor(color);
        }else if (size != null) {
            return serviceLayer.findTShirtsBySize(size);
        }else {
            return serviceLayer.findAllTShirts();
        }
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TShirt findTShirtById(@PathVariable int id) {
        TShirt returnTShirt = serviceLayer.findTShirtById(id);
        if(returnTShirt == null){
            throw new ProductNotFoundException("No T Shirt with Id " + id + " was found");
        }
        return returnTShirt;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt createTShirt(@Valid @RequestBody TShirt newTShirt) {

        return serviceLayer.saveTShirt(newTShirt);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable int id) {
         serviceLayer.deleteTShirt(id);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTShirt(@PathVariable int id, @RequestBody TShirt updatedTShirt) {
        if (updatedTShirt.getId() == 0) {
            updatedTShirt.setId(id);
        }
        if (updatedTShirt.getId() != id) {
            throw new UnprocessableRequestException("T-Shirt Id in request must match URL path id");
        }
        serviceLayer.updateTShirt(updatedTShirt);
    }
}
