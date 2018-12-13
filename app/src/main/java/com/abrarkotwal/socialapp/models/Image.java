package com.abrarkotwal.socialapp.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Image {
    @Expose
    private String largeImageURL;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }
}