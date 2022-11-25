package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mapper.DateTimeMapper;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        log.error("Возникла ошибка 400: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Условия выполнения операции не соблюдены.")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingParameterException(final MissingServletRequestParameterException e) {
        log.error("Возникла ошибка 400: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Условия выполнения операции не соблюдены.")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Возникла ошибка 400: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Условия выполнения операции не соблюдены.")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(final ForbiddenException e) {
        log.error("Возникла ошибка 403: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Условия выполнения операции не соблюдены.")
                .status(HttpStatus.FORBIDDEN.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.error("Возникла ошибка 404: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Запрашиваемый объект не найден.")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        log.error("Возникла ошибка 409: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Запрос приводит к нарушению целостности данных.")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        log.error("Возникла непредвиденная ошибка: {}", e.getMessage());
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Внутренняя ошибка сервера.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(DateTimeMapper.toString(LocalDateTime.now()))
                .build();
    }
}
