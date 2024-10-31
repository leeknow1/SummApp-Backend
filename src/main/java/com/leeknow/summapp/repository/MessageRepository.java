package com.leeknow.summapp.repository;

import com.leeknow.summapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByMessageIdIn(Collection<Integer> messageId);
}
