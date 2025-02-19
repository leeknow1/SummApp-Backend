package com.leeknow.summapp.application.repository;

import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Page<Application> findAllByUser(User user, Pageable pageable);
    List<Application> findAllByUser(User user);


    // Monthly report queries
    @Query("SELECT count(*) FROM Application")
    Integer countAllApplications();

    @Query(value = "SELECT count(*) FROM applications WHERE creation_date>now() - INTERVAL 1 MONTH;", nativeQuery = true)
    Integer countAllApplicationsByLastMonth();

    @Query("SELECT count(*) FROM Application WHERE typeId = :type")
    Integer countAllApplicationsByType(@Param("type") Integer type);

    @Query(value = "SELECT count(*) FROM applications WHERE creation_date>now() - INTERVAL 1 MONTH AND type_id = :type", nativeQuery = true)
    Integer countAllApplicationsByLastMonthAndType(@Param("type") Integer type);

    @Query("SELECT count(*) FROM Application WHERE statusId = :status")
    Integer countAllApplicationsByStatus(@Param("status") Integer status);

    @Query(value = "SELECT count(*) FROM applications WHERE creation_date>now() - INTERVAL 1 MONTH AND status_id = :status", nativeQuery = true)
    Integer countAllApplicationsByLastMonthAndStatus(@Param("status") Integer status);

    @Query("SELECT count(*) FROM Application WHERE typeId = :type AND statusId = :status")
    Integer countAllApplicationsByTypeAndStatus(@Param("type") Integer type, @Param("status") Integer status);

    @Query(value = "SELECT count(*) FROM applications WHERE creation_date>now() - INTERVAL 1 MONTH AND type_id = :type AND status_id = :status", nativeQuery = true)
    Integer countAllApplicationsByLastMonthAndTypeAndStatus(@Param("type") Integer type, @Param("status") Integer status);
}
