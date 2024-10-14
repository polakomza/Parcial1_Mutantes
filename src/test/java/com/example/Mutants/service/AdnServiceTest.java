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
    //Test para validar que es un mutante
    @Test
    void testIsMutant_withMutantDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AAAAAA", "CCCCCC", "TTTTTT", "GGGGGG", "CCCCCC", "TTTTTT"});  // Mutante

        boolean result = adnService.isMutant(request);
        assertTrue(result, "Estamos frente a.. UN MUTANTE!.");
    }

    //Test para validar que no sea mutante
    @Test
    void testIsMutant_withHumanDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AATCGG", "CGTCAA", "TATGCT", "GTCAAA", "CGTCCA", "TGTGAA"});  // No mutante

        boolean result = adnService.isMutant(request);
        assertFalse(result, "Este ADN es de un humano...");
    }

    //Test para validar tamanio de matrices
    @Test
    void testIsMutant_withInvalidMatrixSize() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"AAA", "CCC"}); //Matriz invalida

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La matriz de ADN debe de ser 6x6";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    //Test para recibir solo caracteres validos
    @Test
    void testIsMutant_withNumbersInDna() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[]{"123456", "CAGTGC", "TTATTT", "AGAAGG", "CCCCTA", "TCACTG"});  // Secuencia con números

        Exception exception = assertThrows(InvalidDnaException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La secuencia de ADN contiene caracteres inválidos.";
        assertEquals(expectedMessage, exception.getMessage());
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
    //Test que no permita matriz con valores nulos
    @Test
    void testIsMutant_withNullMatrix() {
        AdnSequenceRequest request = new AdnSequenceRequest();
        request.setDna(new String[6]);  // Matriz de nulls

        Exception exception = assertThrows(InvalidDnaException.class, () -> {
            adnService.isMutant(request);
        });

        String expectedMessage = "La secuencia de ADN contiene valores nulos.";
        assertEquals(expectedMessage, exception.getMessage());
    }


}
