package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FriendsRepository extends JpaRepository<FriendEntity, UUID> {
    // TODO: See if we need to add more methods here FriendsRepository
}
