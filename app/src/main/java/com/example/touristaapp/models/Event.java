package com.example.touristaapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
public class Event {
    private int eventId;
    private String eventName;
    private String description;
    private Long eventDate;
    private int duration;
    private int userId;

    private TouristAttraction touristAttraction;

    public Event() {
    }

    public Event(int eventId, String eventName, String description, Long eventDate, int duration, int userId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.duration = duration;
        this.userId = userId;
    }

    public Event(int eventId, String eventName, String description, Long eventDate, int duration, int userId, TouristAttraction touristAttraction) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.duration = duration;
        this.userId = userId;
        this.touristAttraction = touristAttraction;
    }

    public int getEventId() {
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

    public void setEventId(int eventId) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
