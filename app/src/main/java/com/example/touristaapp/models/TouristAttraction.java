package com.example.touristaapp.models;

import java.util.Arrays;
import java.util.List;

public class TouristAttraction {
    private int attractionId;
    private String name;
    private String description;
    private String address;
    private int phoneNumber;
    private String openHours;
    private float longitude;
    private float latitude;
    private String category;
    private Float rating= 0.0F;

    private User user;

    private List<Review> review;
    private List<Photo> photo;
    private List<Event> event;


    public TouristAttraction() {
    }

    public TouristAttraction(int attractionId, String name, String description, String address, int phoneNumber, String openHours, float longitude, float latitude, String category, Float rating) {
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

    public TouristAttraction(int attractionId, String name, String description, String address, int phoneNumber, String openHours, float longitude, float latitude, String category, Float rating, User user, List<Review> review, List<Photo> photo, List<Event> event) {
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
        this.review = review;
        this.photo = photo;
        this.event = event;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Review> getReview() {
        return review;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "TouristAttraction{" +
                "attractionId=" + attractionId +
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
                ", review=" + review +
                ", photo=" + photo +
                ", event=" + event +
                '}';
    }
}
