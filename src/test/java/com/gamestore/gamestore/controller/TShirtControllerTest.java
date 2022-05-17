package com.gamestore.gamestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.model.TShirt;
import com.gamestore.gamestore.repository.TShirtRepository;
import com.gamestore.gamestore.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TShirtsController.class)
public class TShirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    @MockBean
    TShirtRepository shirtRepo;

    private ObjectMapper mapper = new ObjectMapper();

    TShirt inputShirt;

    TShirt outputShirt;

    String inputShirtString;

    String outputShirtString;

    List<TShirt> allShirts;

    String allShirtsString;

    @Before
    public void setUp() throws Exception {
        inputShirt = new TShirt("Medium","Red","Zelda Shirt", new BigDecimal(7.99),25);
        outputShirt = new TShirt(1,"Medium","Red","Zelda Shirt", new BigDecimal(7.99),25);
        inputShirtString = mapper.writeValueAsString(inputShirt);
        outputShirtString = mapper.writeValueAsString(outputShirt);
        allShirts = Arrays.asList(outputShirt);
        allShirtsString = mapper.writeValueAsString(allShirts);

        when(serviceLayer.saveTShirt(inputShirt)).thenReturn(outputShirt);
        when(serviceLayer.findAllTShirts()).thenReturn(allShirts);
        when(serviceLayer.findTShirtById(1)).thenReturn(outputShirt);
    }

    @Test
    public void shouldAddShirtOnPostRequest() throws Exception {
        mockMvc.perform(post("/tshirts")
                .content(inputShirtString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputShirtString));
    }

    @Test
    public void shouldGetArrayOfShirts() throws Exception {
        mockMvc.perform(get("/tshirts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allShirtsString));
    }

    @Test
    public void shouldGetShirtsById() throws Exception {
        mockMvc.perform(get("/tshirts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputShirtString));
    }

    @Test
    public void shouldUpdateShirts() throws Exception {
        mockMvc.perform(put("/tshirts/1")
                .content(outputShirtString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteShirt() throws Exception {
        mockMvc.perform(delete("/tshirts/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithUnprocessableWhenTShirtCreateRequestIsBad() throws Exception {
        // tshirt1 -
        TShirt tshirt1 = new TShirt();
        String inputTShirt1 = mapper.writeValueAsString(tshirt1);


        mockMvc.perform(post("/tshirts")
                        .content(inputTShirt1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());       // Assert HttpStatus 422 Response - Unprocessable Entity

    }

    @Test
    public void shouldReturn404WhenFindingInvalidId() throws Exception {
        mockMvc.perform(get("/tshirts/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/tshirts/999")
                        .content(outputShirtString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturn404WhenDeletingTShirtWithInvalidId() throws Exception {
        mockMvc.perform(delete("/tshirts/139875"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
