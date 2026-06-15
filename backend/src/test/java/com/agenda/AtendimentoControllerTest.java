package com.agenda;

import com.agenda.controller.AtendimentoController;
import com.agenda.model.Receita;
import com.agenda.model.Atendimento;
import com.agenda.repository.AtendimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AtendimentoController.class)
class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtendimentoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarAtendimentoComSucesso() throws Exception {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);
        atendimento.setTitulo("Atendimento1");
        atendimento.setData(LocalDate.now());
        atendimento.setHorario(LocalTime.now());
        atendimento.setLinkVideo("https://www.linkvideo.com.br");
        atendimento.setReceita(Receita.REMEDIO);

        when(repository.save(any(Atendimento.class))).thenReturn(atendimento);

        mockMvc.perform(post("/api/atendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atendimento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Atendimento1"));
    }

    @Test
    void deveListarAtendimentosVazio() throws Exception {
        when(repository.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/atendimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveRetornar404ParaAtendimentoInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/atendimentos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarAtendimentoComSucesso() throws Exception {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);
        atendimento.setTitulo("Atendimento1");

        when(repository.findById(1L)).thenReturn(Optional.of(atendimento));

        mockMvc.perform(delete("/api/atendimentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Atendimento removido com sucesso"));
    }
}