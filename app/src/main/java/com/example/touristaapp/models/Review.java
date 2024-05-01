package com.example.touristaapp.models;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private int timestamp;
    private TouristAttraction touristAttraction;

    public Review() {
    }

    public Review(int reviewId, int rating, String comment, int timestamp) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public Review(int reviewId, int rating, String comment, int timestamp, TouristAttraction touristAttraction) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.touristAttraction = touristAttraction;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", touristAttraction=" + touristAttraction +
                '}';
    }
}
