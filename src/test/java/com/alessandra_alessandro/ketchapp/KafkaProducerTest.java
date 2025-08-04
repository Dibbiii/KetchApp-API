package com.alessandra_alessandro.ketchapp;

import com.alessandra_alessandro.ketchapp.kafka.KafkaProducer;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        try {
            java.lang.reflect.Field topicField = KafkaProducer.class.getDeclaredField("mailServiceTopic");
            topicField.setAccessible(true);
            topicField.set(kafkaProducer, "test-topic");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSendToKafkaSuccess() throws Exception {
        String email = "test@test.com";
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto();
        String planJson = "{\"field\":\"value\"}";
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);

        kafkaProducer.sendToKafka(email, plan);

        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }

    @Test
    void testSendToKafkaJsonProcessingException() throws Exception {
        String email = "test@example.com";
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto();
        lenient().when(objectMapper.writeValueAsString(plan)).thenThrow(new JsonProcessingException("error") {
        });
        kafkaProducer.sendToKafka(email, plan);
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    @Test
    void testSendToKafkaWithEmptyPlan() throws Exception {
        String email = "test@test.com";
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto(); // empty plan
        String planJson = "{}";
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);

        kafkaProducer.sendToKafka(email, plan);

        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }


    @Test
    void testSendToKafkaWithNullPlan() throws Exception {
        String email = "test@test.com";
        PlanBuilderRequestDto plan = null;
        String planJson = "null";
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);
        kafkaProducer.sendToKafka(email, plan);
        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }

    @Test
    void testSendToKafkaWithIncompletePlan() throws Exception {
        String email = "test@test.com";
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto(); // incomplete plan
        String planJson = "{\"field\":null}"; // adjust according to actual fields
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);

        kafkaProducer.sendToKafka(email, plan);

        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }

    @Test
    void testSendToKafkaWithEmptyEmail() throws Exception {
        String email = "";
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto();
        String planJson = "{\"field\":\"value\"}";
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);

        kafkaProducer.sendToKafka(email, plan);

        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }

    @Test
    void testSendToKafkaWithNullEmail() throws Exception {
        String email = null;
        PlanBuilderRequestDto plan = new PlanBuilderRequestDto();
        String planJson = "{\"field\":\"value\"}";
        when(objectMapper.writeValueAsString(plan)).thenReturn(planJson);

        kafkaProducer.sendToKafka(email, plan);

        String expectedMessage = String.format("EMAIL:%s|DATA:%s", email, planJson);
        verify(kafkaTemplate).send("test-topic", expectedMessage);
    }
}