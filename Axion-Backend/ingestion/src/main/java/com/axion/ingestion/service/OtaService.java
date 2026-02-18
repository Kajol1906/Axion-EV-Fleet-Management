package com.axion.ingestion.service;

import com.axion.ingestion.model.DigitalTwinState;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class OtaService {

    private final RedisTemplate<String, DigitalTwinState> redisTemplate;
    private final Random random = new Random();

    public OtaService(RedisTemplate<String, DigitalTwinState> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean triggerOta(String vehicleId, String campaignId) {
        String key = "digital_twin:" + vehicleId;
        DigitalTwinState state = redisTemplate.opsForValue().get(key);

        if (state == null) {
            return false;
        }

        // Basic Simulation Logic
        // In a real scenario, this would check rules, bandwidth, etc.
        // Here we just simulate a success/fail outcome.

        boolean success = random.nextBoolean(); // 50/50 chance for demo
        // Or make it mostly successful:
        // boolean success = random.nextDouble() > 0.2; // 80% success

        state.setLastUpdateTimestamp(Instant.now());

        // Update eligibility based on the result (Mock logic)
        // If success, maybe not eligible for a while?
        // For now, let's just toggle it or keep it true.
        state.setOtaEligibility(true);

        redisTemplate.opsForValue().set(key, state);

        System.out.println("OTA TRIGGERED for " + vehicleId + " (Campaign: " + campaignId + ") -> Success: " + success);
        return true;
    }
}
