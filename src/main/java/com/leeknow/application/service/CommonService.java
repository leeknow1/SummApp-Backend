package com.leeknow.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<T> {

    Page<T> findAll(Pageable pageable);

    T findById(Long id);

    T save(T object);

    T update(T object);

    void delete(Long id);
}
