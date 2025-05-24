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

    @Query("SELECT f FROM FriendEntity f WHERE f.userUUID = :userUUID")
    List<FriendEntity> findFriendsByUuid(@Param("userUUID") UUID userUUID);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.userUUID = :userUUID")
    List<AppointmentEntity> findAppointmentsByUuid(@Param("userUUID") UUID userUUID);

    @Query("""
            SELECT t.id
            FROM TomatoEntity t
            WHERE t.userUUID = :userUUID
              AND EXISTS (
                SELECT 1 FROM ActivityEntity a WHERE a.tomatoId = t.id AND a.type = 'TIMER' AND a.action = 'START'
              )
              AND EXISTS (
                SELECT 1 FROM ActivityEntity a WHERE a.tomatoId = t.id AND a.type = 'TIMER' AND a.action = 'END'
              )
            """)
    List<ActivityEntity> findTomatoIdsWithStartAndEnd(@Param("userUUID") UUID userUUID);
}