package com.example.touristaapp.models;

public class Review {
    private String reviewId;
    private float rating;
    private String comment;
    private Long timestamp;
    private String userName;
    private TouristAttraction touristAttraction;
    private String userId;

    public Review() {
    }

    public Review(String reviewId, float rating, String comment, Long timestamp, String userName, String userId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.userName = userName;
        this.userId = userId;
    }

    public Review(String reviewId, float rating, String comment, Long timestamp, String userName, TouristAttraction touristAttraction, String userId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
        this.userName = userName;
        this.touristAttraction = touristAttraction;
        this.userId = userId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                ", userId=" + userId +
                '}';
    }
}
