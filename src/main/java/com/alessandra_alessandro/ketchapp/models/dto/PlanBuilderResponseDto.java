package com.alessandra_alessandro.ketchapp.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class PlanBuilderResponseDto {

    @NotNull(message = "{planbuilder.userUUID.notnull}")
    private UUID userId;

    @NotBlank(message = "{planbuilder.session.notblank}")
    private String session;

    @NotBlank(message = "{planbuilder.breakDuration.notblank}")
    private String breakDuration;

    @Valid
    @NotNull(message = "{planbuilder.calendar.notnull}")
    @Size(min = 1, message = "{planbuilder.calendar.size}")
    private List<Calendar> calendar;

    @Valid
    @NotNull(message = "{planbuilder.subjects.notnull}")
    @Size(min = 1, message = "{planbuilder.subjects.size}")
    private List<Subject> subjects;

    public PlanBuilderResponseDto() {}

    public PlanBuilderResponseDto(
        UUID userId,
        String session,
        String breakDuration,
        List<Calendar> calendar,
        List<Subject> subjects
    ) {
        this.userId = userId;
        this.session = session;
        this.breakDuration = breakDuration;
        this.calendar = calendar;
        this.subjects = subjects;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getBreakDuration() {
        return breakDuration;
    }

    public void setBreakDuration(String breakDuration) {
        this.breakDuration = breakDuration;
    }

    public List<Calendar> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Calendar> calendar) {
        this.calendar = calendar;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public static class Calendar {

        @NotBlank(message = "{planbuilder.calendar.start_at.notblank}")
        private String start_at;

        @NotBlank(message = "{planbuilder.calendar.end_at.notblank}")
        private String end_at;

        @NotBlank(message = "{planbuilder.calendar.title.notblank}")
        private String title;

        public Calendar() {}

        public Calendar(String start_at, String end_at, String title) {
            this.start_at = start_at;
            this.end_at = end_at;
            this.title = title;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Subject {

        @NotBlank(message = "{planbuilder.subject.name.notblank}")
        private String name;

        @NotBlank(message = "{planbuilder.subject.duration.notblank}")
        private String duration;

        public Subject() {}

        public Subject(String name, String duration) {
            this.name = name;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    public static class Notification {

        private boolean enabled;
        private String sound;
        private boolean vibration;

        public Notification() {}

        public Notification(boolean enabled, String sound, boolean vibration) {
            this.enabled = enabled;
            this.sound = sound;
            this.vibration = vibration;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public boolean isVibration() {
            return vibration;
        }

        public void setVibration(boolean vibration) {
            this.vibration = vibration;
        }
    }

    public static class Tomato {

        @NotBlank(message = "{planbuilder.tomato.start_at.notblank}")
        private String start_at;

        @NotBlank(message = "{planbuilder.tomato.end_at.notblank}")
        private String end_at;

        @NotBlank(message = "{planbuilder.tomato.pause_end_at.notblank}")
        private String pause_end_at;

        public Tomato() {}

        public Tomato(String start_at, String end_at, String pause_end_at) {
            this.start_at = start_at;
            this.end_at = end_at;
            this.pause_end_at = pause_end_at;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public String getPause_end_at() {
            return pause_end_at;
        }

        public void setPause_end_at(String pause_end_at) {
            this.pause_end_at = pause_end_at;
        }
    }
}
