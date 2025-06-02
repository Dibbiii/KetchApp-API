package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.utils.GeminiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlanBuilderControllers {

    public ResponseEntity<PlanBuilderRequestDto> createPlanBuilder(PlanBuilderResponseDto dto) {
        PlanBuilderRequestDto geminiResponse = GeminiApi.ask(dto);
        return ResponseEntity.ok(geminiResponse);
    }
}