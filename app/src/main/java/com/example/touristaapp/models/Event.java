package com.example.touristaapp.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    private int eventId;
    private String eventName;
    private String description;
    private Date eventDate;
    private LocalDateTime time;
    private int duration;
    private TouristAttraction touristAttraction;

    public Event() {
    }

    public Event(int eventId, String eventName, String description, Date eventDate, LocalDateTime time, int duration) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.time = time;
        this.duration = duration;
    }

    public Event(int eventId, String eventName, String description, Date eventDate, LocalDateTime time, int duration, TouristAttraction touristAttraction) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.time = time;
        this.duration = duration;
        this.touristAttraction = touristAttraction;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", time=" + time +
                ", duration=" + duration +
                ", touristAttraction=" + touristAttraction +
                '}';
    }
}
