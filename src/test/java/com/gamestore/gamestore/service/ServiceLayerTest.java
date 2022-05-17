package com.gamestore.gamestore.service;

import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.repository.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;

    ConsoleRepository consoleRepo;
    GameRepository gameRepo;
    TShirtRepository shirtRepo;
    InvoiceRepository invoiceRepo;
    ProcessingFeeRepository processRepo;
    SalesTaxRateRepository taxRepo;

    @Before
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        setUpGameRepositoryMock();
        setUpTShirtRepositoryMock();
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
    }

    private void setUpGameRepositoryMock() {
        gameRepo = mock(GameRepository.class);
        Game eldenRing = new Game( "Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);
        Game eldenRing2 = new Game(1, "Elden Ring", "M", "Action-Adventure", "FromSoftware", new BigDecimal(59.99), 50);

        List<Game> gameList = new ArrayList<>();
        gameList.add(eldenRing);

        doReturn(eldenRing).when(gameRepo).save(eldenRing2);
        doReturn(Optional.of(eldenRing)).when(gameRepo).findById(1);
        doReturn(gameList).when(gameRepo).findAll();
    }

    private void setUpTShirtRepositoryMock() {
        shirtRepo = mock(TShirtRepository.class);
        TShirt zeldaShirt = new TShirt("Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);
        TShirt zeldaShirt2 = new TShirt(1,"Large", "Black", "Zelda Shirt", new BigDecimal(7.99), 50);

        List<TShirt> shirtList = new ArrayList<>();
        shirtList.add(zeldaShirt);

        doReturn(zeldaShirt).when(shirtRepo).save(zeldaShirt2);
        doReturn(Optional.of(zeldaShirt)).when(shirtRepo).findById(1);
        doReturn(shirtList).when(shirtRepo).findAll();
    }

//    CRUD tests
    @Test
    public void shouldSaveConsole() {
        Console consoleToSave = new Console()
    }


}