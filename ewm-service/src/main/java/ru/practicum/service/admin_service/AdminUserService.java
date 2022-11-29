package ru.practicum.service.admin_service;

import ru.practicum.dto.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getAll(int from, int size);

    List<UserDto> getByIds(List<Long> ids);

    UserDto create(UserDto userDto);

    void delete(long id);
}
