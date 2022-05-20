package com.gamestore.gamestore.service;

import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.exception.UnprocessableRequestException;
import com.gamestore.gamestore.model.*;
import com.gamestore.gamestore.repository.*;
import com.gamestore.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        Optional<Console> opt = consoleRepository.findById(console.getId());
        if(opt.isPresent()){
            consoleRepository.save(console);
        }else {
            throw new ProductNotFoundException("Cannot Update Console, no console found for Id: " + console.getId());
        }
    }

    public void deleteConsole(int id) {
        Optional<Console> opt = consoleRepository.findById(id);
        if(opt.isPresent()){
            consoleRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Cannot Delete Console, no console found for Id: " + id);
        }

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
        Optional<TShirt> opt = tShirtRepository.findById(tShirt.getId());
        if(opt.isPresent()){
            tShirtRepository.save(tShirt);
        }else {
            throw new ProductNotFoundException("Cannot Update TShirt, no shirt found for Id: " + tShirt.getId());
        }
    }
    // DELETE METHOD
    public void deleteTShirt(int id) {
        Optional<TShirt> opt = tShirtRepository.findById(id);
        if(opt.isPresent()){
            tShirtRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Cannot Delete TShirt, no shirt found for Id: " + id);
        }
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
        Optional<Game> opt = gameRepository.findById(game.getId());
        if(opt.isPresent()){
            gameRepository.save(game);
        }else {
            throw new ProductNotFoundException("Cannot Update Game, no game found for Id: " + game.getId());
        }
    }

    public void deleteGame(int id) {
        Optional<Game> opt = gameRepository.findById(id);
        if(opt.isPresent()){
            gameRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Cannot Delete Game, no game found for Id: " + id);
        }

    }
