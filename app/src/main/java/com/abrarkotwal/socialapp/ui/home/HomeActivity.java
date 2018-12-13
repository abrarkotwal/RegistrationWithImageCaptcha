package com.abrarkotwal.socialapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.application.AppController;
import com.abrarkotwal.socialapp.ui.LoginActivity;
import com.abrarkotwal.socialapp.ui.home.mvp.HomePresenter;
import com.abrarkotwal.socialapp.ui.home.mvp.HomeView;
import com.abrarkotwal.socialapp.ui.home.dagger.DaggerHomeComponent;
import com.abrarkotwal.socialapp.ui.home.dagger.HomeContextModule;
import com.abrarkotwal.socialapp.utils.LoginSessionManager;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    HomeView view;
    @Inject
    HomePresenter presenter;
    private boolean doubleBackToExitPressedOnce = false;
    private LoginSessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager =new LoginSessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            finish();
            sessionManager.checkUserLogin();
        }

        DaggerHomeComponent
                .builder()
                .appComponent(AppController.getAppComponent())
                .homeContextModule(new HomeContextModule(this))
                .build()
                .inject(this);
        setContentView(view.view());
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.userLogout();
                finish();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}