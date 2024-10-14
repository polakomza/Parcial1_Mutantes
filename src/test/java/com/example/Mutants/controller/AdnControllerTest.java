package com.example.Mutants.controller;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.service.AdnService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdnController.class)
public class AdnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // Cambiado de @Mock a @MockBean
    private AdnService adnService;

    @Test
    void testIsMutantEndpoint() throws Exception {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AAAAAA", "CCCCCC", "TTTTTT", "GGGGGG", "CCCCCC", "TTTTTT"});  // Mutante

        // Simula el comportamiento del servicio
        when(adnService.isMutant(any())).thenReturn(true);

        // Realiza la petición POST al controlador
        mockMvc.perform(post("/adn/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"AAAAAA\", \"CCCCCC\", \"TTTTTT\", \"GGGGGG\", \"CCCCCC\", \"TTTTTT\"]}"))
                .andExpect(status().isOk());
    }
}

