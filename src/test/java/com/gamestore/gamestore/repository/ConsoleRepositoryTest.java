package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.Console;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleRepositoryTest {

    @Autowired
    ConsoleRepository consoleRepo;

    @Before
    public void setUp() throws Exception {
        consoleRepo.deleteAll();
    }

    @Test
    public void addGetDeleteConsole() {

        Console console = new Console("Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);

        console = consoleRepo.save(console);

        Optional<Console> console1 = consoleRepo.findById(console.getId());

        assertEquals(console1.get(), console);

        consoleRepo.deleteById(console.getId());

        console1 = consoleRepo.findById(console.getId());

        assertFalse(console1.isPresent());
    }

    @Test
    public void getAllConsoles() {
        Console console = new Console("Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);

        console = consoleRepo.save(console);

        Console console2 = new Console("PS5", "Sony", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);

        console2 = consoleRepo.save(console2);

        List<Console> consoleList = consoleRepo.findAll();
        assertEquals(2, consoleList.size());
    }

    @Test
    public void updateConsole() {
        Console console = new Console("Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);

        console = consoleRepo.save(console);

        console.setModel("Xbox Series X");

        consoleRepo.save(console);

        Optional<Console> console1 = consoleRepo.findById(console.getId());
        assertEquals(console, console1.get());
    }

    @Test
    public void findConsoleByManufacturer() {
        Console console1 = new Console("Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);
        Console console2 = new Console("Xbox 360", "Microsoft", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);
        Console console3 = new Console("PS5", "Sony", "1TB", "AMD", new BigDecimal(499.99).setScale(2, BigDecimal.ROUND_FLOOR), 50);

        consoleRepo.save(console1);
        consoleRepo.save(console2);
        consoleRepo.save(console3);

        List<Console> consoleList = consoleRepo.findByManufacturer("Microsoft");
        assertEquals(2, consoleList.size());
    }
}
