package ru.hiddenproject.feelmeserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.hiddenproject.feelmeserver.dto.BaseUserDto;
import ru.hiddenproject.feelmeserver.dto.RegisteredUserDto;
import ru.hiddenproject.feelmeserver.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User baseDtoToModel(BaseUserDto dto);
    RegisteredUserDto modelToRegistered(User user);

}
