package com.example.Mutants.model;

import lombok.Data;

@Data
public class AdnSequence {
    private String[] dna;

    public char[][] toCharMatrix() {
        // Validar que la matriz tenga exactamente 6 filas
        if (dna.length != 6) {
            throw new IllegalArgumentException("La secuencia de ADN debe tener exactamente 6 filas.");
        }

        char[][] charMatrix = new char[6][];

        for (int i = 0; i < dna.length; i++) {
            // Validar que cada fila tenga exactamente 6 caracteres
            if (dna[i].length() != 6) {
                throw new IllegalArgumentException("Cada fila de la secuencia de ADN debe tener exactamente 6 caracteres.");
            }
            charMatrix[i] = dna[i].toCharArray();
        }

        return charMatrix;
    }
}
