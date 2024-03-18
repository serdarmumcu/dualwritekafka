package com.example.dualwritekafka;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    void deleteAllByIdIn(List<Long> ids);
}

