package com.example.Mutants.controller;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.dto.AdnStatsDTO;
import com.example.Mutants.service.AdnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdnController.class)
public class AdnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdnService adnService;

    @Test
    void testIsMutantEndpoint_notMutant() throws Exception {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AATCGG", "CGTCAA", "TATGCT", "GTCAAA", "CGTCCA", "TGTGAA"}); // No mutante

        // Simula el comportamiento del servicio
        when(adnService.isMutant(any())).thenReturn(false);

        // Realiza la petición POST al controlador
        mockMvc.perform(post("/adn/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"AATCGG\", \"CGTCAA\", \"TATGCT\", \"GTCAAA\", \"CGTCCA\", \"TGTGAA\"]}"))
                .andExpect(status().isForbidden()) // Verifica que se recibe 403 Forbidden
                .andExpect(jsonPath("$.message").value("No es mutante")); // Verifica que el mensaje es "No es mutante"
    }

    @Test
    void testGetAdnStatsEndpoint() throws Exception {
        // Crea un objeto de estadísticas de ADN con datos de ejemplo
        AdnStatsDTO stats = new AdnStatsDTO();
        stats.setCountMutantDna(40);
        stats.setCountHumanDna(100);
        stats.setRatio(0.4);

        // Simula el comportamiento del servicio para que devuelva las estadísticas
        when(adnService.getAdnStats()).thenReturn(stats);

        // Realiza la petición GET al endpoint de estadísticas
        mockMvc.perform(get("/adn/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna").value(40))  // Asegúrate de usar camelCase
                .andExpect(jsonPath("$.countHumanDna").value(100))  // Asegúrate de usar camelCase
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

}


