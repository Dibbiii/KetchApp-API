package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.dto.*;
import com.alessandra_alessandro.ketchapp.models.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    // Activity

    /**
     * Maps an {@link ActivityEntity} to an {@link ActivityDto}.
     *
     * @param entity the activity entity to map
     * @return the mapped activity DTO
     */
    ActivityDto activityEntityToDto(ActivityEntity entity);

    /**
     * Maps an {@link ActivityDto} to an {@link ActivityEntity}.
     *
     * @param dto the activity DTO to map
     * @return the mapped activity entity
     */
    ActivityEntity activityDtoToEntity(ActivityDto dto);

    // Tomato

    /**
     * Maps a {@link TomatoEntity} to a {@link TomatoDto}.
     *
     * @param entity the tomato entity to map
     * @return the mapped tomato DTO
     */
    TomatoDto tomatoEntityToDto(TomatoEntity entity);

    /**
     * Maps a {@link TomatoDto} to a {@link TomatoEntity}.
     *
     * @param dto the tomato DTO to map
     * @return the mapped tomato entity
     */
    TomatoEntity tomatoDtoToEntity(TomatoDto dto);

    // User

    /**
     * Maps a {@link UserEntity} to a {@link UserDto}.
     *
     * @param entity the user entity to map
     * @return the mapped user DTO
     */
    UserDto userEntityToDto(UserEntity entity);

    /**
     * Maps a {@link UserDto} to a {@link UserEntity}.
     *
     * @param dto the user DTO to map
     * @return the mapped user entity
     */
    UserEntity userDtoToEntity(UserDto dto);
}

