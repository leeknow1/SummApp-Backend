package com.leeknow.summapp.application.entity;

import com.leeknow.summapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;
    private String number;

    @CreatedDate
    private Timestamp creationDate;

    @LastModifiedDate
    private Timestamp finishDate;
    private Integer statusId;
    private Integer typeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
