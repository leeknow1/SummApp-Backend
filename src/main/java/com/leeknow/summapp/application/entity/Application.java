package com.leeknow.summapp.application.entity;

import com.leeknow.summapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;
    private String number;
    private Timestamp creationDate;
    private Timestamp finishDate;
    private Integer statusId;
    private Integer typeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
