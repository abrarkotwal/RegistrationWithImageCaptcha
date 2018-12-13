package com.abrarkotwal.socialapp.ui.home.dagger;



import com.abrarkotwal.socialapp.ui.home.HomeActivity;
import com.abrarkotwal.socialapp.application.ApiBuilder.AppComponent;

import dagger.Component;

@HomeScope
@Component(dependencies = {AppComponent.class} , modules = {HomeContextModule.class})
public interface HomeComponent {

    void inject(HomeActivity homeActivity);
}
