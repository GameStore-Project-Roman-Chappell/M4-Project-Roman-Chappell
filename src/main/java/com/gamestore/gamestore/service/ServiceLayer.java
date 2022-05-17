package com.gamestore.gamestore.service;

import com.gamestore.gamestore.model.*;
import com.gamestore.gamestore.repository.*;
import com.gamestore.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private TShirtRepository tShirtRepository;
    private GameRepository gameRepository;
    private InvoiceRepository invoiceRepository;
    private SalesTaxRateRepository taxRateRepository;
    private ProcessingFeeRepository feeRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, TShirtRepository tShirtRepository, GameRepository gameRepository, InvoiceRepository invoiceRepository, SalesTaxRateRepository taxRateRepository, ProcessingFeeRepository feeRepository) {
        this.consoleRepository = consoleRepository;
        this.tShirtRepository = tShirtRepository;
        this.gameRepository = gameRepository;
        this.invoiceRepository = invoiceRepository;
        this.taxRateRepository = taxRateRepository;
        this.feeRepository = feeRepository;
    }
// ------------------------   CONSOLES SECTION   ----------------------//
//    Consoles CRUD
    public Console saveConsole(Console console) {
        return consoleRepository.save(console);
    }

    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }
    public List<Console> findAllConsolesByManufacturer(String manufacturer){
        return consoleRepository.findByManufacturer(manufacturer);
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

// ------------------------   T SHIRTS SECTION   ----------------------//
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
// ------------------------  GAMES SECTION   ----------------------//
//    Games CRUD
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> findGamesByTitleLike(String title){
        return gameRepository.findByTitleLike(title);
    }
    public List<Game> findGamesByStudio(String studio){
        return gameRepository.findByStudio(studio);
    }
    public List<Game> findGamesByESRB(String esrb){
        return gameRepository.findByEsrbRating(esrb);
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
// ------------------------  INVOICE SECTION   ----------------------//
    @Transactional
    public InvoiceViewModel createInvoiceReturnViewModel(Invoice invoice){
        // Add the fee based on the item type, and check the Quantity of the Item Id Available before Transaction
        String type = invoice.getItemType();
        Integer itemId = invoice.getItemId();
        Integer qtyRequested = invoice.getQuantity();
        switch(type){
            case "tshirt":
                TShirt shirt = tShirtRepository.getById(itemId);
                if(shirt.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                }else{
                    // Get the Fee from the ProcessingFee for tshirt
                    ProcessingFee fee = feeRepository.findByProductType("tshirt");
                    invoice.setProcessingFee(fee.getFee());
                    // Calculate the subtotal abd set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = shirt.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal);
                }
                break;
            case "console":
                Console console = consoleRepository.getById(itemId);
                if(console.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                }else{
                    ProcessingFee fee = feeRepository.findByProductType("console");
                    invoice.setProcessingFee(fee.getFee());
                    // Calculate the subtotal abd set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = console.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal);
                }
                break;
            case "game":
                Game game = gameRepository.getById(itemId);
                if(game.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                }else{
                    ProcessingFee fee = feeRepository.findByProductType("game");
                    invoice.setProcessingFee(fee.getFee());
                    // Calculate the subtotal abd set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = game.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal);
                }
                break;
            default:
                //Add Error, bad type
        }
        // Add Extra Processing fee for Large Order
        if(invoice.getQuantity() >= 10){
            BigDecimal additionalFee = new BigDecimal(15.49);
            BigDecimal processingFee = invoice.getProcessingFee().add(additionalFee);
            invoice.setProcessingFee(processingFee);
        }

        // Get the tax rate based on the order state and perform the calculation based on the subtotal
        BigDecimal taxRate = taxRateRepository.findByState(invoice.getState()).getRate();
        BigDecimal taxValue = taxRate.multiply(invoice.getSubtotal());
        invoice.setTax(taxValue);

        // Add the total
        BigDecimal total = invoice.getTax().add(invoice.getProcessingFee()).add(invoice.getProcessingFee());
        invoice.setTotal(total);

        // Save, build view Model, and Return the Invoice
        Invoice createdInvoice = invoiceRepository.save(invoice);
        InvoiceViewModel ivm = buildInvoiceViewModel(createdInvoice);
        return ivm;
    }


    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        InvoiceViewModel ivm = new InvoiceViewModel(invoice);

        String type = invoice.getItemType();
        switch(type) {
            case "tshirt":
                TShirt shirt = tShirtRepository.getById(invoice.getItemId());
                ivm.setTshirt(shirt);
                break;
            case "console":
                Console console = consoleRepository.getById(invoice.getItemId());
                ivm.setConsole(console);
                break;
            case "game":
                Game game = gameRepository.getById(invoice.getItemId());
                ivm.setGame(game);
                break;
            default:
                //error
                break;
        }

        // Return the InvoiceViewModel
        return ivm;
    }
}