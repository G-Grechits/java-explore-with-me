package ru.practicum.controller.privateController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.service.privateService.PrivateRequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId) {
        List<ParticipationRequestDto> requests = requestService.getRequests(userId);
        log.info("Получен список заявок на участие.");
        return requests;
    }

    @PostMapping
    public ParticipationRequestDto createRequest(@PathVariable long userId, @RequestParam long eventId) {
        ParticipationRequestDto createdRequest = requestService.createRequest(userId, eventId);
        log.info("Заявка на участие в событии добавлена.");
        return createdRequest;
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId, @PathVariable long requestId) {
        ParticipationRequestDto cancelledRequest = requestService.cancelRequest(userId, requestId);
        log.info("Заявка на участие в событии отозвана.");
        return cancelledRequest;
    }
}
