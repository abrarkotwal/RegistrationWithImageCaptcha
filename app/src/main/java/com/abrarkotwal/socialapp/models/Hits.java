package com.abrarkotwal.socialapp.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Hits {

    @Expose
    private List<Image> hits;

    public List<Image> getResults() {
        return hits;
    }

    public void setResults(List<Image> hits) {
        this.hits = hits;
    }

}
