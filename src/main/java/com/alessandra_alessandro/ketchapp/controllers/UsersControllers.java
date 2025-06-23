package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.*;
import com.alessandra_alessandro.ketchapp.models.entity.*;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersControllers {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersControllers(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private UserDto convertEntityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUuid(entity.getUuid());
        dto.setUsername(entity.getUsername());
        // Not setting totalTomatoes or totalHours here, as this is a simple converter
        return dto;
    }

    private UserEntity convertDtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserEntity(
                dto.getUuid(),
                dto.getUsername()
        );
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        UserEntity userEntity = convertDtoToEntity(userDto);
        userEntity = usersRepository.save(userEntity);
        return convertEntityToDto(userEntity);
    }

    @Transactional
    public UserDto deleteUser(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(uuid);
        if (entityOptional.isPresent()) {
            usersRepository.delete(entityOptional.get());
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("User with UUID '" + uuid + "' not found.");
        }
    }

    public List<UserDto> getUsers() {
        List<UserEntity> entities = usersRepository.findAll();
        List<UserDto> users = new ArrayList<>();
        for (UserEntity entity : entities) {
            double totalHours = 0.0;
            // Usa i nuovi metodi senza filtro data
            List<String> subjects = usersRepository.findSubjectsByUuid(entity.getUuid());
            for (String subject : subjects) {
                Double subjectHours = usersRepository.findTotalHoursByUserAndSubject(entity.getUuid(), subject);
                if (subjectHours != null) {
                    totalHours += subjectHours;
                }
            }
            UserDto userDto = new UserDto();
            userDto.setUuid(entity.getUuid());
            userDto.setUsername(entity.getUsername());
            userDto.setTotalHours(totalHours);
            users.add(userDto);
        }
        return users;
    }

    public UserDto getUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(id);
        if (entityOptional.isPresent()) {
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("User with UUID '" + id + "' not found.");
        }
    }

    // Restituisce la mail dato l'username
    public String getEmailByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Optional<UserEntity> entityOptional = usersRepository.findByUsername(username);
        if (entityOptional.isPresent()) {
            return entityOptional.get().getEmail();
        } else {
            throw new IllegalArgumentException("User with username '" + username + "' not found.");
        }
    }

    public List<TomatoDto> getUserTomatoes(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<TomatoEntity> tomatoes = usersRepository.findTomatoesByUuid(uuid);
        return tomatoes.stream()
                .map(tomato -> new TomatoDto(
                        tomato.getId(),
                        tomato.getUserUUID(),
                        tomato.getStartAt(),
                        tomato.getEndAt(),
                        tomato.getPauseEnd(),
                        tomato.getNextTomatoId(),
                        tomato.getSubject(),
                        tomato.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public List<TomatoDto> getUserTomatoes(UUID uuid, LocalDate date) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<TomatoEntity> tomatoes = usersRepository.findTomatoesByUuid(uuid);
        return tomatoes.stream()
                .filter(tomato -> date == null || !tomato.getCreatedAt().toLocalDateTime().toLocalDate().isBefore(date))
                .map(tomato -> new TomatoDto(
                        tomato.getId(),
                        tomato.getUserUUID(),
                        tomato.getStartAt(),
                        tomato.getEndAt(),
                        tomato.getPauseEnd(),
                        tomato.getNextTomatoId(),
                        tomato.getSubject(),
                        tomato.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public List<ActivityDto> getUserActivities(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<ActivityEntity> activities = usersRepository.findActivitiesByUuid(uuid);
        return activities.stream()
                .map(activity -> new ActivityDto(
                        activity.getId(),
                        activity.getUserUUID(),
                        activity.getTomatoId(),
                        activity.getType(),
                        activity.getAction(),
                        activity.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public List<AchievementDto> getUserAchievements(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<AchievementEntity> achievements = usersRepository.findAchievementsByUuid(uuid);
        return achievements.stream()
                .map(achievement -> new AchievementDto(
                        achievement.getId(),
                        achievement.getUserUUID(),
                        achievement.getDescription(),
                        achievement.getCompleted(),
                        achievement.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public List<FriendDto> getUserFriends(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<FriendEntity> friends = usersRepository.findFriendsByUuid(uuid);
        return friends.stream()
                .map(friend -> new FriendDto(
                        friend.getId(),
                        friend.getUserUUID(),
                        friend.getFriendUUID(),
                        friend.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public List<AppointmentDto> getUserAppointments(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<AppointmentEntity> appointments = usersRepository.findAppointmentsByUuid(uuid);
        return appointments.stream()
                .map(appointment -> new AppointmentDto(
                        appointment.getId(),
                        appointment.getUserUUID(),
                        appointment.getName(),
                        appointment.getStartAt(),
                        appointment.getEndAt(),
                        appointment.getCreatedAt()
                )).collect(Collectors.toList());
    }


    public UUID getUserUUIDByFirebaseUID(String firebaseUID) {
        if (firebaseUID == null || firebaseUID.isEmpty()) {
            throw new IllegalArgumentException("Firebase UID cannot be null or empty");
        }
        Optional<UserEntity> entityOptional = usersRepository.findByFirebaseUid(firebaseUID);
        if (entityOptional.isPresent()) {
            return entityOptional.get().getUuid();
        } else {
            throw new IllegalArgumentException("User with Firebase UID '" + firebaseUID + "' not found.");
        }
    }

    public StatisticsDto getUserStatistics(UUID uuid, LocalDate startDate, LocalDate endDate) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        List<LocalDate> rangeDates = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            rangeDates.add(current);
            current = current.plusDays(1);
        }
        List<StatisticsDateDto> statisticsDates = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            List<String> subjects = usersRepository.findSubjectsByUuidAndDate(uuid, date.toString());
            List<StatisticsSubjectDto> statisticsSubjects = new ArrayList<>();
            for (String subject : subjects) {
                Double totalHours = usersRepository.findTotalHoursByUserAndSubjectAndDate(uuid, subject, date.toString());
                statisticsSubjects.add(new StatisticsSubjectDto(subject, totalHours != null ? totalHours : 0.0));
            }
            statisticsDates.add(new StatisticsDateDto(
                    date,
                    statisticsSubjects.stream().mapToDouble(StatisticsSubjectDto::getHours).sum(),
                    statisticsSubjects));
        }
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setDates(statisticsDates);
        return statisticsDto;
    }
}