package com.example.Mutants.controller;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.service.AdnService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdnController.class)
public class AdnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AdnService adnService;

    @Test
    void testIsMutantEndpoint() throws Exception {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AAAA", "TTTT", "GGGG", "CCCC"});

        when(adnService.isMutant(any())).thenReturn(true);

        mockMvc.perform(post("/api/adn/mutant")
                        .contentType("application/json")
                        .content("{\"dna\": [\"AAAA\", \"TTTT\", \"GGGG\", \"CCCC\"]}"))
                .andExpect(status().isOk());
    }
}
