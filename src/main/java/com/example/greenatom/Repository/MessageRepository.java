package com.example.greenatom.Repository;

import com.example.greenatom.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTopicId(Long topicId);

    void delete(Message message);

    Message save(Message message);

    Optional<Message> findById(Long id);
}
