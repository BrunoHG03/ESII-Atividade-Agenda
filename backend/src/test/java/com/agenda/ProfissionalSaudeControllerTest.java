package com.agenda;

import com.agenda.controller.ProfissionalSaudeController;
import com.agenda.model.Categoria;
import com.agenda.model.ProfissionalSaude;
import com.agenda.repository.ProfissionalSaudeRepository;
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


@WebMvcTest(ProfissionalSaudeController.class)
class ProfissionalSaudeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfissionalSaudeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarProfissionalComSucesso() throws Exception {
        ProfissionalSaude profissionalSaude = new ProfissionalSaude();
        profissionalSaude.setId(1L);
        profissionalSaude.setNome("João Silva");
        profissionalSaude.setTelefone("31999999999");
        profissionalSaude.setCategoria(Categoria.FISIOTERAPEUTA);

        when(repository.save(any(ProfissionalSaude.class))).thenReturn(profissionalSaude);

        mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profissionalSaude)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void deveListarProfissionaisVazio() throws Exception {
        when(repository.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/profissionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveRetornar404ParaProfissionalInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profissionais/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarProfissionalComSucesso() throws Exception {
        ProfissionalSaude profissionalSaude = new ProfissionalSaude();
        profissionalSaude.setId(1L);
        profissionalSaude.setNome("João Silva");

        when(repository.findById(1L)).thenReturn(Optional.of(profissionalSaude));

        mockMvc.perform(delete("/api/profissionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Profissional removido com sucesso"));
    }
}