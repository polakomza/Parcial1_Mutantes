package com.example.Mutants.util;

import java.util.Random;

public class AdnGenerator {

    private static final String[] BASES = {"A", "T", "C", "G"};

    public static char[][] generateRandomDnaMatrix(int size) {
        char[][] dnaMatrix = new char[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dnaMatrix[i][j] = BASES[random.nextInt(BASES.length)].charAt(0);
            }
        }

        return dnaMatrix;
    }
}
