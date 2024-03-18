package com.example.dualwritekafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventProcessingService {

    @Autowired
    private OutboxRepository outboxRepository;

    @Transactional
    public void deleteProcessedEvents(List<Long> eventIds) {
        if (!eventIds.isEmpty()) {
            outboxRepository.deleteAllByIdIn(eventIds);
        }
    }
}
