package com.alessandra_alessandro.ketchapp.kafka;

import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

    @Value("${app.kafka.topic.mail-service}")
    private String mailServiceTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToKafka(String email, PlanBuilderRequestDto plan) {
        try {
            String planJson = objectMapper.writeValueAsString(plan);

            String message = String.format("EMAIL:%s|DATA:%s", email, planJson);

            log.info("Producing formatted message to topic {}: {}", mailServiceTopic, message);

            log.info(
                "Producing JSON message to topic {}: {}",
                mailServiceTopic,
                message
            );
            this.kafkaTemplate.send(mailServiceTopic, message);
        } catch (JsonProcessingException e) {
            log.error(
                "Failed to serialize message for email {}: {}",
                email,
                e.getMessage(),
                e
            );
        }
    }
}
