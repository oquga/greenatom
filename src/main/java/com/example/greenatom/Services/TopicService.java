package com.example.greenatom.Services;

import com.example.greenatom.DTO.MessageDto;
import com.example.greenatom.DTO.TopicDto;
import com.example.greenatom.Models.Message;
import com.example.greenatom.Models.Topic;
import com.example.greenatom.Repository.MessageRepository;
import com.example.greenatom.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public List<AbstractMap.SimpleEntry<Long, String>> getAllTopicsNames() {
        List<Topic> topics = topicRepository.findAll();
        List<AbstractMap.SimpleEntry<Long, String>> titles = topics.stream()
                .map(topic -> new AbstractMap.SimpleEntry<>(topic.getId(), topic.getTitle()))
                .collect(Collectors.toList());

        return titles;
    }

    @Transactional(readOnly = true)
    public List<Topic> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesByTopicId(Long topicId) {
        return messageRepository.findByTopicId(topicId);
    }

    @Transactional
    public Topic createTopic(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setTitle(topicDto.getTitle());

        Message firstMessage = new Message();
        System.out.println(LocalDateTime.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        firstMessage.setCreatedAt(formattedDateTime);

        firstMessage.setAuthor(topicDto.getAuthor());
        firstMessage.setText(topicDto.getFirstMessage());
        firstMessage.setTopic(topic);

        topic.getMessages().add(firstMessage);

        return topicRepository.save(topic);
    }

    @Transactional
    public Message createMessage(Long topicId, MessageDto messageDto) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id: " + topicId));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        Message message = new Message();
        message.setCreatedAt(formattedDateTime);
        message.setAuthor(messageDto.getAuthor());
        message.setText(messageDto.getText());
        message.setTopic(topic);

        return messageRepository.save(message);
    }

    @Transactional
    public Message updateMessage(Long messageId, MessageDto messageDto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
        message.setText(messageDto.getText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        message.setUpdatedAt(formattedDateTime);
        return messageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
        messageRepository.delete(message);
    }
}

