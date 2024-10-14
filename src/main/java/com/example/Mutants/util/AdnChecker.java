package com.example.Mutants.util;

public class AdnChecker {
    public static boolean isMutant(String[] dna) {
        int n = dna.length;
        int m = dna[0].length();
        int mutantCount = 0;

        // Convertir el arreglo de cadenas en un arreglo de caracteres
        char[][] charDna = new char[n][m];
        for (int i = 0; i < n; i++) {
            charDna[i] = dna[i].toCharArray();
        }

        // Verificar filas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= m - 4; j++) {
                if (checkSequence(charDna, i, j, 0, 1)) {
                    mutantCount++;
                }
            }
        }

        // Verificar columnas
        for (int j = 0; j < m; j++) {
            for (int i = 0; i <= n - 4; i++) {
                if (checkSequence(charDna, i, j, 1, 0)) {
                    mutantCount++;
                }
            }
        }

        // Verificar diagonales principales
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j <= m - 4; j++) {
                if (checkSequence(charDna, i, j, 1, 1)) {
                    mutantCount++;
                }
            }
        }

        // Verificar diagonales secundarias
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 3; j < m; j++) {
                if (checkSequence(charDna, i, j, 1, -1)) {
                    mutantCount++;
                }
            }
        }

        return mutantCount >= 2;
    }

    private static boolean checkSequence(char[][] dna, int startRow, int startCol, int rowInc, int colInc) {
        char base = dna[startRow][startCol];
        for (int k = 1; k < 4; k++) {
            int i = startRow + k * rowInc;
            int j = startCol + k * colInc;
            if (i >= dna.length || j < 0 || j >= dna[0].length || dna[i][j] != base) {
                return false;
            }
        }
        return true;
    }
}


