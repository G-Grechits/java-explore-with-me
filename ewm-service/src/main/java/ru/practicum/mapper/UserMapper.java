package ru.practicum.mapper;

import ru.practicum.dto.UserDto;
import ru.practicum.dto.UserShortDto;
import ru.practicum.entity.User;

public class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
