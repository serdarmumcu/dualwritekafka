package com.example.dualwritekafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventProcessingService {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private Environment env;

    @Transactional
    public void deleteProcessedEvents(List<Long> eventIds) {
        if (!eventIds.isEmpty()) {
            boolean simulateFailure = Boolean.parseBoolean(env.getProperty("demo.simulateDeletionFailure"));
            if (simulateFailure) {
                System.out.println("Simulated deletion failure due to demo property.");
            }
            else
                outboxRepository.deleteAllByIdIn(eventIds);
        }
    }
}
