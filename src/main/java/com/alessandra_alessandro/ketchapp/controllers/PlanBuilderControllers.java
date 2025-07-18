package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.utils.GeminiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.repositories.TomatoesRepository;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlanBuilderControllers {
    private static final Logger log = LoggerFactory.getLogger(PlanBuilderControllers.class);
    private final TomatoesRepository tomatoesRepository;
    private final UsersRepository usersRepository;
    private final GeminiApi geminiApi;

    @Autowired
    public PlanBuilderControllers(TomatoesRepository tomatoesRepository, UsersRepository usersRepository, GeminiApi geminiApi) {
        this.tomatoesRepository = tomatoesRepository;
        this.usersRepository = usersRepository;
        this.geminiApi = geminiApi;
    }

    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(PlanBuilderResponseDto dto) {
        PlanBuilderRequestDto geminiResponse = geminiApi.ask(dto);
        if (geminiResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        if (geminiResponse.getSubjects() != null) {
            for (var subject : geminiResponse.getSubjects()) {
                if (subject.getTomatoes() != null) {
                    TomatoEntity prevTomato = null;
                    for (var tomato : subject.getTomatoes()) {
                        TomatoEntity tomatoEntity = new TomatoEntity();
                        tomatoEntity.setId(null);
                        tomatoEntity.setSubject(subject.getName());
                        tomatoEntity.setUserUUID(dto.getUserUUID());
                        tomatoEntity.setStartAt(parseIsoToTimestamp(tomato.getStart_at()));
                        tomatoEntity.setEndAt(parseIsoToTimestamp(tomato.getEnd_at()));
                        tomatoEntity.setPauseEnd(parseIsoToTimestamp(tomato.getPause_end_at()));
                        TomatoEntity savedTomato = tomatoesRepository.save(tomatoEntity);
                        if (prevTomato != null) {
                            prevTomato.setNextTomatoId(savedTomato.getId());
                            tomatoesRepository.save(prevTomato);
                        }
                        prevTomato = savedTomato;
                    }
                }
            }
        }

        String userEmail = "";
        Optional<UserEntity> userOptional = usersRepository.findById(dto.getUserUUID());
        if (userOptional.isPresent()) {
            userEmail = userOptional.get().getEmail();
        }
        String encodedEmail = URLEncoder.encode(userEmail, StandardCharsets.UTF_8);
        String mailUrl = "http://151.61.228.91:8082/api/mail/" + encodedEmail;
        log.info("Invio richiesta email a: {} con payload: {}", mailUrl, geminiResponse.toJson());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(mailUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(geminiResponse.toJson()))
                .build();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Error sending email notification: {}", response.body());
            } else {
                log.info("Email notification sent successfully.");
            }
        } catch (IOException | InterruptedException e) {
            log.error("Exception sending email notification", e);
        }

        return ResponseEntity.ok(geminiResponse);
    }

    private Timestamp parseIsoToTimestamp(String isoString) {
        if (isoString == null || isoString.isEmpty()) return null;
        try {
            if (isoString.endsWith("Z")) {
                return Timestamp.from(Instant.parse(isoString));
            } else if (isoString.contains("T")) {
                LocalDateTime ldt = LocalDateTime.parse(isoString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return Timestamp.valueOf(ldt);
            } else {
                return Timestamp.valueOf(isoString);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timestamp string: " + isoString, e);
        }
    }
}