package com.leeknow.summapp.repository;

import com.leeknow.summapp.entity.Module;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO modules_users VALUES (:moduleId, :userId, :right)", nativeQuery = true)
    void setUserRightForModule(@Param("moduleId") Integer moduleId, @Param("userId") Integer userId, @Param("right") Integer right);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM modules_users WHERE module_id = :moduleId AND user_id = :userId", nativeQuery = true)
    void deleteUserRightForModule(@Param("moduleId") Integer moduleId, @Param("userId") Integer userId);
}
