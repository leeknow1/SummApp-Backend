package com.leeknow.summapp.refreshtoken.entity;

import com.leeknow.summapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {

    @Id
    private String refreshTokenId;
    private String token;
    private Timestamp creationDate;
    private Timestamp finishDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
