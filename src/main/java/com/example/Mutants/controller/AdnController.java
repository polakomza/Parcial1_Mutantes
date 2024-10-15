package com.example.Mutants.controller;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.dto.AdnSequenceResponse;
import com.example.Mutants.dto.AdnStatsDTO;
import com.example.Mutants.service.AdnService;
import com.example.Mutants.util.AdnGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adn")
public class AdnController {

    @Autowired
    private AdnService adnService;

    @PostMapping("/mutant")
    public ResponseEntity<AdnSequenceResponse> isMutant(@RequestBody AdnSequenceRequest adnSequenceRequest) {
        boolean isMutant = adnService.isMutant(adnSequenceRequest);

        if (isMutant) {
            AdnSequenceResponse response = new AdnSequenceResponse();
            response.setMessage("Es mutante");
            return ResponseEntity.ok(response);
        } else {
            AdnSequenceResponse response = new AdnSequenceResponse();
            response.setMessage("No es mutante");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // Incluye el mensaje "No es mutante" en el body
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<AdnSequenceRequest> generateRandomDna() {
        char[][] dna = AdnGenerator.generateRandomDnaMatrix(6);
        AdnSequenceRequest adnSequenceRequest = new AdnSequenceRequest();
        adnSequenceRequest.setDna(new String[6]);
        for (int i = 0; i < 6; i++) {
            adnSequenceRequest.getDna()[i] = new String(dna[i]);
        }
        return ResponseEntity.ok(adnSequenceRequest);
    }

    // Endpoint para obtener las estadísticas
    @GetMapping("/stats")
    public ResponseEntity<AdnStatsDTO> getAdnStats() {
        AdnStatsDTO stats = adnService.getAdnStats();
        return ResponseEntity.ok(stats);
    }
}


