package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestTomatoesDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.utils.GeminiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PlanBuilderControllers {
    private final TomatoesControllers tomatoesControllers;

    @Autowired
    public PlanBuilderControllers(TomatoesControllers tomatoesControllers) {
        this.tomatoesControllers = tomatoesControllers;
    }

    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(PlanBuilderResponseDto dto) {
        PlanBuilderRequestDto geminiResponse = GeminiApi.ask(dto);
        if (geminiResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        // Process the response to create TomatoEntity objects
        List<PlanBuilderRequestTomatoesDto> tomatoDtos = geminiResponse.getTomatoes();
        for (PlanBuilderRequestTomatoesDto tomatoDto : tomatoDtos) {
            TomatoEntity tomatoEntity = new TomatoEntity();
            tomatoEntity.setSubject(tomatoDto.getSubject());
            tomatoEntity.setStartAt(parseIsoToTimestamp(tomatoDto.getStart_at()));
            tomatoEntity.setEndAt(parseIsoToTimestamp(tomatoDto.getEnd_at()));
            tomatoEntity.setPauseEnd(parseIsoToTimestamp(tomatoDto.getPause_end_at()));
        }
        // Save the TomatoEntity objects to the database

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
}