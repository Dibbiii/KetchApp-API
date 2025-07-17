package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.kafka.KafkaProducer;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.utils.GeminiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import com.alessandra_alessandro.ketchapp.repositories.TomatoesRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PlanBuilderControllers {
    private final TomatoesControllers tomatoesControllers;
    private final TomatoesRepository tomatoesRepository;

    private final KafkaProducer kafkaProducer;

    @Autowired
    public PlanBuilderControllers(TomatoesControllers tomatoesControllers, TomatoesRepository tomatoesRepository, KafkaProducer kafkaProducer) {
        this.tomatoesControllers = tomatoesControllers;
        this.tomatoesRepository = tomatoesRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(PlanBuilderResponseDto dto) {
        PlanBuilderRequestDto geminiResponse = GeminiApi.ask(dto);
        if (geminiResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        // Process the response to create TomatoEntity objects
        if (geminiResponse.getSubjects() != null) {
            for (var subject : geminiResponse.getSubjects()) {
                if (subject.getTomatoes() != null) {
                    TomatoEntity prevTomato = null;
                    for (var tomato : subject.getTomatoes()) {
                        TomatoEntity tomatoEntity = new TomatoEntity();
                        tomatoEntity.setId(null); // Lascia che il DB generi l'id
                        tomatoEntity.setSubject(subject.getName());
                        tomatoEntity.setUserUUID(dto.getUserUUID()); // Usa il parametro passato
                        tomatoEntity.setStartAt(parseIsoToTimestamp(tomato.getStart_at()));
                        tomatoEntity.setEndAt(parseIsoToTimestamp(tomato.getEnd_at()));
                        tomatoEntity.setPauseEnd(parseIsoToTimestamp(tomato.getPause_end_at()));
                        // Salva tomatoEntity nel database
                        TomatoEntity savedTomato = tomatoesRepository.save(tomatoEntity);
                        // Se c'è un tomato precedente, aggiorna il suo next_tomato_id
                        if (prevTomato != null) {
                            prevTomato.setNextTomatoId(savedTomato.getId());
                            tomatoesRepository.save(prevTomato);
                        }
                        // Usa sempre l'istanza persistente per evitare duplicati
                        prevTomato = savedTomato;
                    }
                }
            }
        }

        return ResponseEntity.ok(geminiResponse);
    }

    private Timestamp parseIsoToTimestamp(String isoString) {
        if (isoString == null || isoString.isEmpty()) return null;
        try {
            if (isoString.endsWith("Z")) {
                // e.g. 2024-06-22T13:15:00Z
                return Timestamp.from(Instant.parse(isoString));
            } else if (isoString.contains("T")) {
                // e.g. 2024-06-22T13:15:00 or 2024-06-22T13:15:00.123 etc.
                LocalDateTime ldt = LocalDateTime.parse(isoString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return Timestamp.valueOf(ldt);
            } else {
                // Try JDBC format "yyyy-MM-dd HH:mm:ss"
                return Timestamp.valueOf(isoString);
            }
        } catch (Exception e) {
            // Optionally: log the error here if you wish
            throw new IllegalArgumentException("Invalid timestamp string: " + isoString, e);
        }
    }

    public String TestKafka(String message) {
        this.kafkaProducer.sendMessage(message);
        System.out.println("MESASGE SEND KAFKA");
        return message;
    }
}