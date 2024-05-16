package com.leeknow.summapp.repository;

import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Page<Application> findAllByUser(User user, Pageable pageable);
}
