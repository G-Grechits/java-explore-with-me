package ru.practicum.dto;

import lombok.Getter;

@Getter
public class EventShortDto extends EventBaseDto {

    public EventShortDto(Long id, String title, String annotation, CategoryDto category, String eventDate,
                         UserShortDto initiator, Boolean paid, Integer views, Integer confirmedRequests) {
        super(id, title, annotation, category, eventDate, initiator, paid, views, confirmedRequests);
    }
}
