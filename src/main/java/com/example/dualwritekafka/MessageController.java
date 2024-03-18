package com.example.dualwritekafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private OutboxRepository outboxRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/messages")
    @Transactional
    public String createMessage(@RequestBody String content) throws JsonProcessingException {
        Message message = new Message();
        message.setContent(content);
        messageRepository.save(message);

        Outbox outbox = new Outbox();
        outbox.setEventType("MessageCreated");
        outbox.setPayload(objectMapper.writeValueAsString(message));
        outboxRepository.save(outbox);

        return "Message saved and event created";
    }
}
