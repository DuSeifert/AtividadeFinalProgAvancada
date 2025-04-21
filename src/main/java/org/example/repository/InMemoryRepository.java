package org.example.repository;

import org.example.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryRepository<Tipo extends Entity> implements EntityRepository<Tipo> {
    private ArrayList<Tipo> data = new ArrayList<>();

    @Override
    public void save(Tipo entity) {
        this.data.removeIf(e -> e.getUuid().equals(entity.getUuid()));
        this.data.add(entity);
    }

    @Override
    public Optional<Tipo> findById(UUID id) {
        return this.data.stream().filter(e -> e.getUuid().equals(id)).findFirst();
    }

    @Override
    public List<Tipo> findAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public void deleteById(UUID id) {
        this.data.removeIf(e -> e.getUuid().equals(id));
    }
}