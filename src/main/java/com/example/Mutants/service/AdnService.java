package com.example.Mutants.service;

import com.example.Mutants.dto.AdnSequenceRequest;
import com.example.Mutants.model.AdnEntity;
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
        // Convertir el arreglo de ADN a un String
        String dnaString = String.join(",", adnSequenceRequest.getDna());

        // Verificar si la secuencia de ADN ya existe en la base de datos
        Optional<AdnEntity> existingAdn = adnRepository.findByDna(dnaString);
        if (existingAdn.isPresent()) {
            // Si existe, retornar el valor existente de isMutant
            return existingAdn.get().isMutant();
        }

        // Determinar si es mutante
        boolean isMutant = AdnChecker.isMutant(adnSequenceRequest.getDna());

        // Crear una nueva entidad y guardarla
        AdnEntity adnEntity = new AdnEntity();
        adnEntity.setDna(dnaString);
        adnEntity.setMutant(isMutant);
        adnRepository.save(adnEntity);

        return isMutant;
    }
}
