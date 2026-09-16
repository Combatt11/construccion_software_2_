package com.nexora.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository<T> {
    private final List<T> data = new ArrayList<>();

    public void save(T value) { data.add(value); }
    public List<T> findAll() { return new ArrayList<>(data); }
    public Optional<T> findFirst(java.util.function.Predicate<T> predicate) {
        return data.stream().filter(predicate).findFirst();
    }
}
