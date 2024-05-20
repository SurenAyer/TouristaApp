package com.example.touristaapp.models;

public class Photo {
    private String photoId;
    private String photoUrl;
    private TouristAttraction touristAttraction;

    public Photo() {
    }

    public Photo(String photoId, String photoUrl) {
        this.photoId = photoId;
        this.photoUrl = photoUrl;
    }

    public Photo(String photoId, String photoUrl, TouristAttraction touristAttraction) {
        this.photoId = photoId;
        this.photoUrl = photoUrl;
        this.touristAttraction = touristAttraction;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", photoUrl='" + photoUrl + '\'' +
                ", touristAttraction=" + touristAttraction +
                '}';
    }
}
