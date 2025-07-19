package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseDto {
    @NotNull(message = "{planbuilder.userUUID.notnull}")
    private UUID userUUID;

    @NotBlank(message = "{planbuilder.session.notblank}")
    private String session;

    @NotBlank(message = "{planbuilder.breakDuration.notblank}")
    private String breakDuration;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Calendar {
        @NotBlank(message = "{planbuilder.calendar.start_at.notblank}")
        private String start_at;
        @NotBlank(message = "{planbuilder.calendar.end_at.notblank}")
        private String end_at;
        @NotBlank(message = "{planbuilder.calendar.title.notblank}")
        private String title;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Subject {
        @NotBlank(message = "{planbuilder.subject.name.notblank}")
        private String name;
        @NotBlank(message = "{planbuilder.subject.duration.notblank}")
        private String duration;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Notification {
        private boolean enabled;
        private String sound;
        private boolean vibration;
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

    @Valid
    @NotNull(message = "{planbuilder.calendar.notnull}")
    @Size(min = 1, message = "{planbuilder.calendar.size}")
    private List<Calendar> calendar;

    @Valid
    @NotNull(message = "{planbuilder.subjects.notnull}")
    @Size(min = 1, message = "{planbuilder.subjects.size}")
    private List<Subject> subjects;

}
