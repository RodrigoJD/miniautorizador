package com.example.miniautorizador.controller;

import com.example.miniautorizador.service.CardService;
import com.example.miniautorizador.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CardController.class)
public class CardControllerWebMvcTest {

    @MockBean
    private CardService cardService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCartao() throws Exception {
        var card = TestUtil.buildCard();

        mockMvc.perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldGetSaldo() throws Exception {
        mockMvc.perform(get("/cartoes/{numeroCartao}", "6549873025634501"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
