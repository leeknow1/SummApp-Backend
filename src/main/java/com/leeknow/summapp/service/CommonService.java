package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.DataSearchDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CommonService<T> {

    Map<String, Page<T>> findAll(DataSearchDTO searchDTO);

    Map<String, T> findById(Integer id);

    Map<String, T> save(T object);

    Map<String, T> update(T object);

    void delete(Integer id);
}
