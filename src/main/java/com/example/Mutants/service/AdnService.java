package com.example.Mutants.service;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.exceptions.InvalidDnaException;
import com.example.Mutants.model.AdnEntity;
import com.example.Mutants.model.AdnSequence;
import com.example.Mutants.repository.AdnRepository;
import com.example.Mutants.util.AdnChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdnService {

    @Autowired
    private AdnRepository adnRepository;

    public boolean isMutant(AdnSequenceRequest adnSequenceRequest) {
        // Crear una instancia de AdnSequence y validar el ADN
        AdnSequence adnSequence = new AdnSequence();
        adnSequence.setDna(adnSequenceRequest.getDna());
        char[][] charMatrix = adnSequence.toCharMatrix(); // Esto lanzará excepciones si no es válido

        // Convertir el arreglo de ADN a un String separado por comas
        String dnaString = String.join(",", adnSequenceRequest.getDna());

        // Verificar si la secuencia ya está almacenada en la base de datos
        Optional<AdnEntity> existingAdn = adnRepository.findByDna(dnaString);
        if (existingAdn.isPresent()) {
            return existingAdn.get().isMutant(); // Devolver el resultado ya guardado
        }

        // Si no está almacenado, calcular si es mutante y guardarlo en la base de datos
        boolean isMutant = AdnChecker.isMutant(adnSequenceRequest.getDna());
        AdnEntity adnEntity = new AdnEntity();
        adnEntity.setDna(dnaString); // Guardar la secuencia como un String
        adnEntity.setMutant(isMutant);
        adnRepository.save(adnEntity); // Guardar la entidad en la base de datos

        return isMutant;
    }

    // Metodo de validación del ADN (para asegurar que sea de 6x6)
    private void validateDna(String[] dna) {
        if (dna == null || dna.length != 6) {
            throw new InvalidDnaException("La secuencia de ADN debe ser una matriz de 6x6.");
        }
        for (String row : dna) {
            if (row.length() != 6) {
                throw new InvalidDnaException("Cada fila de la secuencia de ADN debe tener 6 caracteres.");
            }
        }
    }
}

