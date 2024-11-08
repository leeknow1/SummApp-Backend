package com.leeknow.summapp.user.repository;

import com.leeknow.summapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);


    //Monthly report queries
    @Query("SELECT count(*) FROM User")
    Integer countAllUsers();

    @Query(value = "SELECT count(*) FROM users WHERE creation_date>now() - INTERVAL 1 MONTH;", nativeQuery = true)
    Integer countAllUsersByLastMonth();
}
