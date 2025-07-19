package com.alessandra_alessandro.ketchapp.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Calendar {
        @NotBlank(message = "{planbuilder.calendar.title.notblank}")
        @Size(max = 255, message = "{planbuilder.calendar.title.size}")
        private String title;
        @NotBlank(message = "{planbuilder.calendar.start_at.notblank}")
        private String start_at;
        @NotBlank(message = "{planbuilder.calendar.end_at.notblank}")
        private String end_at;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tomato {
        @NotBlank(message = "{planbuilder.tomato.start_at.notblank}")
        private String start_at;
        @NotBlank(message = "{planbuilder.tomato.end_at.notblank}")
        private String end_at;
        @NotBlank(message = "{planbuilder.tomato.pause_end_at.notblank}")
        private String pause_end_at;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Subject {
        @NotBlank(message = "{planbuilder.subject.name.notblank}")
        private String name;
        @NotNull(message = "{planbuilder.subject.tomatoes.notnull}")
        @Size(min = 1, message = "{planbuilder.subject.tomatoes.size}")
        @Valid
        private List<Tomato> tomatoes;
    }

    @NotNull(message = "{planbuilder.calendar.notnull}")
    @Size(min = 1, message = "{planbuilder.calendar.size}")
    @Valid
    private List<Calendar> calendar;

    @NotNull(message = "{planbuilder.subjects.notnull}")
    @Size(min = 1, message = "{planbuilder.subjects.size}")
    @Valid
    private List<Subject> subjects;

    public String toJson() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Error converting PlanBuilderRequestDto to JSON", e);
        }
    }
}

