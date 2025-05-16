package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.FriendDto;
import com.alessandra_alessandro.ketchapp.models.entity.FriendEntity;
import com.alessandra_alessandro.ketchapp.repositories.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendsControllers {
    private final FriendsRepository friendsRepository;

    @Autowired
    public FriendsControllers(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    public FriendDto convertEntityToDto(FriendEntity entity) {
        if (entity == null) {
            return null;
        }
        return new FriendDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getFriendUUID(),
                entity.getCreatedAt()
        );
    }

    public FriendEntity convertDtoToEntity(FriendDto dto) {
        if (dto == null) {
            return null;
        }
        return new FriendEntity(
                dto.getUserUUID(),
                dto.getFriendUUID()
        );
    }

    public List<FriendDto> getFriends() {
        List<FriendEntity> friends = friendsRepository.findAll();
        return friends.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public FriendDto getFriendship(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<FriendEntity> friendEntity = friendsRepository.findById(id);
        if (friendEntity.isPresent()) {
            return convertEntityToDto(friendEntity.get());
        } else {
            throw new IllegalArgumentException("Friend not found");
        }
    }

    public FriendDto createFriendship(FriendDto friendDto) {
        if (friendDto == null) {
            throw new IllegalArgumentException("FriendDto cannot be null");
        }
        FriendEntity friendEntity = convertDtoToEntity(friendDto);
        FriendEntity savedFriend = friendsRepository.save(friendEntity);
        return convertEntityToDto(savedFriend);
    }

    public FriendDto deleteFriendship(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<FriendEntity> friendEntity = friendsRepository.findById(id);
        if (friendEntity.isPresent()) {
            friendsRepository.delete(friendEntity.get());
            return convertEntityToDto(friendEntity.get());
        } else {
            throw new IllegalArgumentException("Friend not found");
        }
    }
}