package com.abrarkotwal.socialapp.application.ApiBuilder;

import com.abrarkotwal.socialapp.models.Hits;
import com.abrarkotwal.socialapp.models.Image;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;


public interface ApiClient {
    @GET("api/?key=10977577-f0b29d91d6b843c7d6b5b7ecc&amp;q=landscapes&amp;image_type=photo&amp;pretty=true")
    Observable<Hits> getImageList();

}