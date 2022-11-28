package ru.practicum.mapper;

import ru.practicum.State;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;

import java.time.LocalDateTime;

public class EventMapper {

    public static Event toEvent(NewEventDto eventDto, Category category, User initiator) {
        return new Event(null, eventDto.getTitle(), eventDto.getAnnotation(), eventDto.getDescription(), category,
                LocalDateTime.now(), null, DateTimeMapper.toLocalDateTime(eventDto.getEventDate()), initiator,
                eventDto.getLocation(), eventDto.getPaid(),
                eventDto.getParticipantLimit() != null ? eventDto.getParticipantLimit() : 0,
                eventDto.getRequestModeration(), State.PENDING);
    }

    public static EventFullDto toEventFullDto(Event event, int views, int confirmedRequests) {
        return new EventFullDto(event.getId(), event.getTitle(), event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()), DateTimeMapper.toString(event.getEventDate()),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), views, confirmedRequests,
                event.getDescription(), DateTimeMapper.toString(event.getCreatedOn()),
                event.getPublishedOn() != null ? DateTimeMapper.toString(event.getPublishedOn()) : null,
                event.getLocation(), event.getParticipantLimit(), event.getRequestModeration(), event.getState().name());
    }

    public static EventShortDto toEventShortDto(Event event, int views, int confirmedRequests) {
        return new EventShortDto(event.getId(), event.getTitle(), event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()), DateTimeMapper.toString(event.getEventDate()),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), views, confirmedRequests);
    }
}
