package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.entity.Location;

@Getter
@AllArgsConstructor
public class EventFullDto extends EventBaseDto {
    private String description;
    private String createdOn;
    private String publishedOn;
    private Location location;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String state;

    public EventFullDto(Long id, String title, String annotation, CategoryDto category, String eventDate,
                        UserShortDto initiator, Boolean paid, Integer views, Integer confirmedRequests,
                        String description, String createdOn, String publishedOn, Location location,
                        Integer participantLimit, Boolean requestModeration, String state) {
        super(id, title, annotation, category, eventDate, initiator, paid, views, confirmedRequests);
        this.description = description;
        this.createdOn = createdOn;
        this.publishedOn = publishedOn;
        this.location = location;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.state = state;
    }
}
