package com.alessandra_alessandro.ketchapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alessandra_alessandro.ketchapp.config.GlobalExceptionHandler;
import com.alessandra_alessandro.ketchapp.controllers.PlanBuilderControllers;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderRequestDto;
import com.alessandra_alessandro.ketchapp.models.dto.PlanBuilderResponseDto;
import com.alessandra_alessandro.ketchapp.routes.PlanBuilderRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.UUID;
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

@ExtendWith(MockitoExtension.class)
public class PlanBuilderRoutesTest {

    @Mock
    private PlanBuilderControllers planBuilderControllers;

    @InjectMocks
    private PlanBuilderRoutes planBuilderRoutes;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

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

        System.out.println("[TEST]: Executing testCreatePlanBuilder...");
        System.out.println(
            "[TEST]: Request DTO for mocking (from PlanBuilderRequestDto): " +
            objectMapper.writeValueAsString(requestDto)
        );
        System.out.println(
            "[TEST]: Response DTO for request body (from PlanBuilderResponseDto): " +
            objectMapper.writeValueAsString(responseDto)
        );

        when(
            planBuilderControllers.createPlanBuilder(
                any(PlanBuilderResponseDto.class)
            )
        ).thenReturn(
            (ResponseEntity) new ResponseEntity<PlanBuilderResponseDto>(
                responseDto,
                HttpStatus.OK
            )
        );

        mockMvc
            .perform(
                post("/api/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isOk())
            .andDo(print());

        ArgumentCaptor<PlanBuilderResponseDto> argumentCaptor =
            ArgumentCaptor.forClass(PlanBuilderResponseDto.class);
        verify(planBuilderControllers).createPlanBuilder(
            argumentCaptor.capture()
        );
        System.out.println(
            "[TEST]: Captured DTO by ArgumentCaptor (passed to controller): " +
            objectMapper.writeValueAsString(argumentCaptor.getValue())
        );
        assertEquals(
            responseDto.getUserId(),
            argumentCaptor.getValue().getUserId()
        );
        System.out.println(
            "[TEST]: testCreatePlanBuilder completed successfully."
        );
    }

    @Test
    void testCreatePlanBuilder_InternalServerError() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();

        System.out.println(
            "[TEST]: Executing testCreatePlanBuilder_InternalServerError..."
        );
        System.out.println(
            "[TEST]: Request DTO for internal server error: " +
            objectMapper.writeValueAsString(responseDto)
        );

        when(
            planBuilderControllers.createPlanBuilder(
                any(PlanBuilderResponseDto.class)
            )
        ).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc
            .perform(
                post("/api/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isInternalServerError())
            .andDo(print());
        System.out.println(
            "[TEST]: testCreatePlanBuilder_InternalServerError completed."
        );
    }

    @Test
    void testCreatePlanBuilder_BadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = new PlanBuilderResponseDto();

        System.out.println(
            "[TEST]: Executing testCreatePlanBuilder_BadRequest..."
        );
        System.out.println(
            "[TEST]: Request DTO for bad request (empty): " +
            objectMapper.writeValueAsString(responseDto)
        );

        mockMvc
            .perform(
                post("/api/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
        System.out.println(
            "[TEST]: testCreatePlanBuilder_BadRequest completed."
        );
    }

    @Test
    void testCreatePlanBuilder_PartialBadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = new PlanBuilderResponseDto();
        responseDto.setUserId(UUID.randomUUID());

        System.out.println(
            "[TEST]: Executing testCreatePlanBuilder_PartialBadRequest..."
        );
        System.out.println(
            "[TEST]: Request DTO for partial bad request: " +
            objectMapper.writeValueAsString(responseDto)
        );

        mockMvc
            .perform(
                post("/api/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
        System.out.println(
            "[TEST]: testCreatePlanBuilder_PartialBadRequest completed."
        );
    }

    @Test
    void testCreatePlanBuilder_InvalidValuesBadRequest() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();
        responseDto.setSession("");

        System.out.println(
            "[TEST]: Executing testCreatePlanBuilder_InvalidValuesBadRequest..."
        );
        System.out.println(
            "[TEST]: Request DTO for invalid values bad request: " +
            objectMapper.writeValueAsString(responseDto)
        );

        mockMvc
            .perform(
                post("/api/plans")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
        System.out.println(
            "[TEST]: testCreatePlanBuilder_InvalidValuesBadRequest completed."
        );
    }

    @Test
    void testCreatePlanBuilder_NotFound() throws Exception {
        PlanBuilderResponseDto responseDto = createValidResponseDto();

        mockMvc
            .perform(
                post("/api/nonexistent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseDto))
            )
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    private PlanBuilderResponseDto createValidResponseDto() {
        PlanBuilderResponseDto.Calendar calendarDto =
            new PlanBuilderResponseDto.Calendar();
        calendarDto.setStart_at("2024-07-18T10:00:00");
        calendarDto.setEnd_at("2024-07-18T11:00:00");
        calendarDto.setTitle("Study Session");
        PlanBuilderResponseDto.Subject subjectDto =
            new PlanBuilderResponseDto.Subject("subject", "1h");
        return new PlanBuilderResponseDto(
            UUID.randomUUID(),
            "60",
            "10",
            Collections.singletonList(calendarDto),
            Collections.singletonList(subjectDto)
        );
    }
}
