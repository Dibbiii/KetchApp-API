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

    @Query(value = """
            SELECT u.uuid, u.username, COALESCE(SUM(EXTRACT(EPOCH FROM (a_stop.created_at - a_start.created_at)) / 3600.0), 0) AS total_hours
            FROM Users u
            LEFT JOIN Activities a_start ON u.uuid = a_start.user_uuid AND a_start.type = 'TIMER' AND a_start.action = 'START'
            LEFT JOIN Activities a_stop ON a_start.tomato_id = a_stop.tomato_id AND a_stop.type = 'TIMER' AND a_stop.action = 'END'
            GROUP BY u.uuid, u.username
            ORDER BY total_hours DESC
            LIMIT 100
            """, nativeQuery = true)
    List<Object[]> findTop100UsersByTotalHours();

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

    @Query("SELECT COUNT(t) FROM TomatoEntity t WHERE t.userUUID = :userUUID")
    long countTomatoesByUserUUID(@Param("userUUID") UUID userUUID);
}
