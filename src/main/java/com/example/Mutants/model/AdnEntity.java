package com.example.Mutants.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "adn")
public class AdnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna", nullable = false, unique = true)
    private String dna; // Se cambia a String para guardar la secuencia completa de ADN

    @Column(name = "is_mutant", nullable = false)
    private boolean isMutant; // true si es mutante, false si no lo es
}




