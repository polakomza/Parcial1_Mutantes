package com.example.Mutants.repository;

import com.example.Mutants.model.AdnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdnRepository extends JpaRepository<AdnEntity, Long> {
    Optional<AdnEntity> findByDna(String dna);
}
