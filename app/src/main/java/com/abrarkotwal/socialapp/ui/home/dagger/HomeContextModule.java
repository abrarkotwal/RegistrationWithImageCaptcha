package com.abrarkotwal.socialapp.ui.home.dagger;


import com.abrarkotwal.socialapp.ui.home.HomeActivity;
import com.abrarkotwal.socialapp.application.ApiBuilder.ApiClient;
import com.abrarkotwal.socialapp.ui.home.mvp.HomeModel;
import com.abrarkotwal.socialapp.ui.home.mvp.HomePresenter;
import com.abrarkotwal.socialapp.ui.home.mvp.HomeView;
import com.abrarkotwal.socialapp.utils.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class HomeContextModule {

    HomeActivity homeActivity;

    public HomeContextModule(HomeActivity context) {
        this.homeActivity = context;
    }



    @HomeScope
    @Provides
    HomeView provideView() {
        return new HomeView(homeActivity);
    }

    @HomeScope
    @Provides
    HomePresenter providePresenter(RxSchedulers schedulers, HomeView view, HomeModel model) {
        CompositeSubscription subscriptions = new CompositeSubscription();
        return new HomePresenter(schedulers, model, view, subscriptions);
    }



    @HomeScope
    @Provides
    HomeActivity provideContext() {
        return homeActivity;
    }

    @HomeScope
    @Provides
    HomeModel provideModel(ApiClient api) {
        return new HomeModel(homeActivity, api);
    }
}
