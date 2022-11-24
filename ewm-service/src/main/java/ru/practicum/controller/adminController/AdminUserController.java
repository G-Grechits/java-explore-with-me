package ru.practicum.controller.adminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.UserDto;
import ru.practicum.service.adminService.AdminUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        List<UserDto> users;
        if (ids == null || ids.isEmpty()) {
            users = userService.getAllUsers(from, size);
        } else {
            users = userService.getUsersByIds(ids);
        }
        log.info("Получен список пользователей.");
        return users;
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        log.info("Пользователь {} зарегистрирован.", createdUser.getName());
        return createdUser;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        log.info("Пользователь с ID = {} удалён.", userId);
    }
}
