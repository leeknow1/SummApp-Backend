package com.leeknow.summapp.application.repository;

import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>, JpaSpecificationExecutor<Application> {

    Page<Application> findAllByUser(User user, Pageable pageable);
    List<Application> findAllByUser(User user);
}
