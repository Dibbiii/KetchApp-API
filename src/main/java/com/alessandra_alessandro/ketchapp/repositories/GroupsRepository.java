package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<GroupEntity, Integer> {
}