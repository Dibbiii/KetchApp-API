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
        return new UserDto(
                entity.getUuid(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFirebaseUid(),
                entity.getCreatedAt()
        );
    }

    private UserEntity convertDtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserEntity(
                dto.getFirebaseUid(),
                dto.getEmail(),
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
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
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
    public UserDto getEmailByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Optional<UserEntity> entityOptional = usersRepository.findByUsername(username);
        if (entityOptional.isPresent()) {
            return convertEntityToDto(entityOptional.get());
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
                        tomato.getGroupId(),
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
                        achievement.getAchievementNumber(),
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


    public StatisticsDto getUserStatistics(UUID uuid, LocalDate date) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        List<LocalDate> weekDates = new ArrayList<>(); // TODO: Simplify
        for (int i = 0; i < 7; i++) {
            weekDates.add(date.minusDays(i));
        }

        List<StatisticsDateDto> statisticsDates = new ArrayList<>();
        for (LocalDate weekDate : weekDates) {
            List<String> subjects = usersRepository.findSubjectsByUuidAndDate(uuid, weekDate.toString());
            List<StatisticsSubjectDto> statisticsSubjects = new ArrayList<>();
            for (String subject : subjects) {
                Double totalHours = usersRepository.findTotalHoursByUserAndSubjectAndDate(uuid, subject, weekDate.toString());
                statisticsSubjects.add(new StatisticsSubjectDto(subject, totalHours));
            }
            statisticsDates.add(new StatisticsDateDto(
                    weekDate,
                    statisticsSubjects.stream().mapToDouble(StatisticsSubjectDto::getHours).sum(),
                    statisticsSubjects));
        }
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setDates(statisticsDates);
        return statisticsDto;
    }
}