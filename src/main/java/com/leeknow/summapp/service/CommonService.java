package com.leeknow.summapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<T> {

    Page<T> findAll(Pageable pageable);

    T findById(Integer id);

    T save(T object);

    T update(T object);

    void delete(Integer id);
}
