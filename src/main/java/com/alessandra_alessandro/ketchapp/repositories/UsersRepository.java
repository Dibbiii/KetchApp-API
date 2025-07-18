package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT t FROM TomatoEntity t WHERE t.userUUID = :userUUID")
    List<TomatoEntity> findTomatoesByUuid(@Param("userUUID") UUID userUUID);

    @Query("SELECT a FROM ActivityEntity a WHERE a.userUUID = :userUUID")
    List<ActivityEntity> findActivitiesByUuid(@Param("userUUID") UUID userUUID);

    @Query("SELECT a FROM AchievementEntity a WHERE a.userUUID = :userUUID")
    List<AchievementEntity> findAchievementsByUuid(@Param("userUUID") UUID userUUID);

    @Query("SELECT DISTINCT t.subject FROM TomatoEntity t WHERE t.userUUID = :userUUID AND DATE(t.createdAt) = DATE(:date) ORDER BY t.subject")
    List<String> findSubjectsByUuidAndDate(@Param("userUUID") UUID userUUID, @Param("date") String date);

    @Query(
            value = """
                    SELECT SUM(EXTRACT(EPOCH FROM (a_stop.created_at - a_start.created_at))) / 3600.0 AS total_hours
                    FROM Activities a_start JOIN Activities a_stop ON a_start.tomato_id = a_stop.tomato_id
                                            JOIN Tomatoes t ON a_start.tomato_id = t.id
                    WHERE a_start.user_uuid = :userUUID
                      AND a_start.type = 'TIMER'
                      AND a_start.action = 'START'
                      AND a_stop.type = 'TIMER'
                      AND a_stop.action = 'END'
                      AND t.subject = :subject
                      AND DATE(a_start.created_at) = DATE (:date)""",
            nativeQuery = true
    )
    Double findTotalHoursByUserAndSubjectAndDate(
            @Param("userUUID") UUID userUUID,
            @Param("subject") String subject,
            @Param("date") String date);

    @Query("SELECT DISTINCT t.subject FROM TomatoEntity t WHERE t.userUUID = :userUUID ORDER BY t.subject")
    List<String> findSubjectsByUuid(@Param("userUUID") UUID userUUID);

    @Query(value = """
            SELECT SUM(EXTRACT(EPOCH FROM (a_stop.created_at - a_start.created_at))) / 3600.0 AS total_hours
            FROM Activities a_start JOIN Activities a_stop ON a_start.tomato_id = a_stop.tomato_id
                                    JOIN Tomatoes t ON a_start.tomato_id = t.id
            WHERE a_start.user_uuid = :userUUID
              AND a_start.type = 'TIMER'
              AND a_start.action = 'START'
              AND a_stop.type = 'TIMER'
              AND a_stop.action = 'END'
              AND t.subject = :subject
            """, nativeQuery = true)
    Double findTotalHoursByUserAndSubject(
            @Param("userUUID") UUID userUUID,
            @Param("subject") String subject);

    Optional<UserEntity> findByFirebaseUid(String firebaseUid);

    @Query(value = """
            SELECT SUM(EXTRACT(EPOCH FROM (a_stop.created_at - a_start.created_at))) / 3600.0 AS total_hours
            FROM Activities a_start JOIN Activities a_stop ON a_start.tomato_id = a_stop.tomato_id
            WHERE a_start.user_uuid = :userUUID
              AND a_start.type = 'TIMER'
              AND a_start.action = 'START'
              AND a_stop.type = 'TIMER'
              AND a_stop.action = 'END'
            """, nativeQuery = true)
    Double findTotalHoursByUserUUID(@Param("userUUID") UUID userUUID);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT t FROM TomatoEntity t WHERE t.userUUID = :userUUID AND FUNCTION('DATE', t.createdAt) = CAST(:date AS date)")
    List<TomatoEntity> findTomatoesByUuidAndDate(@Param("userUUID") UUID userUUID, @Param("date") String date);

    @Query("SELECT COUNT(t) FROM TomatoEntity t WHERE t.userUUID = :userUUID")
    long countTomatoesByUserUUID(@Param("userUUID") UUID userUUID);
}
