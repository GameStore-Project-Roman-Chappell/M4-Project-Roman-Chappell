package com.gamestore.gamestore.repository;

import com.gamestore.gamestore.model.TShirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TShirtRepositoryTest {

    @Autowired
    TShirtRepository tshirtRepository;

    @Before
    public void setUp() {
        tshirtRepository.deleteAll();
    }

    @Test
    public void shouldAddAndDeleteTShirtFromRepository() {

        TShirt shirt = new TShirt("Large", "Blue", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt);

        Optional<TShirt> shirtOpt = tshirtRepository.findById(shirt.getId());

        assertEquals(shirtOpt.get(), shirt);

        tshirtRepository.deleteById(shirt.getId());

        shirtOpt = tshirtRepository.findById(shirt.getId());

        assertFalse(shirtOpt.isPresent());
    }

    @Test
    public void shouldFindAllShirtsAsListFromRepository() {
        // Arrange
        TShirt shirt1 = new TShirt("Large", "Blue", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt1);

        TShirt shirt2 = new TShirt("Medium", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 32);
        tshirtRepository.save(shirt2);
        // Act
        List<TShirt> shirtList = tshirtRepository.findAll();

        // Assert
        assertEquals(2, shirtList.size());
        assertEquals(shirt1, shirtList.get(0));
        assertEquals(shirt2, shirtList.get(1));
    }

    @Test
    public void shouldUpdateShirtOnSave() {
        TShirt shirt1 = new TShirt("Large", "Blue", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt1);

        shirt1.setColor("Red");
        shirt1.setQuantity(53);
        tshirtRepository.save(shirt1);

        Optional<TShirt> shirtOpt = tshirtRepository.findById(shirt1.getId());
        assertEquals(shirt1,shirtOpt.get());
        assertEquals( "Red", shirtOpt.get().getColor());
        assertEquals(53, shirtOpt.get().getQuantity());
    }

    @Test
    public void shouldReturnShirtsBySize() {
        TShirt shirt1 = new TShirt("Large", "Blue", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt1);
        TShirt shirt2 = new TShirt("Large", "Blue", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 21);
        tshirtRepository.save(shirt2);
        TShirt shirt3 = new TShirt("Extra Large", "Red", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("6.95").setScale(2, RoundingMode.HALF_DOWN), 3);
        tshirtRepository.save(shirt3);
        TShirt shirt4 = new TShirt("Medium", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 14);
        tshirtRepository.save(shirt4);


        List<TShirt> shirtList1 = tshirtRepository.findAllBySize("Large");
        List<TShirt> shirtList2 = tshirtRepository.findAllBySize("Medium");
        assertEquals(2, shirtList1.size());
        assertEquals(shirtList1.get(0), shirt1);
        assertEquals(shirtList1.get(1), shirt2);
        assertEquals(1, shirtList2.size());
        assertEquals(shirtList2.get(0), shirt4);
    }

    @Test
    public void shouldReturnShirtsByColor() {
        // Arrange
        TShirt shirt1 = new TShirt("Large", "Blue", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt1);
        TShirt shirt2 = new TShirt("Large", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 21);
        tshirtRepository.save(shirt2);
        TShirt shirt3 = new TShirt("Extra Large", "Red", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("6.95").setScale(2, RoundingMode.HALF_DOWN), 3);
        tshirtRepository.save(shirt3);
        TShirt shirt4 = new TShirt("Medium", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 14);
        tshirtRepository.save(shirt4);

        // Act
        List<TShirt> shirtList1 = tshirtRepository.findAllByColor("Black");
        List<TShirt> shirtList2 = tshirtRepository.findAllByColor("Red");
        // Assert
        assertEquals(2, shirtList1.size());
        assertEquals(shirtList1.get(0), shirt2);
        assertEquals(shirtList1.get(1), shirt4);
        assertEquals(1, shirtList2.size());
        assertEquals(shirtList2.get(0), shirt3);
    }

    @Test
    public void shouldReturnTShirtsByColorAndSize() {
        // Arrange
        TShirt shirt1 = new TShirt("Large", "Black", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("9.95").setScale(2, RoundingMode.HALF_DOWN), 5);
        tshirtRepository.save(shirt1);
        TShirt shirt2 = new TShirt("Large", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 21);
        tshirtRepository.save(shirt2);
        TShirt shirt3 = new TShirt("Extra Large", "Red", "Legend of Zelda Shirt with the Triforce Symbol", new BigDecimal("6.95").setScale(2, RoundingMode.HALF_DOWN), 3);
        tshirtRepository.save(shirt3);
        TShirt shirt4 = new TShirt("Medium", "Black", "Halo Shirt with Master Chief", new BigDecimal("5.95").setScale(2, RoundingMode.HALF_DOWN), 14);
        tshirtRepository.save(shirt4);

        // Act
        List<TShirt> shirtList1 = tshirtRepository.findAllByColorAndSize("Black", "Large");
        List<TShirt> shirtList2 = tshirtRepository.findAllByColorAndSize("Red", "Medium");
        List<TShirt> shirtList3 = tshirtRepository.findAllByColorAndSize("Red", "Extra Large");
        // Assert
        assertEquals(2, shirtList1.size());
        assertEquals(shirtList1.get(0), shirt1);
        assertEquals(shirtList1.get(1), shirt2);
        assertEquals(0, shirtList2.size());
        assertEquals(1, shirtList3.size());
        assertEquals(shirtList3.get(0), shirt3);
    }
}