package com.example.dualwritekafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean checkAndSet(String key) {
        Boolean isPresent = redisTemplate.opsForValue().setIfAbsent(key, "exists");
        return Boolean.TRUE.equals(isPresent);
    }
}
