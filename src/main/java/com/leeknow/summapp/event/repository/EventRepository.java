package com.leeknow.summapp.event.repository;

import com.leeknow.summapp.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByEventTimeBetween(Timestamp start, Timestamp finish, Pageable pageable);
}
