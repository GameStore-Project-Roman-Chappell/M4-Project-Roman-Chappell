package com.gamestore.gamestore.service;

import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.exception.UnprocessableRequestException;
import com.gamestore.gamestore.model.*;
import com.gamestore.gamestore.repository.*;
import com.gamestore.gamestore.viewmodel.InvoiceViewModel;
import org.assertj.core.util.VisibleForTesting;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.TabableView;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;

    ConsoleRepository consoleRepo;
    GameRepository gameRepo;
    TShirtRepository shirtRepo;
    InvoiceRepository invoiceRepository;
    ProcessingFeeRepository processRepo;
    SalesTaxRateRepository taxRepo;

    Invoice shirtInvoiceInput = new Invoice("Mr Potato Head","123 Main", "San Antonio", "TX","12345", "tshirt", 1, 2);
    Invoice consoleInvoiceInput = new Invoice("Dr No","123 Evil", "Chicago", "IL", "12345","console", 1, 1);
    Invoice gameInvoiceInput = new Invoice( "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1,  3);
    Invoice largeQtyInvoiceInput = new Invoice( "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1, 12 );


    Invoice shirtInvoice1 = new Invoice(  "Mr Potato Head","123 Main", "San Antonio", "TX", "12345", "tshirt", 1, new BigDecimal("7.99"), 2, new BigDecimal("15.98"), new BigDecimal("0.48"), new BigDecimal("1.98"),new BigDecimal("18.44") );
    Invoice consoleInvoice1 = new Invoice("Dr No","123 Evil", "Chicago", "IL", "12345", "console", 1, new BigDecimal("499.99"), 1, new BigDecimal("499.99"), new BigDecimal("25.00"), new BigDecimal("14.99"),new BigDecimal("539.98"));
    Invoice gameInvoice1 = new Invoice( "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1, new BigDecimal("59.99"), 3, new BigDecimal("179.97"), new BigDecimal("10.80"), new BigDecimal("1.49"), new BigDecimal("192.26"));
    Invoice largeQtyInvoice1 = new Invoice( "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1, new BigDecimal("59.99"), 12, new BigDecimal("719.88"), new BigDecimal("43.20"), new BigDecimal("16.98"), new BigDecimal("780.06"));

    Invoice shirtReturnInvoice1 = new Invoice( 1, "Mr Potato Head","123 Main", "San Antonio", "TX", "12345", "tshirt", 1, new BigDecimal("7.99"), 2, new BigDecimal("15.98"), new BigDecimal("0.48"), new BigDecimal("1.98"),new BigDecimal("18.44") );
    Invoice consoleReturnInvoice1 = new Invoice(2, "Dr No","123 Evil", "Chicago", "IL", "12345", "console", 1, new BigDecimal("499.99"), 1, new BigDecimal("499.99"), new BigDecimal("25.00"), new BigDecimal("14.99"),new BigDecimal("539.98"));
    Invoice gameReturnInvoice1 = new Invoice(3, "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1, new BigDecimal("59.99"), 3, new BigDecimal("179.97"), new BigDecimal("10.80"), new BigDecimal("1.49"), new BigDecimal("192.26"));
    Invoice largeQtyReturnInvoice1 = new Invoice(4, "Steve Rogers","123 America", "New York", "NY", "12345", "game", 1, new BigDecimal("59.99"), 12, new BigDecimal("719.88"), new BigDecimal("43.20"), new BigDecimal("16.98"), new BigDecimal("780.06"));


    @Before
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        setUpGameRepositoryMock();
        setUpTShirtRepositoryMock();
        setUpTaxRateRepoMock();
        setUpProcessingFeeRepoMock();
        setUpInvoiceRepositoryMock();

        service = new ServiceLayer(consoleRepo, shirtRepo, gameRepo, invoiceRepository, taxRepo, processRepo);
    }

//    MOCK REPO setups
    private void setUpConsoleRepositoryMock() {
        consoleRepo = mock(ConsoleRepository.class);
        Console xbox = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console xbox2 = new Console("Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        List<Console> consoleList = new ArrayList<>();
        consoleList.add(xbox);

        doReturn(xbox).when(consoleRepo).save(xbox2);
        doReturn(Optional.of(xbox)).when(consoleRepo).findById(1);
        doReturn(consoleList).when(consoleRepo).findAll();
        Optional<Console> nullConsole = Optional.ofNullable(null);
        doReturn(nullConsole).when(consoleRepo).findById(3752);
    }

    private void setUpGameRepositoryMock() {
        gameRepo = mock(GameRepository.class);
        Game eldenRing = new Game( 1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal("59.99"), 50);
        Game eldenRing2 = new Game( "Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal("59.99"), 50);

        List<Game> gameList = new ArrayList<>();
        gameList.add(eldenRing);

        doReturn(eldenRing).when(gameRepo).save(eldenRing2);
        doReturn(Optional.of(eldenRing)).when(gameRepo).findById(1);
        doReturn(gameList).when(gameRepo).findAll();

        Optional<Game> nullGame = Optional.ofNullable(null);
        doReturn(nullGame).when(gameRepo).findById(3752);
    }

    private void setUpTShirtRepositoryMock() {
        shirtRepo = mock(TShirtRepository.class);
        TShirt zeldaShirt = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt zeldaShirt2 = new TShirt("Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(zeldaShirt);

        doReturn(zeldaShirt).when(shirtRepo).save(zeldaShirt2);
        doReturn(Optional.of(zeldaShirt)).when(shirtRepo).findById(1);
        doReturn(shirtList).when(shirtRepo).findAll();

        Optional<TShirt> nullShirt = Optional.ofNullable(null);
        doReturn(nullShirt).when(shirtRepo).findById(3752);
    }

    public void setUpTaxRateRepoMock(){
        taxRepo = mock(SalesTaxRateRepository.class);
        SalesTaxRate taxRate1 = new SalesTaxRate("TX", new BigDecimal("0.03"));
        SalesTaxRate taxRate2 = new SalesTaxRate("IL", new BigDecimal("0.05"));
        SalesTaxRate taxRate3 = new SalesTaxRate("NY", new BigDecimal("0.06"));
        SalesTaxRate nullTax = null;

        doReturn(taxRate1).when(taxRepo).findByState("TX");
        doReturn(taxRate2).when(taxRepo).findByState("IL");
        doReturn(taxRate3).when(taxRepo).findByState("NY");
        doReturn(nullTax).when(taxRepo).findByState("ZZ");
    }

    public void setUpProcessingFeeRepoMock(){
        processRepo = mock(ProcessingFeeRepository.class);
        ProcessingFee shirtFee = new ProcessingFee("tshirt", new BigDecimal("1.98"));
        ProcessingFee gameFee = new ProcessingFee("game", new BigDecimal("1.49"));
        ProcessingFee consoleFee = new ProcessingFee("console", new BigDecimal("14.99"));
        ProcessingFee nullFee = null;

        doReturn(shirtFee).when(processRepo).findByProductType("tshirt");
        doReturn(gameFee).when(processRepo).findByProductType("game");
        doReturn(consoleFee).when(processRepo).findByProductType("console");
        doReturn(nullFee).when(processRepo).findByProductType("food");
    }

    public void setUpInvoiceRepositoryMock(){
        invoiceRepository = mock(InvoiceRepository.class);

        doReturn(shirtReturnInvoice1).when(invoiceRepository).save(shirtInvoice1);
        doReturn(consoleReturnInvoice1).when(invoiceRepository).save(consoleInvoice1);
        doReturn(gameReturnInvoice1).when(invoiceRepository).save(gameInvoice1);
        doReturn(largeQtyReturnInvoice1).when(invoiceRepository).save(largeQtyInvoice1);
    }

//    CONSOLE CRUD tests
    @Test
    public void shouldSaveConsole() {
        Console consoleToSave = new Console("Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console expectedConsole = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);

        Console actualResult = service.saveConsole(consoleToSave);

        assertEquals(expectedConsole, actualResult);
    }

    @Test
    public void shouldFindAllConsoles() {
        List<Console> fromService = service.findAllConsoles();

        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindConsoleById() {
        Console expectedResult = new Console(1,"Xbox", "Microsoft", "1GB", "AMD", new BigDecimal(499.99), 50);
        Console actualResult = service.findConsole(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateConsole() {
        Console expectedConsole = new Console(1,"PS5", "Sony", "1GB", "AMD", new BigDecimal(499.99), 50);
         service.updateConsole(new Console(1, "PS5", "Sony", "1GB", "AMD", new BigDecimal(499.99), 50));

         verify(consoleRepo).save(expectedConsole);
    }

    @Test
    public void shouldDeleteConsole() {
        service.deleteConsole(1);
        verify(consoleRepo).deleteById(1);
    }
//  Console Exception Tests
    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingConsoleWithInvalidId(){
        Console badIdConsole = new Console(3752,"DerpBox", "Microsoft", "1GB", "AMD", new BigDecimal(5099.99), 50);
        service.updateConsole(badIdConsole);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingConsoleWithInvalidId(){
        service.deleteConsole(3752);
        fail("We Failed the Test, Exception Not Thrown.");
    }

//    GAME CRUD tests
    @Test
    public void shouldSaveGame() {
        Game gameToSave = new Game("Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal("59.99").setScale(2, RoundingMode.HALF_DOWN), 50);
        Game expectedGame = new Game(1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal("59.99").setScale(2, RoundingMode.HALF_DOWN), 50);

        Game actualResult = service.saveGame(gameToSave);
        assertEquals(expectedGame, actualResult);
    }

    @Test
    public void shouldFindAllGames() {
        List<Game> fromService = service.findAllGames();

        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindGameById() {
        Game expectedResult = new Game(1,"Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal("59.99").setScale(2, RoundingMode.HALF_DOWN), 50);
        Game actualResult = service.findGame(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateGame() {
        Game expectedGame = new Game(1,"Dark Souls 3", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        service.updateGame(new Game(1,"Dark Souls 3", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50));

        verify(gameRepo).save(expectedGame);
    }

    @Test
    public void shouldDeleteGame() {
        service.deleteGame(1);
        verify(gameRepo).deleteById(1);
    }
//  Game Exception Tests
    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingGameWithInvalidId(){
        Game badIdGame = new Game(3752,"Smash and Boom", "M", "Do you even shoot?", "Rockstar Games", new BigDecimal(56099.99), 50);
        service.updateGame(badIdGame);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingGameWithInvalidId(){
        service.deleteGame(3752);
        fail("We Failed the Test, Exception Not Thrown.");
    }

//    SHIRT CRUD Tests
    @Test
    public void shouldSaveShirt() {
        TShirt shirtToSave = new TShirt("Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt expectedShirt = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);

        TShirt actualResult = service.saveTShirt(shirtToSave);
        assertEquals(expectedShirt, actualResult);
    }

    @Test
    public void shouldFindAllShirts() {
        List<TShirt> fromService = service.findAllTShirts();
        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindShirtById() {
        TShirt expectedResult = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt actualResult = service.findTShirtById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldUpdateShirt() {
        TShirt expectedShirt = new TShirt(1,"Medium", "Red", "Zelda Shirt", new BigDecimal(7.99), 50);
        service.updateTShirt(new TShirt(1,"Medium", "Red", "Zelda Shirt", new BigDecimal(7.99), 50));
        verify(shirtRepo).save(expectedShirt);
    }

    @Test
    public void shouldDeleteShirt() {
        service.deleteTShirt(1);
        verify(shirtRepo).deleteById(1);
    }

    //  Shirt Exception Tests
    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingShirtWithInvalidId(){
        TShirt badIdTshirt = new TShirt("Large","Blue","Pretty Cool Shirt", new BigDecimal(56099.99), 50);
        service.updateTShirt(badIdTshirt);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingShirtWithInvalidId(){
        service.deleteTShirt(3752);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    // Invoice Method Tests
    @Test
    public void shouldCreateInvoiceWithProperFeeByItemType(){

        Invoice testResult1 = service.createInvoiceAndReturn(shirtInvoiceInput);
        Invoice testResult2 = service.createInvoiceAndReturn(consoleInvoiceInput);
        Invoice testResult3 = service.createInvoiceAndReturn(gameInvoiceInput);

        assertEquals(new BigDecimal("1.98"), testResult1.getProcessingFee());
        assertEquals(new BigDecimal("14.99"), testResult2.getProcessingFee());
        assertEquals(new BigDecimal("1.49"), testResult3.getProcessingFee());
    }

    @Test
    public void shouldCreateInvoiceWithProperSubtotalForQtyandItem(){

        Invoice testResult1 = service.createInvoiceAndReturn(shirtInvoiceInput);
        Invoice testResult2 = service.createInvoiceAndReturn(consoleInvoiceInput);
        Invoice testResult3 = service.createInvoiceAndReturn(gameInvoiceInput);

        assertEquals(new BigDecimal("15.98"), testResult1.getSubtotal().setScale(2,BigDecimal.ROUND_HALF_DOWN));
        assertEquals(new BigDecimal("499.99"), testResult2.getSubtotal().setScale(2,BigDecimal.ROUND_HALF_DOWN));
        assertEquals(new BigDecimal("179.97"), testResult3.getSubtotal().setScale(2,BigDecimal.ROUND_HALF_DOWN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreatingInvoiceWithInvalidItemType(){
        Invoice badItemTypeInvoice = new Invoice(  "Mr Potato Head","123 Main", "San Antonio", "TX", "12345", "potato", 1, 2 );
        service.createInvoiceAndReturn(badItemTypeInvoice);
        fail("We Failed the Test, Exception Not Thrown.");
    }
    @Test
    public void shouldAddLargeOrderProcessingFeeForInvoiceQtyOverTen(){
        Invoice testResult1 = service.createInvoiceAndReturn(largeQtyInvoiceInput);
        Double normalFee = 1.49;
        Double largeFee = 15.49;
        assertEquals(normalFee + largeFee, Double.valueOf(String.valueOf(testResult1.getProcessingFee())), .0001);
    }

    @Test(expected = UnprocessableRequestException.class)
    public void shouldRespondWith422IfInvoiceQuantityRequestedIsGreaterThanInventory(){
        Invoice exceptionInput = largeQtyInvoiceInput;
        exceptionInput.setQuantity(65);
        service.createInvoiceAndReturn(exceptionInput);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = ProductNotFoundException.class)
    public void  shouldRespondWithNotFoundIfInvoiceItemIdNotExistsForItemType(){
        Invoice exceptionInput = shirtInvoiceInput;
        exceptionInput.setItemId(3752);
        service.createInvoiceAndReturn(exceptionInput);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test
    public void shouldSetInvoiceUnitPriceByProductPriceNotByRequestBodyUnitPrice(){
        Invoice testUnitPriceInput = shirtInvoiceInput;
        testUnitPriceInput.setUnitPrice(new BigDecimal("49.99"));
        Invoice outputInvoice = service.createInvoiceAndReturn(testUnitPriceInput);
        assertEquals(new BigDecimal("7.99"), outputInvoice.getUnitPrice().setScale(2,BigDecimal.ROUND_HALF_DOWN));
    }

    @Test
    public void shouldCalculateInvoiceTaxAndSetTaxAmountFromTaxRate(){

        Invoice shirtReturn = service.createInvoiceAndReturn(shirtInvoiceInput);
        Invoice consoleReturn = service.createInvoiceAndReturn(consoleInvoiceInput);
        Invoice gameReturn = service.createInvoiceAndReturn(gameInvoiceInput);

        assertEquals(new BigDecimal("0.48"), shirtReturn.getTax());
        assertEquals(new BigDecimal("25.00"), consoleReturn.getTax());
        assertEquals(new BigDecimal("10.80"), gameReturn.getTax());
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldRespond422IllegalArgumentIfInvoiceStateDoesNotExistInTaxRateRepo(){
        Invoice badStateInput = shirtInvoiceInput;
        badStateInput.setState("ZZ");

        Invoice returnInvoice = service.createInvoiceAndReturn(badStateInput);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test
    public void shouldCalculateAndSetTotalForInvoice(){
        Invoice shirtReturn = service.createInvoiceAndReturn(shirtInvoiceInput);
        Invoice consoleReturn = service.createInvoiceAndReturn(consoleInvoiceInput);
        Invoice gameReturn = service.createInvoiceAndReturn(gameInvoiceInput);

        assertEquals(new BigDecimal("18.44"), shirtReturn.getTotal());
        assertEquals(new BigDecimal("539.98"), consoleReturn.getTotal());
        assertEquals(new BigDecimal("192.26"), gameReturn.getTotal());
    }

}