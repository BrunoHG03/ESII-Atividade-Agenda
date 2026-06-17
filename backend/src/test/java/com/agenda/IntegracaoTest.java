package com.agenda;

import com.agenda.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.jayway.jsonpath.JsonPath;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveExecutarFluxoCompletoProfissional() throws Exception {
        // 1. CRIAR Profissional (POST)
        MvcResult result = mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Maria\", \"categoria\": \"MEDICO\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        String jsonResposta = result.getResponse().getContentAsString();
        Long id = ((Number) JsonPath.read(jsonResposta, "$.id")).longValue();

        // 2. BUSCAR Profissional (GET)
        mockMvc.perform(get("/api/profissionais/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));

        // 3. ATUALIZAR Profissional (PUT)
        mockMvc.perform(put("/api/profissionais/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Maria Silva\", \"categoria\": \"MEDICO\"}"))
                .andExpect(status().isOk());

        // 4. DELETAR Profissional (DELETE)
        mockMvc.perform(delete("/api/profissionais/" + id))
                .andExpect(status().isOk());
    }
}