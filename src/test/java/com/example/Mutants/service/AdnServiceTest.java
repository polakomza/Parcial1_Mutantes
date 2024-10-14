package com.example.Mutants.service;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.exceptions.InvalidDnaException;
import com.example.Mutants.repository.AdnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdnServiceTest {

    @InjectMocks
    private AdnService adnService;

    @Mock
    private AdnRepository adnRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //Test para validar que es un mutante tanto vertical como horizontalmente
    @Test
    void testIsMutant_withMutantDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AAAACC",   //Primera secuencia mutante en horizontal (A-A-A-A)
                                    "CCCTGC",
                                    "TTTCGT",   //Segunda secuencia de forma vertical (T-T-T-T)
                                    "GGGCTT",
                                    "TGCTAT",
                                    "TTGTTT"});

        boolean result = adnService.isMutant(request);
        assertTrue(result, "Estamos frente a.. UN MUTANTE!.");
    }
    //Test para validar un mutante de manera diagonal
    @Test
    void testIsMutant_withDiagonalMutantDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{
                "ATGCGA",  // Primera secuencia mutante diagonal (A-A-A-A)
                "CAGTGC",
                "CTATGT",
                "ACAAGG",  // Segunda secuencia mutante diagonal (C-C-C-C)
                "CCCCTA",
                "TCACTG"
        });
        boolean result = adnService.isMutant(request);
        assertTrue(result, "El ADN contiene dos secuencias mutantes en las diagonales.");
    }


    //Test para validar que no sea mutante
    @Test
    void testIsMutant_withHumanDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AATCGG",
                                    "CGTCAA",
                                    "TATGCT",
                                    "GTCAAA",
                                    "CGTCCA",
                                    "TGTGAA"});  // No mutante

        boolean result = adnService.isMutant(request);
        assertFalse(result, "Este ADN es de un humano...");
    }

    //Test para validar tamanio de matrices
    @Test
    void testIsMutant_withInvalidMatrixSize() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"ATGC", "CAGTGC", "TTATTT"});  // Matriz inválida

        Exception exception = assertThrows(InvalidDnaException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La secuencia de ADN debe ser una matriz de 6x6.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    //Test para recibir solo caracteres validos
    @Test
    void testIsMutant_withNumbersInDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"123456", "CAGTGC", "TTATTT", "AGAAGG", "CCCCTA", "TCACTG"});  // Secuencia con números

        Exception exception = assertThrows(InvalidDnaException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La secuencia de ADN contiene caracteres inválidos. Solo se permiten A, T, C, G.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    //Test para lanzar excepcion al recibir Null
    @Test
    void testIsMutant_withNullDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(null);

        Exception exception = assertThrows(InvalidDnaException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La secuencia de ADN no puede ser nula.";
        assertEquals(expectedMessage, exception.getMessage());
    }

}
