package com.leeknow.summapp.module.repository;

import com.leeknow.summapp.module.entity.Module;
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
    @Query(value = "INSERT INTO modules_roles VALUES (:moduleId, :roleId, :right)", nativeQuery = true)
    void setUserRightForModule(@Param("moduleId") Integer moduleId, @Param("roleId") Integer roleId, @Param("right") Integer right);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM modules_roles WHERE module_id = :moduleId AND role_id = :roleId", nativeQuery = true)
    void deleteUserRightForModule(@Param("moduleId") Integer moduleId, @Param("roleId") Integer roleId);

    @Query(value = "SELECT COUNT(*) FROM modules_roles WHERE module_id = :moduleId AND role_id in (:roleIds)", nativeQuery = true)
    int getUserModule(@Param("moduleId") Integer moduleId, @Param("roleIds") String roleIds);
}
