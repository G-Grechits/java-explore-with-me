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

import static ru.practicum.mapper.UserMapper.toUser;
import static ru.practicum.mapper.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        return userRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ConflictException(
                    String.format("Пользователь с электронной почтой '%s' уже зарегистрирован.", userDto.getEmail()));
        }
        User user = userRepository.save(toUser(userDto));

        return toUserDto(user);
    }

    @Override
    public void delete(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID = %d не найден.", id)));
        userRepository.deleteById(id);
    }
}
