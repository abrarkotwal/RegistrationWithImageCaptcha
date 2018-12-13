package com.abrarkotwal.socialapp.ui.home.mvp;

import android.app.ProgressDialog;
import android.util.Log;


import com.abrarkotwal.socialapp.utils.UiUtils;
import com.abrarkotwal.socialapp.utils.rx.RxSchedulers;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class HomePresenter {

    private HomeView view;
    private HomeModel model;
    private RxSchedulers rxSchedulers;
    private CompositeSubscription subscriptions;
    private ProgressDialog progressDialog;

    public HomePresenter(RxSchedulers schedulers, HomeModel model, HomeView view, CompositeSubscription sub) {
        this.rxSchedulers = schedulers;
        this.view = view;
        this.model = model;
        this.subscriptions = sub;
        progressDialog = UiUtils.showProgressDialog(model.context);
    }

    public void onCreate() {
        subscriptions.add(getSubCategoryList());
    }

    public void onDestroy() {
        subscriptions.clear();
    }


    private Subscription getSubCategoryList() {
        return model.isNetworkAvailable().doOnNext(networkAvailable -> {
            if (!networkAvailable) {
                progressDialog.dismiss();
            }
        })
                .filter(isNetworkAvailable -> true)
                .flatMap(isAvailable -> model.provideListSubCategory())
                .subscribeOn(rxSchedulers.internet())
                .observeOn(rxSchedulers.androidThread())
                .subscribe(hitsList -> {
                    Log.d("Abrar", String.valueOf(hitsList.getResults().size()));
                    if (hitsList.getResults() != null ) {
                        progressDialog.dismiss();
                        view.swapAdapter(hitsList.getResults());
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    UiUtils.handleThrowable(throwable,model.context);
                });

    }
}
