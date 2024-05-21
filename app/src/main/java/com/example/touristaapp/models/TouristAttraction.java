package com.example.touristaapp.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class TouristAttraction {
    private String attractionId;
    private String name;
    private String description;
    private String address;
    private long phoneNumber;
    private String openHours;
    private float longitude;
    private float latitude;
    private String category;
    private Float rating= 0.0F;

    private User user;

    private List<Review> reviews;
    private List<Photo> photos;
    private List<Event> events;


    public TouristAttraction() {
    }

    public TouristAttraction(String attractionId, String name, String description, String address, long phoneNumber, String openHours, float longitude, float latitude, String category, Float rating) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openHours = openHours;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.rating = rating;
    }

    public TouristAttraction(String attractionId, String name, String description, String address, long phoneNumber, String openHours, float longitude, float latitude, String category, Float rating, User user, List<Review> reviews, List<Photo> photos, List<Event> events) {
        this.attractionId = attractionId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openHours = openHours;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.rating = rating;
        this.user = user;
        this.reviews = reviews;
        this.photos = photos;
        this.events = events;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
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

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
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

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


    @Override
    public String toString() {
        return "TouristAttraction{" +
                "attractionId='" + attractionId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", openHours='" + openHours + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                ", user=" + user +
                ", reviews=" + reviews +
                ", photos=" + photos +
                ", events=" + events +
                '}';
    }
}
