package com.gamestore.gamestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.repository.GameRepository;
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
@WebMvcTest(GamesController.class)
public class GamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    @MockBean
    GameRepository gamesRepo;

    private ObjectMapper mapper = new ObjectMapper();

    Game inputGame;

    Game outputGame;

    String inputGameString;

    String outputGameString;

    List<Game> allGames;

    String allGamesString;

    @Before
    public void setUp() throws Exception {
        inputGame = new Game("Elden Ring", "M", "Action Adventure", "From Software", new BigDecimal(59.99), 50);
        outputGame = new Game(1,"Elden Ring", "M", "Action Adventure", "From Software", new BigDecimal(59.99), 50);
        inputGameString = mapper.writeValueAsString(inputGame);
        outputGameString = mapper.writeValueAsString(outputGame);
        allGames = Arrays.asList(outputGame);
        allGamesString = mapper.writeValueAsString(allGames);

        when(serviceLayer.saveGame(inputGame)).thenReturn(outputGame);
        when(serviceLayer.findAllGames()).thenReturn(allGames);
        when(serviceLayer.findGame(1)).thenReturn(outputGame);

    }

    @Test
    public void shouldAddGameOnPostRequest() throws Exception {
        mockMvc.perform(post("/games")
                .content(inputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputGameString));
    }

    @Test
    public void shouldGetArrayOfGames() throws Exception {
        mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesString));
    }

    @Test
    public void shouldGetGamesById() throws Exception {
        mockMvc.perform(get("/games/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputGameString));
    }

    @Test
    public void shouldUpdateGames() throws Exception {
        mockMvc.perform(put("/games/1")
                .content(outputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn404WhenFindingInvalidId() throws Exception {
        mockMvc.perform(get("/games/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRetur422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/games/999")
                .content(outputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
