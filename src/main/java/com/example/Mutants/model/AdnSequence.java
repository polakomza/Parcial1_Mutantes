package com.example.Mutants.model;

import com.example.Mutants.exceptions.InvalidDnaException;
import lombok.Data;

@Data
public class AdnSequence {
    private String[] dna;

    public char[][] toCharMatrix() {
        validateDna();
        char[][] charMatrix = new char[dna.length][];
        for (int i = 0; i < dna.length; i++) {
            charMatrix[i] = dna[i].toCharArray();
        }
        return charMatrix;
    }

    private void validateDna() {
        if (dna == null || dna.length != 6) {
            throw new InvalidDnaException("La secuencia de ADN debe ser una matriz de 6x6.");
        }
        for (String row : dna) {
            if (row.length() != 6) {
                throw new InvalidDnaException("Cada fila de la secuencia de ADN debe tener 6 caracteres.");
            }
            if (!row.matches("[ATCG]+")) {
                throw new InvalidDnaException("La secuencia de ADN contiene caracteres inválidos. Solo se permiten A, T, C, G.");
            }
        }
    }
}


