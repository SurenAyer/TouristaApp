package com.example.touristaapp.models;

import java.util.Arrays;

public class TouristAttraction {
    private int attractionId;
    private String name;
    private String description;
    private String address;
    private int phoneNumber;
    private float longitude;
    private float latitude;
    private String category;
    private Float rating= 0.0F;

    private User user;

    private Review[] reviews;
    private Photo[] photos;
    private Event[] events;


    public TouristAttraction() {
    }

    public TouristAttraction(int attractionId, String name, String description, String address, int phoneNumber, float longitude, float latitude, String category, Float rating) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.rating = rating;
    }

    public TouristAttraction(int attractionId, String name, String description, String address, int phoneNumber, float longitude, float latitude, String category, Float rating, User user, Review[] reviews, Photo[] photos, Event[] events) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.rating = rating;
        this.user = user;
        this.reviews = reviews;
        this.photos = photos;
        this.events = events;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public Photo[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photo[] photos) {
        this.photos = photos;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "TouristAttraction{" +
                "attractionId=" + attractionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", user=" + user +
                ", reviews=" + Arrays.toString(reviews) +
                ", photos=" + Arrays.toString(photos) +
                ", events=" + Arrays.toString(events) +
                '}';
    }
}
