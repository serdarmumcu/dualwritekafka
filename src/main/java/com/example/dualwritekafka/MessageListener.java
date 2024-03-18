package com.example.dualwritekafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
@Profile("prod")
public class MessageListener {

    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Value("${demo.consumerIdempotency:false}")
    private boolean consumerIdempotencyEnabled;

    @Autowired
    private RedisService redisService;


    @KafkaListener(topics = "messages-topic", groupId = "group_id")
    public void listen(String message) {
        String messageId = generateHash(message);
        if (!consumerIdempotencyEnabled || redisService.checkAndSet(messageId)) {
            logger.info("Received message in group 'group_id': {}", message);
        } else {
            logger.info("Duplicate message ignored: {}", message);
        }
    }

    private String generateHash(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate hash for message", e);
        }
    }
}
