package com.example.miniautorizador.controller;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.service.CartaoService;
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


@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @MockBean
    private CartaoService cartaoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCartao() throws Exception {
        var cartao = Cartao.builder()
                .numeroCartao(6549873025634501L)
                .senha("1234")
                .build();

        mockMvc.perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
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
