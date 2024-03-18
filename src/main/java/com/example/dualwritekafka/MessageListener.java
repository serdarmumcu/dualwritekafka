package com.example.dualwritekafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class MessageListener {

    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @KafkaListener(topics = "messages-topic", groupId = "group_id")
    public void listen(String message) {
        logger.info("Received message in group 'group_id': {}", message);
        // Print the message to console
        System.out.println("Received Message: " + message);
    }
}
