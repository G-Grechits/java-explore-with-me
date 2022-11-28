package ru.practicum.service.private_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.State;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ForbiddenException("Нельзя добавить уже существующую заявку на участие в событии.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с ID = %d не найден.", userId)));
        Event event = eventRepository.findByIdAndState(eventId, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие с ID = %d не найдено или не было опубликовано.", eventId)));
        if (event.getInitiator().getId() == userId) {
            throw new ForbiddenException("Инициатор события не может добавить заявку на участие в своём событии.");
        }
        if (event.getParticipantLimit() == requestRepository.findAllByEventId(eventId).size()) {
            throw new ForbiddenException("У события достигнут лимит заявок на участие.");
        }
        Request request = new Request(null, user, event, LocalDateTime.now(),
                event.getRequestModeration() ? State.PENDING : State.CONFIRMED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Заявка с ID = %d от пользователя с ID = %d не найдена.", requestId, userId)));
        request.setStatus(State.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}
