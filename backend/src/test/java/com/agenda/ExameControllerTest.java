package com.agenda;

import com.agenda.controller.ExameController;
import com.agenda.model.Exame;
import com.agenda.repository.ExameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ExameController.class)
class ExameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExameRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarExameComSucesso() throws Exception {
        Exame exame = new Exame();
        exame.setId(1L);
        exame.setDescricao("exame1");
        exame.setPatologia("Sangue");

        when(repository.save(any(Exame.class))).thenReturn(exame);

        mockMvc.perform(post("/api/exames")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exame)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("exame1"));
    }

    @Test
    void deveListarExamesVazio() throws Exception {
        when(repository.findAllByOrderByDescricaoAsc()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/exames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveRetornar404ParaExameInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/exames/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarExameComSucesso() throws Exception {
        Exame exame = new Exame();
        exame.setId(1L);
        exame.setDescricao("exame1");

        when(repository.findById(1L)).thenReturn(Optional.of(exame));

        mockMvc.perform(delete("/api/exames/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Exame removido com sucesso"));
    }
}