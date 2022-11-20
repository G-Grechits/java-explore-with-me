package ru.practicum.mapper;

import ru.practicum.State;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.entity.Event;

import java.time.LocalDateTime;

public class EventMapper {

    public static Event toEvent(NewEventDto eventDto) {
        return new Event(null, eventDto.getTitle(), eventDto.getAnnotation(), eventDto.getDescription(), null,
                LocalDateTime.now(), null, DateTimeMapper.toLocalDateTime(eventDto.getEventDate()), null,
                eventDto.getLocation(), eventDto.getPaid(),
                eventDto.getParticipantLimit() != null ? eventDto.getParticipantLimit() : 0,
                eventDto.getRequestModeration(), State.PENDING);
    }

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(event.getId(), event.getTitle(), event.getAnnotation(), event.getDescription(),
                CategoryMapper.toCategoryDto(event.getCategory()), DateTimeMapper.toString(event.getCreatedOn()),
                event.getPublishedOn() != null ? DateTimeMapper.toString(event.getPublishedOn()) : null,
                DateTimeMapper.toString(event.getEventDate()), UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(), event.getPaid(), null, event.getRequestModeration(), event.getState().name(),
                null, null);
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(event.getId(), event.getTitle(), event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()), DateTimeMapper.toString(event.getEventDate()),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), null, null);
    }
}
