package org.example.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<Tipo> {
    void save(Tipo entity);
    Optional<Tipo> findById(UUID id);
    List<Tipo> findAll();
    void deleteById(UUID id);
}