package com.gamestore.gamestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestore.gamestore.model.Game;
import com.gamestore.gamestore.repository.GameRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(GamesController.class)
public class GamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GameRepository gamesRepo;

    private ObjectMapper mapper = new ObjectMapper();



}
