package com.example.touristaapp.models;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private Long timestamp;
    private String userName;
    private TouristAttraction touristAttraction;

    public Review() {
    }

    public Review(int reviewId, int rating, String comment, Long timestamp, String userName) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.userName = userName;
    }

    public Review(int reviewId, int rating, String comment, Long timestamp, String userName, TouristAttraction touristAttraction) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.userName = userName;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
                ", userName='" + userName + '\'' +
                ", touristAttraction=" + touristAttraction +
                '}';
    }
}
