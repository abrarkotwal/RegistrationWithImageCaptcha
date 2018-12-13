package com.abrarkotwal.socialapp.ui.home.mvp;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.abrarkotwal.socialapp.ui.home.HomeActivity;
import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.ui.home.adapter.ImageAdapter;
import com.abrarkotwal.socialapp.models.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class HomeView {

    @BindView(R.id.recyclerview)
    RecyclerView subcategoryRecyclerview;
    @BindView(R.id.notFound)
    ImageView notFound;

    private View view;
    public ImageAdapter adapter;

    public HomeView(HomeActivity context) {
        FrameLayout parent = new FrameLayout(context);
        parent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view = LayoutInflater.from(context).inflate(R.layout.activity_home, parent, true);
        ButterKnife.bind(this, view);

        adapter = new ImageAdapter();

        subcategoryRecyclerview.setAdapter(adapter);
        subcategoryRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        subcategoryRecyclerview.setHasFixedSize(true);
        subcategoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

    }


    public View view() {
        return view;
    }

    public void swapAdapter(List<Image> imageList) {
        Log.d("Abrar", String.valueOf(imageList.size()));
        if (imageList.size() != 0) {
            //Passing 20 image at time
            adapter.swapAdapter(imageList);
        }
        else{
            subcategoryRecyclerview.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }

}
