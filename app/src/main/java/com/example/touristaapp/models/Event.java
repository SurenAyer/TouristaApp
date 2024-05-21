package com.example.touristaapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
public class Event {
    private String eventId;
    private String eventName;
    private String description;
    private Long eventDate;
    private int duration;
    private String userId;

    private TouristAttraction touristAttraction;

    public Event() {
    }

    public Event(String eventId, String eventName, String description, Long eventDate, int duration, String userId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.duration = duration;
        this.userId = userId;
    }

    public Event(String eventId, String eventName, String description, Long eventDate, int duration, String userId, TouristAttraction touristAttraction) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.duration = duration;
        this.userId = userId;
        this.touristAttraction = touristAttraction;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDescription() {
        return description;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public int getDuration() {
        return duration;
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", duration=" + duration +
                ", userId=" + userId +
                ", touristAttraction=" + touristAttraction +
                '}';
    }
}
