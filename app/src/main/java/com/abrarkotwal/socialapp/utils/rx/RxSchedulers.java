package com.abrarkotwal.socialapp.utils.rx;

import rx.Scheduler;

public interface RxSchedulers {


    Scheduler runOnBackground();

    Scheduler io();

    Scheduler compute();

    Scheduler androidThread();

    Scheduler internet();



}
