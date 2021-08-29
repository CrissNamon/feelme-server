package ru.hiddenproject.feelmeserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.dto.RegisteredUserDto;
import ru.hiddenproject.feelmeserver.model.User;

/**
 * Mapper for {@link ru.hiddenproject.feelmeserver.model.User} model
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Mapper instance
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts {@link ru.hiddenproject.feelmeserver.dto.BaseUserDto} to {@link ru.hiddenproject.feelmeserver.model.User} model
     * @param dto DTO to convert
     * @return User model
     */
    User baseDtoToModel(BaseUserDto dto);

    /**
     * Converts {@link ru.hiddenproject.feelmeserver.model.User} model to {@link ru.hiddenproject.feelmeserver.dto.RegisteredUserDto}
     * @param user Model to convert
     * @return Registered user DTO
     */
    RegisteredUserDto modelToRegistered(User user);

}
