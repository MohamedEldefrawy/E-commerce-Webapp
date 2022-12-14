package com.vodafone.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    boolean create(T entity);

    boolean update(Long id, T updatedEntity);

    boolean delete(Long id);

    Optional<T> getById(Long id);

    Optional<List<T>> getAll();
}