// ------------------------  INVOICE SECTION   ----------------------//
    @Transactional
    public Invoice createInvoiceAndReturn(Invoice invoice){
        System.out.println("Begin createInvoiceAndReturn with:" + invoice);
        // Add the fee based on the item type, and check the Quantity of the Item Id Available before Transaction
        String type = invoice.getItemType();
        Integer itemId = invoice.getItemId();
        Integer qtyRequested = invoice.getQuantity();
        switch(type){
            case "tshirt":
                // Get the tshirt as an optional, in order to validate the ID of the tshirt or throw an error
                Optional<TShirt> tshirt = tShirtRepository.findById(itemId);
                TShirt shirt;
                if(tshirt.isPresent()){
                    shirt = tshirt.get();
                }else{
                    throw new ProductNotFoundException("T Shirt Not Found with ID: "+invoice.getItemId());
                }
                if(shirt.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                    throw new UnprocessableRequestException("Request cannot be processed, you requested a purchase of "+ qtyRequested+ ", but we only have " + shirt.getQuantity() + " of that item in inventory.");

                }else{
                    System.out.println("T Shirt item Type found for Invoice");
                    // Set Unit Price off of the item from the DB, not from the input received
                    invoice.setUnitPrice(shirt.getPrice().setScale(2, RoundingMode.HALF_DOWN));
                    // Get the Fee from the ProcessingFee for tshirt
                    try {
                        ProcessingFee fee = feeRepository.findByProductType("tshirt");
                        invoice.setProcessingFee(fee.getFee().setScale(2, RoundingMode.HALF_DOWN));
                    } catch (Exception e){
                        throw new IllegalArgumentException("The fee for itemType 'tshirt' has not been set up. ");
                    }
                    // Calculate the subtotal and set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = shirt.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_DOWN));
                    // Remove the Qty Purchased from the TShirt
                    shirt.removeQuantity(invoice.getQuantity());
                    tShirtRepository.save(shirt);
                }
                System.out.println("Exit Swtich for Item Type with Invoice: "+invoice);
                break;
            case "console":
                // Get the console as an optional, in order to validate the ID of the console or throw an error
                Optional<Console> consoleOpt= consoleRepository.findById(itemId);
                Console console;
                if(consoleOpt.isPresent()){
                    console = consoleOpt.get();
                }else{
                    throw new ProductNotFoundException("Console Not Found with ID: "+ invoice.getItemId());
                }
                if(console.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                    throw new UnprocessableRequestException("Request cannot be processed, you requested a purchase of "+ qtyRequested+ ", but we only have " + console.getQuantity() + " of that item in inventory.");
                }else{
                    // Set Unit Price off of the item from the DB, not from the input received
                    invoice.setUnitPrice(console.getPrice().setScale(2, RoundingMode.HALF_DOWN));
                    // Get the Fee from the ProcessingFee for console
                    try {
                        ProcessingFee fee = feeRepository.findByProductType("console");
                        invoice.setProcessingFee(fee.getFee().setScale(2, RoundingMode.HALF_DOWN));
                    } catch (Exception e){
                        throw new IllegalArgumentException("The fee for itemType 'console' has not been set up. ");
                    }
                    // Calculate the subtotal and set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = console.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_DOWN));
                    // Remove the Qty Purchased from the Console
                    console.removeQuantity(invoice.getQuantity());
                   consoleRepository.save(console);
                }
                break;
            case "game":
                // Get the game as an optional, in order to validate the ID of the game or throw an error
                Optional<Game> gameOpt= gameRepository.findById(itemId);
                Game game;
                if(gameOpt.isPresent()){
                    game = gameOpt.get();
                }else{
                    throw new ProductNotFoundException("Game Not Found with ID: "+ invoice.getItemId());
                }
                if(game.getQuantity() < qtyRequested){
                    // throw Error or response for invalid request, you cannot buy x QTY, we only have y
                    throw new UnprocessableRequestException("Request cannot be processed, you requested a purchase of "+ qtyRequested+ ", but we only have " + game.getQuantity() + " of that item in inventory.");
                }else{
                    // Set Unit Price off of the item from the DB, not from the input received
                    invoice.setUnitPrice(game.getPrice().setScale(2, RoundingMode.HALF_DOWN));
                    // Get the Fee from the ProcessingFee for game
                    try{
                        ProcessingFee fee = feeRepository.findByProductType("game");
                        invoice.setProcessingFee(fee.getFee().setScale(2, RoundingMode.HALF_DOWN));
                    } catch (Exception e){
                        throw new IllegalArgumentException("The fee for itemType 'game' has not been set up. ");
                    }

                    // Calculate the subtotal and set it to the invoice
                    BigDecimal qty = new BigDecimal(qtyRequested);
                    BigDecimal subtotal = game.getPrice().multiply(qty);
                    invoice.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_DOWN));
                    // Remove the Qty Purchased from the Game
                    game.removeQuantity(invoice.getQuantity());
                   gameRepository.save(game);
                }
                break;
            default:
                throw new IllegalArgumentException("No item type of " + invoice.getItemType() + " was found. Please use item type of: tshirt, console, or game.");
        }
        // Add Extra Processing fee for Large Order
        System.out.println("Checking for large order processing fee.");
        if(invoice.getQuantity() >= 10){
            BigDecimal additionalFee = new BigDecimal("15.49").setScale(2, RoundingMode.HALF_DOWN);
            BigDecimal processingFee = invoice.getProcessingFee().add(additionalFee);
            invoice.setProcessingFee(processingFee.setScale(2, RoundingMode.HALF_DOWN));
        }

        // Get the tax rate based on the order state and perform the calculation based on the subtotal
        System.out.println("Finding tax rate.");
        try {
            BigDecimal taxRate = taxRateRepository.findByState(invoice.getState()).getRate();
            BigDecimal taxValue = taxRate.multiply(invoice.getSubtotal()).setScale(2, BigDecimal.ROUND_CEILING);
            System.out.println("Tax Amount: "+taxValue);
            invoice.setTax(taxValue.setScale(2, RoundingMode.HALF_DOWN));
        }catch (Exception e){
            throw new IllegalArgumentException("No tax rate found for "+ invoice.getState() + ". State must be a valid state abbreviation.");
        }

        // Add the total
        BigDecimal total = invoice.getTax().add(invoice.getProcessingFee()).add(invoice.getSubtotal());
        invoice.setTotal(total.setScale(2, RoundingMode.HALF_DOWN));

        // Save and Return the Invoice
        System.out.println("Saving Invoice Built" + invoice);
        Invoice created = invoiceRepository.save(invoice);
        System.out.println("Invoice Saved: " + created);
        return created;
    }


    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        System.out.println("Building Invoice View Model: "+invoice);
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
        System.out.println("Completed View Model: " + ivm);
        // Return the InvoiceViewModel
        return ivm;
    }
}