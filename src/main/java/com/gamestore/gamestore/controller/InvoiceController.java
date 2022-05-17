package com.gamestore.gamestore.controller;


import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.model.Invoice;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(value="/buy", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Invoice requestItemPurchase(@RequestBody Invoice invoice){
        return serviceLayer.createInvoice(invoice);
    }
}