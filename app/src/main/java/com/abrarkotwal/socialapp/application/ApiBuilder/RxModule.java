package com.abrarkotwal.socialapp.application.ApiBuilder;


import com.abrarkotwal.socialapp.utils.rx.AppRxSchedulers;
import com.abrarkotwal.socialapp.utils.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;


@Module
public class RxModule {

    @Provides
    RxSchedulers provideRxSchedulers() {
        return new AppRxSchedulers();
    }
}
