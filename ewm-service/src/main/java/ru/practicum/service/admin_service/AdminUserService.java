package ru.practicum.service.admin_service;

import ru.practicum.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getAllUsers(int from, int size);

    List<UserDto> getUsersByIds(List<Long> ids);

    UserDto createUser(UserDto userDto);

    void deleteUser(long id);
}
