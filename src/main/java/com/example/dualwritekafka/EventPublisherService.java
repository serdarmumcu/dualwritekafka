package com.example.dualwritekafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EventPublisherService {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private EventProcessingService eventProcessingService;

    @Scheduled(fixedDelay = 10000)
    public void publishEvents() {
        List<Outbox> events = outboxRepository.findAll();
        List<Long> eventIdsForDeletion = new ArrayList<>();

        CompletableFuture<?>[] futures = events.stream().map(event ->
                kafkaTemplate.send("messages-topic", event.getPayload()).thenApply(sendResult -> {
                    synchronized (eventIdsForDeletion) {
                        eventIdsForDeletion.add(event.getId());
                    }
                    return null;
                }).exceptionally(throwable -> {
                    // Log error or implement retry mechanism
                    System.out.println("Error sending event to Kafka: " + throwable.getMessage());
                    return null;
                })
        ).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        eventProcessingService.deleteProcessedEvents(eventIdsForDeletion);
    }
}
