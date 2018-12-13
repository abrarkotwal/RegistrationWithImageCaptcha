package com.abrarkotwal.socialapp.ui.home.mvp;


import com.abrarkotwal.socialapp.models.Hits;
import com.abrarkotwal.socialapp.ui.home.HomeActivity;
import com.abrarkotwal.socialapp.application.ApiBuilder.ApiClient;
import com.abrarkotwal.socialapp.utils.NetworkUtils;

import java.util.List;

import rx.Observable;

public class HomeModel {

    HomeActivity context;
    private ApiClient api;

    public HomeModel(HomeActivity context, ApiClient api) {
        this.api = api;
        this.context = context;
    }


    Observable<Boolean> isNetworkAvailable() {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }

    Observable<Hits> provideListSubCategory() {
        return api.getImageList();
    }

}
