package com.gamestore.gamestore.controller;


import com.gamestore.gamestore.model.Invoice;
import com.gamestore.gamestore.viewmodel.InvoiceViewModel;
import com.gamestore.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
public class InvoiceController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(value="/purchase", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel requestItemPurchase(@RequestBody Invoice invoice){
        return serviceLayer.createInvoiceReturnViewModel(invoice);
    }

    @RequestMapping(value="/invoices", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel viewInvoices(){
        return null; //serviceLayer.showAllInvoiceViewModels();
    }
}