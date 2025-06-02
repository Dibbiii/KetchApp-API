package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.GroupDto;
import com.alessandra_alessandro.ketchapp.models.entity.GroupEntity;
import com.alessandra_alessandro.ketchapp.repositories.GroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsControllers {
    @Autowired
    public GroupsRepository groupsRepository;

    public GroupDto convertEntityToDto(GroupEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GroupDto(
                entity.getId(),
                entity.getCreatedAt()
        );
    }

    public GroupEntity convertDtoToEntity(GroupDto dto) {
        if (dto == null) {
            return null;
        }
        return new GroupEntity(
                dto.getId(),
                dto.getCreatedAt()
        );
    }

    public List<GroupDto> getGroups() {
        List<GroupEntity> groups = groupsRepository.findAll();
        return groups.stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    public GroupDto getGroup(Integer id) {
        GroupEntity group = groupsRepository.findById(id).orElse(null);
        return convertEntityToDto(group);
    }

    public GroupDto createGroup(GroupDto groupDto) {
        GroupEntity groupEntity = convertDtoToEntity(groupDto);
        GroupEntity savedGroup = groupsRepository.save(groupEntity);
        return convertEntityToDto(savedGroup);
    }

    public GroupDto deleteGroup(Integer id) {
        GroupEntity group = groupsRepository.findById(id).orElse(null);
        if (group != null) {
            groupsRepository.delete(group);
            return convertEntityToDto(group);
        }
        return null;
    }
}
