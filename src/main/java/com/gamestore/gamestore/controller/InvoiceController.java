package com.gamestore.gamestore.controller;


import com.gamestore.gamestore.model.Invoice;
import com.gamestore.gamestore.viewmodel.InvoiceViewModel;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class InvoiceController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(value="/purchase", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Invoice requestItemPurchase(@Valid @RequestBody Invoice invoice){
        return serviceLayer.createInvoiceAndReturn(invoice);
    }

    @RequestMapping(value="/invoices", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel viewInvoices(){
        return null; //serviceLayer.showAllInvoiceViewModels();
    }
}