package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestTomatoesDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.utils.GeminiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alessandra_alessandro.ketchapp.models.dto.TomatoDto;
import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;

import java.sql.Timestamp;
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
            tomatoEntity.setStartAt(Timestamp.valueOf(tomatoDto.getStart_at()));
            tomatoEntity.setEndAt(Timestamp.valueOf(tomatoDto.getEnd_at()));
            tomatoEntity.setPauseEnd(Timestamp.valueOf(tomatoDto.getPause_end_at()));
        }
        // Save the TomatoEntity objects to the database

        return ResponseEntity.ok(geminiResponse);
    }
}