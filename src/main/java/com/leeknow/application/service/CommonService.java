package com.leeknow.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<T> {

    public Page<T> findAll(Pageable pageable);

    public T findById(Long id);

    public T save(T object);

    public T update(T object);

    public void delete(Long id);
}
