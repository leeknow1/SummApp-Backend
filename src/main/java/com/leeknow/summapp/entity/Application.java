package com.leeknow.summapp.entity;

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
    private Integer number;
    private Timestamp creationDate;
    private Timestamp finishDate;
    private Integer statusId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
