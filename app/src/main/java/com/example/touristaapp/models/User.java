package com.example.touristaapp.models;

import java.util.Arrays;
import java.util.List;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private List<String> interest;

    private List<TouristAttraction> touristAttractions;
    private List<Review> reviews;
    private List<Event> events;

    private List<Photo> photos;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email, String password, int phoneNumber, List<String> interest) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.interest = interest;
    }

    public User(int userId, String firstName, String lastName, String email, String password, int phoneNumber, List<String> interest, List<TouristAttraction> touristAttractions, List<Review> reviews, List<Event> events, List<Photo> photos) {
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getInterest() {
        return interest;
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public void setTouristAttractions(List<TouristAttraction> touristAttractions) {
        this.touristAttractions = touristAttractions;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setPhotos(List<Photo> photos) {
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
                ", interest=" + interest +
                ", touristAttractions=" + touristAttractions +
                ", reviews=" + reviews +
                ", events=" + events +
                ", photos=" + photos +
                '}';
    }
}

