package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventBaseDto {
    protected Long id;
    protected String title;
    protected String annotation;
    protected CategoryDto category;
    protected String eventDate;
    protected UserShortDto initiator;
    protected Boolean paid;
    protected Integer views;
    protected Integer confirmedRequests;
}
