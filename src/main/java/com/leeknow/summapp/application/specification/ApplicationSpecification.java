package com.leeknow.summapp.application.specification;

import com.leeknow.summapp.application.dto.ApplicationSearchDTO;
import com.leeknow.summapp.application.entity.Application;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;

public class ApplicationSpecification {

    public static Specification<Application> getApplicationSpecification(ApplicationSearchDTO searchDTO) {
        return Specification
                .where(hasNumber(searchDTO.getNumber()))
                .and(hasType(searchDTO.getTypeId()))
                .and(hasStatus(searchDTO.getStatusId()))
                .and(hasUser(searchDTO.getUserId()))
                .and(createdAt(searchDTO.getCreatedDate()))
                .and(finishedAt(searchDTO.getFinishedDate()));
    }

    public static Specification<Application> hasNumber(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(root.get("number"), '%' + name + '%');
    }

    public static Specification<Application> hasStatus(Integer status) {
        return (root, query, criteriaBuilder) -> status == null ? null : criteriaBuilder.equal(root.get("statusId"), status);
    }

    public static Specification<Application> hasType(Integer type) {
        return (root, query, criteriaBuilder) -> type == null ? null : criteriaBuilder.equal(root.get("typeId"), type);
    }

    public static Specification<Application> createdAt(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) return null;
            Expression<Date> dateExpression = criteriaBuilder.function("DATE", java.sql.Date.class, root.get("creationDate"));
            return criteriaBuilder.equal(dateExpression, java.sql.Date.valueOf(date));
        };
    }

    public static Specification<Application> finishedAt(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) return null;
            Expression<Date> dateExpression = criteriaBuilder.function("DATE", java.sql.Date.class, root.get("finishDate"));
            return criteriaBuilder.equal(dateExpression, java.sql.Date.valueOf(date));
        };
    }

    public static Specification<Application> hasUser(Integer userId) {
        return (root, query, criteriaBuilder) -> userId == null ? null : criteriaBuilder.equal(root.get("user").get("userId"), userId);
    }
}
