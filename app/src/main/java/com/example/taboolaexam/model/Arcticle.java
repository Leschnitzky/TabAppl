package com.example.taboolaexam.model;

public class Arcticle {
    private String title;
    private String imageURL;
    private String description;


    public Arcticle(String title, String imageURL, String description) {
        this.title = title;
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
