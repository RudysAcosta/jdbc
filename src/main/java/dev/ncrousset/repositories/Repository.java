package dev.ncrousset.repositories;

import java.util.List;

public interface Repository<T> {
    T getById(Long id);
    List<T> getAll();
    void save(T register);
    void update(T register);
    void delete(Long id);
    T getLast();
}
