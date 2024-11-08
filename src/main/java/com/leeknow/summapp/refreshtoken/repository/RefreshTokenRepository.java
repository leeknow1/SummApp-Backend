package com.leeknow.summapp.refreshtoken.repository;

import com.leeknow.summapp.refreshtoken.entity.RefreshToken;
import com.leeknow.summapp.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    @Transactional
    void deleteAllByUser(User user);
}
