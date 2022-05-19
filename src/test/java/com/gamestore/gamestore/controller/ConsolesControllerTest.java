package com.gamestore.gamestore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestore.gamestore.exception.ProductNotFoundException;
import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.repository.ConsoleRepository;
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
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsolesController.class)
public class ConsolesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    @MockBean
    ConsoleRepository consoleRepo;

    private ObjectMapper mapper = new ObjectMapper();

    Console inputXbox;

    Console outputXbox;

    Console outputConsoleSearch1;

    Console outputConsoleSearch2;

    String inputConsoleString;

    String outputConsoleString;

    String outputConsoleStringSearch;

    List<Console> allConsoles;

    List<Console> outputSearchList;

    String allConsolesString;




    @Before
    public void setUp() throws Exception {
        inputXbox = new Console(1,"Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99), 50);
        outputXbox = new Console(1,"Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99), 50);
        outputConsoleSearch1 = new Console(2,"PS 5", "Sony", "648GB", "AMD", new BigDecimal(699.99), 40);
        outputConsoleSearch2 = new Console(3,"PS 2", "Sony", "64 GB", "AMD", new BigDecimal(24.99), 4);


        inputConsoleString = mapper.writeValueAsString(inputXbox);
        outputConsoleString = mapper.writeValueAsString(outputXbox);
        allConsoles = Arrays.asList(outputXbox);
        allConsolesString = mapper.writeValueAsString(allConsoles);
        outputSearchList = Arrays.asList(outputConsoleSearch1, outputConsoleSearch2);
        outputConsoleStringSearch = mapper.writeValueAsString(outputSearchList);


        when(serviceLayer.saveConsole(inputXbox)).thenReturn(outputXbox);
        when(serviceLayer.findAllConsoles()).thenReturn(allConsoles);
        when(serviceLayer.findConsole(1)).thenReturn(outputXbox);
        doThrow(new ProductNotFoundException("Bad")).when(serviceLayer).deleteConsole(139875);
        when(serviceLayer.findAllConsolesByManufacturer("Sony")).thenReturn(outputSearchList);


    }

    @Test
    public void shouldAddConsoleOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputXbox);
        String outputJson = mapper.writeValueAsString(outputXbox);

        mockMvc.perform(post("/consoles")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }




    @Test
    public void shouldGetArrayOfConsoles() throws Exception {
        mockMvc.perform(get("/consoles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allConsolesString));
    }

    @Test
    public void shouldGetConsoleById() throws Exception {
        mockMvc.perform(get("/consoles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputConsoleString));
    }

    @Test
    public void shouldUpdateConsoles() throws Exception {
        mockMvc.perform(put("/consoles/1")
                .content(outputConsoleString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteConsole() throws Exception {
        mockMvc.perform(delete("/consoles/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    // Someone needs to fully appreciate this method name, it could have been longer/better
    @Test
    public void shouldRespondWithUnprocessableWhenConsoleCreateRequestIsBad() throws Exception {
        // console1 - Only First Operand Included
        Console console1 = new Console();
        String inputConsole1 = mapper.writeValueAsString(console1);


        mockMvc.perform(post("/consoles")
                        .content(inputConsole1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());       // Assert HttpStatus 422 Response - Unprocessable Entity

    }

    @Test
    public void shouldReturn404WhenFindingConsoleWithInvalidId() throws Exception {
        mockMvc.perform(get("/consoles/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenUpdateRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/consoles/999")
                .content(inputConsoleString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void shouldReturn404WhenDeletingConsoleWithInvalidId() throws Exception {
        mockMvc.perform(delete("/consoles/139875"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnConsoleListWhenSearchingByManufacturer() throws Exception {
        mockMvc.perform(get("/consoles?manufacturer=Sony"))
                .andDo(print())
                .andExpect(content().json(outputConsoleStringSearch));
    }
}