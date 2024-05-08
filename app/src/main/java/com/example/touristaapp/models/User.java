package com.example.touristaapp.models;

import java.util.Arrays;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String[] interest;

    private TouristAttraction[] touristAttractions;
    private Review[] reviews;
    private Event[] events;

    private Photo[] photos;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email, String password, int phoneNumber, String[] interest) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.interest = interest;
    }

    public User(int userId, String firstName, String lastName, String email, String password, int phoneNumber, String[] interest, TouristAttraction[] touristAttractions, Review[] reviews, Event[] events, Photo[] photos) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.interest = interest;
        this.touristAttractions = touristAttractions;
        this.reviews = reviews;
        this.events = events;
        this.photos = photos;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getInterest() {
        return interest;
    }

    public void setInterest(String[] interest) {
        this.interest = interest;
    }

    public TouristAttraction[] getTouristAttractions() {
        return touristAttractions;
    }

    public void setTouristAttractions(TouristAttraction[] touristAttractions) {
        this.touristAttractions = touristAttractions;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Photo[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photo[] photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", interest=" + Arrays.toString(interest) +
                ", touristAttractions=" + Arrays.toString(touristAttractions) +
                ", reviews=" + Arrays.toString(reviews) +
                ", events=" + Arrays.toString(events) +
                ", photos=" + Arrays.toString(photos) +
                '}';
    }
}

