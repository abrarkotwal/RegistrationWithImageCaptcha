package com.abrarkotwal.socialapp.application.ApiBuilder;


import com.abrarkotwal.socialapp.utils.rx.RxSchedulers;

import dagger.Component;

@AppScope
@Component(modules = {NetworkModule.class, AppContextModule.class, RxModule.class, Api.class})
public interface AppComponent {
    RxSchedulers rxSchedulers();
    ApiClient apiService();
}
