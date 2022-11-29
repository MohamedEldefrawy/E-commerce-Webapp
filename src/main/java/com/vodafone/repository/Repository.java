package com.vodafone.repository;

import java.util.List;

public interface Repository<T> {
    boolean create(T entity);

    boolean update(Long id, T updatedEntity);

    boolean delete(Long id);

    T get(Long id);

    List<T> getAll();
}
