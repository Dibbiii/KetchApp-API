package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.*;
import com.alessandra_alessandro.ketchapp.models.entity.*;
import com.alessandra_alessandro.ketchapp.repositories.AchievementsRepository;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import com.alessandra_alessandro.ketchapp.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsersControllers {
    private static final Logger log = LoggerFactory.getLogger(UsersControllers.class);

    private final UsersRepository usersRepository;
    private final AchievementsRepository achievementsRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public UsersControllers(UsersRepository usersRepository, AchievementsRepository achievementsRepository, EntityMapper entityMapper) {
        this.usersRepository = usersRepository;
        this.achievementsRepository = achievementsRepository;
        this.entityMapper = entityMapper;
    }

    /**
     * Returns a list of the top 100 users ordered by total hours.
     *
     * @return a list of UserDto representing the users with their total hours
     */
    public List<UserDto> getUsers() {
        log.info("Fetching top 100 users by total hours");
        List<Object[]> results = usersRepository.findTop100UsersByTotalHours();
        List<UserDto> users = new ArrayList<>();
        for (Object[] row : results) {
            UserDto userDto = new UserDto();
            userDto.setId(row[0] != null ? UUID.fromString(row[0].toString()) : null);
            userDto.setUsername(row[1] != null ? row[1].toString() : null);
            userDto.setTotalHours(row[2] != null ? ((Number) row[2]).doubleValue() : 0.0);
            users.add(userDto);
        }
        log.info("Returning {} users", users.size());
        return users;
    }

    /**
     * Returns a user given their UUID.
     *
     * @param id the UUID of the user to retrieve
     * @return the UserDto object corresponding to the found user
     * @throws IllegalArgumentException if the UUID is null or if the user is not found
     */
    public UserDto getUser(UUID id) {
        log.info("Fetching user with UUID: {}", id);
        if (id == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(id);
        if (entityOptional.isPresent()) {
            log.info("User found: {}", entityOptional.get().getUsername());
            return entityMapper.userEntityToDto(entityOptional.get());
        } else {
            log.warn("User with UUID '{}' not found", id);
            throw new IllegalArgumentException("User with UUID '" + id + "' not found.");
        }
    }

    /**
     * Returns the email associated with a specific username.
     *
     * @param username the username of the user whose email to retrieve
     * @return the email of the user corresponding to the provided username
     * @throws IllegalArgumentException if the username is null, empty, or if no user is found
     */
    public String getEmailByUsername(String username) {
        log.info("Fetching email for username: {}", username);
        if (username == null || username.isEmpty()) {
            log.error("Username cannot be null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Optional<UserEntity> entityOptional = usersRepository.findByUsername(username);
        if (entityOptional.isPresent()) {
            log.info("Email found for username {}: {}", username, entityOptional.get().getEmail());
            return entityOptional.get().getEmail();
        } else {
            log.warn("User with username '{}' not found", username);
            throw new IllegalArgumentException("User with username '" + username + "' not found.");
        }
    }

    /**
     * Returns the list of TomatoDto associated with the user identified by the provided UUID.
     *
     * @param uuid the UUID of the user whose tomatoes to retrieve
     * @return list of TomatoDto related to the user
     * @throws IllegalArgumentException if the UUID is null
     */
    public List<TomatoDto> getUserTomatoes(UUID uuid) {
        log.info("Fetching tomatoes for user UUID: {}", uuid);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<TomatoEntity> tomatoes = usersRepository.findTomatoesByUuid(uuid);
        log.info("Found {} tomatoes for user UUID {}", tomatoes.size(), uuid);
        return tomatoes.stream()
                .map(entityMapper::tomatoEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of TomatoDto associated with the user identified by the provided UUID,
     * filtering the results from the specified date (inclusive).
     *
     * @param uuid the UUID of the user whose tomatoes to retrieve
     * @param date the date from which to filter the tomatoes (inclusive); if null, returns all tomatoes
     * @return list of TomatoDto related to the user and filtered by date
     * @throws IllegalArgumentException if the UUID is null
     */
    public List<TomatoDto> getUserTomatoes(UUID uuid, LocalDate date) {
        log.info("Fetching tomatoes for user UUID: {} from date: {}", uuid, date);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<TomatoEntity> tomatoes = usersRepository.findTomatoesByUuid(uuid);
        List<TomatoDto> filtered = tomatoes.stream()
                .filter(tomato -> date == null || !tomato.getCreatedAt().toLocalDateTime().toLocalDate().isBefore(date))
                .map(entityMapper::tomatoEntityToDto)
                .collect(Collectors.toList());
        log.info("Found {} tomatoes for user UUID {} from date {}", filtered.size(), uuid, date);
        return filtered;
    }

    /**
     * Returns the list of TomatoDto associated with the user identified by the provided UUID,
     * filtering the results between the specified dates (both inclusive).
     *
     * @param uuid      the UUID of the user whose tomatoes to retrieve
     * @param startDate the start date of the filter (inclusive)
     * @param endDate   the end date of the filter (inclusive)
     * @return list of TomatoDto related to the user and filtered by date range
     * @throws IllegalArgumentException if the UUID, startDate, or endDate are null
     */
    public List<TomatoDto> getUserTomatoes(UUID uuid, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching tomatoes for user UUID: {} from {} to {}", uuid, startDate, endDate);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (startDate == null || endDate == null) {
            log.error("Both startDate and endDate must be provided");
            throw new IllegalArgumentException("Both startDate and endDate must be provided");
        }
        List<TomatoEntity> tomatoes = usersRepository.findTomatoesByUuid(uuid);
        List<TomatoDto> filtered = tomatoes.stream()
                .filter(tomato -> {
                    LocalDate tomatoDate = tomato.getCreatedAt().toLocalDateTime().toLocalDate();
                    return (tomatoDate.isEqual(startDate) || tomatoDate.isAfter(startDate)) &&
                            (tomatoDate.isEqual(endDate) || tomatoDate.isBefore(endDate));
                })
                .map(entityMapper::tomatoEntityToDto)
                .collect(Collectors.toList());
        log.info("Found {} tomatoes for user UUID {} from {} to {}", filtered.size(), uuid, startDate, endDate);
        return filtered;
    }

    /**
     * Returns the list of activities (ActivityDto) associated with the user identified by the provided UUID.
     *
     * @param uuid the UUID of the user whose activities to retrieve
     * @return list of ActivityDto related to the user
     * @throws IllegalArgumentException if the UUID is null
     */
    public List<ActivityDto> getUserActivities(UUID uuid) {
        log.info("Fetching activities for user UUID: {}", uuid);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        List<ActivityEntity> activities = usersRepository.findActivitiesByUuid(uuid);
        log.info("Found {} activities for user UUID {}", activities.size(), uuid);
        return activities.stream()
                .map(entityMapper::activityEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of AchievementDto associated with the user identified by the provided UUID.
     * Updates or creates the "Studied for 5 hours" and "Completed 10 Tomatoes" achievement records
     * based on the user's current statistics.
     *
     * @param uuid the UUID of the user whose achievements to retrieve
     * @return list of AchievementDto related to the user
     * @throws IllegalArgumentException if the UUID is null
     */
    public List<AchievementDto> getUserAchievements(UUID uuid) {
        log.info("Fetching achievements for user UUID: {}", uuid);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (usersRepository.findById(uuid).isEmpty()) {
            log.warn("User with UUID '{}' not found for achievements", uuid);
            return new ArrayList<>();
        }
        Double totalHours = usersRepository.findTotalHoursByUserUUID(uuid);
        if (totalHours == null) totalHours = 0.0;
        log.info("Total hours for user {}: {}", uuid, totalHours);
        Optional<AchievementEntity> studiedAchievement = usersRepository.findAchievementsByUuid(uuid).stream()
                .filter(a -> "Studied for 5 hours".equals(a.getDescription())).findFirst();
        boolean studiedCompleted = totalHours >= 5.0;
        AchievementEntity studiedEntity = studiedAchievement.orElseGet(() -> new AchievementEntity(uuid, "Studied for 5 hours", studiedCompleted));
        studiedEntity.setCompleted(studiedCompleted);
        achievementsRepository.save(studiedEntity);
        log.info("Updated/created 'Studied for 5 hours' achievement for user {}: completed={}", uuid, studiedCompleted);
        int tomatoCount = Math.toIntExact(usersRepository.countTomatoesByUserUUID(uuid));
        log.info("Tomato count for user {}: {}", uuid, tomatoCount);
        Optional<AchievementEntity> tomatoAchievement = usersRepository.findAchievementsByUuid(uuid).stream()
                .filter(a -> "Completed 10 Tomatoes".equals(a.getDescription())).findFirst();
        boolean tomatoCompleted = tomatoCount >= 10;
        AchievementEntity tomatoEntity = tomatoAchievement.orElseGet(() -> new AchievementEntity(uuid, "Completed 10 Tomatoes", tomatoCompleted));
        tomatoEntity.setCompleted(tomatoCompleted);
        achievementsRepository.save(tomatoEntity);
        log.info("Updated/created 'Completed 10 Tomatoes' achievement for user {}: completed={}", uuid, tomatoCompleted);
        List<AchievementEntity> achievements = usersRepository.findAchievementsByUuid(uuid);
        if (achievements == null) return new ArrayList<>();
        log.info("Returning {} achievements for user {}", achievements.size(), uuid);
        return achievements.stream()
                .map(achievementEntity -> new AchievementDto(
                        achievementEntity.getId(),
                        achievementEntity.getUserUUID(),
                        achievementEntity.getDescription(),
                        achievementEntity.getCompleted(),
                        achievementEntity.getCreatedAt()
                )).collect(Collectors.toList());
    }

    /**
     * Returns the user's statistics for a specified date range.
     *
     * @param uuid      the UUID of the user whose statistics to retrieve
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return a StatisticsDto object containing the user's daily and subject-specific statistics
     * @throws IllegalArgumentException if uuid, startDate, or endDate are null, or if endDate is before startDate
     */
    public StatisticsDto getUserStatistics(UUID uuid, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching statistics for user UUID: {} from {} to {}", uuid, startDate, endDate);
        if (uuid == null) {
            log.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (startDate == null || endDate == null) {
            log.error("Start date and end date cannot be null");
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            log.error("End date cannot be before start date");
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        List<LocalDate> rangeDates = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            rangeDates.add(current);
            current = current.plusDays(1);
        }
        log.info("Date range for statistics: {}", rangeDates);
        List<StatisticsDateDto> statisticsDates = new ArrayList<>();
        for (LocalDate date : rangeDates) {
            List<String> subjects = usersRepository.findSubjectsByUuidAndDate(uuid, date.toString());
            log.info("Subjects for user {} on {}: {}", uuid, date, subjects);
            List<StatisticsSubjectDto> statisticsSubjects = new ArrayList<>();
            for (String subject : subjects) {
                Double totalHours = usersRepository.findTotalHoursByUserAndSubjectAndDate(uuid, subject, date.toString());
                log.info("Total hours for user {} on {} for subject '{}': {}", uuid, date, subject, totalHours);
                statisticsSubjects.add(new StatisticsSubjectDto(subject, totalHours != null ? totalHours : 0.0));
            }
            statisticsDates.add(new StatisticsDateDto(
                    date,
                    statisticsSubjects.stream().mapToDouble(StatisticsSubjectDto::getHours).sum(),
                    statisticsSubjects));
        }
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setDates(statisticsDates);
        log.info("Returning statisticsDto for user {}: {}", uuid, statisticsDto);
        return statisticsDto;
    }

    /**
     * Creates a new user from a UserDto.
     *
     * @param newUserDto the user data to create
     * @return the created UserDto
     * @throws IllegalArgumentException if username or email is missing or already exists
     */
    public NewUserDto createUser(NewUserDto newUserDto) {
        log.info("Creating user: {}", newUserDto.getUsername());
        if (newUserDto.getUsername() == null || newUserDto.getUsername().isBlank()) {
            log.error("Username cannot be null or blank");
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (usersRepository.findByUsername(newUserDto.getUsername()).isPresent()) {
            log.error("Username '{}' already exists", newUserDto.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        UserEntity entity = new UserEntity();
        entity.setUsername(newUserDto.getUsername());
        entity.setEmail(newUserDto.getEmail());
        UserEntity saved = usersRepository.save(entity);
        log.info("User created with UUID: {}", saved.getId());
        return new NewUserDto(saved.getId().toString(), saved.getUsername(), saved.getEmail());
    }
}
