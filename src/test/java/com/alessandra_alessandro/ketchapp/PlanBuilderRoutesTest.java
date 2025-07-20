package com.alessandra_alessandro.ketchapp;

import com.alessandra_alessandro.ketchapp.config.GlobalExceptionHandler;
import com.alessandra_alessandro.ketchapp.controllers.PlanBuilderControllers;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.routes.PlanBuilderRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PlanBuilderRoutesTest {

    @Mock
    private PlanBuilderControllers planBuilderControllers;

    @InjectMocks
    private PlanBuilderRoutes planBuilderRoutes;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private PlanBuilderResponseDto createValidResponseDto() {
        PlanBuilderResponseDto.Calendar calendarDto = new PlanBuilderResponseDto.Calendar();
        calendarDto.setStart_at("2024-07-18T10:00:00");
        calendarDto.setEnd_at("2024-07-18T11:00:00");
        calendarDto.setTitle("Study Session");
        PlanBuilderResponseDto.Subject subjectDto = new PlanBuilderResponseDto.Subject("subject", "1h");
        return new PlanBuilderResponseDto(
                UUID.randomUUID(),
                "60",
                "10",
                Collections.singletonList(calendarDto),
                Collections.singletonList(subjectDto)
        );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(planBuilderRoutes)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreatePlanBuilder() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();
        PlanBuilderRequestDto requestDto = new PlanBuilderRequestDto();

        when(planBuilderControllers.createPlanBuilder(any(PlanBuilderResponseDto.class)))
                .thenReturn(new ResponseEntity<>(requestDto, HttpStatus.OK));

        mockMvc.perform(post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isOk())
                .andDo(print());

        ArgumentCaptor<PlanBuilderResponseDto> argumentCaptor = ArgumentCaptor.forClass(PlanBuilderResponseDto.class);
        verify(planBuilderControllers).createPlanBuilder(argumentCaptor.capture());
        assertEquals(responseDto.getUserUUID(), argumentCaptor.getValue().getUserUUID());
    }

    @Test
    void testCreatePlanBuilder_InternalServerError() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();

        when(planBuilderControllers.createPlanBuilder(any(PlanBuilderResponseDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    void testCreatePlanBuilder_BadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = new PlanBuilderResponseDto();

        mockMvc.perform(post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testCreatePlanBuilder_PartialBadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = new PlanBuilderResponseDto();
        responseDto.setUserUUID(UUID.randomUUID());

        mockMvc.perform(post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testCreatePlanBuilder_InvalidValuesBadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();
        responseDto.setSession("");

        mockMvc.perform(post("/api/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
