package ru.practicum.service.admin_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.UserDto;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return userRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ConflictException(
                    String.format("Пользователь с электронной почтой '%s' уже зарегистрирован.", userDto.getEmail()));
        }
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID = %d не найден.", id)));
        userRepository.deleteById(id);
    }
}
