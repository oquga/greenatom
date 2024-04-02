package com.example.greenatom.Controllers;
import com.example.greenatom.DTO.MessageDto;
import com.example.greenatom.DTO.TopicDto;
import com.example.greenatom.Models.Message;
import com.example.greenatom.Models.Topic;
import com.example.greenatom.Services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<AbstractMap.SimpleEntry<Long,String>>> getAllTopicsNames() {
        List<AbstractMap.SimpleEntry<Long,String>> topics = topicService.getAllTopicsNames();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<Topic>> getAllTopics() {
//        List<Topic> topics = topicService.getAllTopics();
//        return new ResponseEntity<>(topics, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody TopicDto topicDto) {
        Topic createdTopic = topicService.createTopic(topicDto);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @GetMapping("/{topicId}/messages")
    public ResponseEntity<List<Message>> getMessagesByTopicId(@PathVariable Long topicId) {
        List<Message> messages = topicService.getMessagesByTopicId(topicId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/{topicId}/messages")
    public ResponseEntity<Message> createMessage(@PathVariable Long topicId, @RequestBody MessageDto messageDto) {
        Message createdMessage = topicService.createMessage(topicId, messageDto);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @PutMapping("/messages/{messageId}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long messageId, @RequestBody MessageDto messageDto) {
        Message updatedMessage = topicService.updateMessage(messageId, messageDto);
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        topicService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

