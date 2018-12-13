package com.abrarkotwal.socialapp.ui.home.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.models.Image;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageViewHolder extends ViewHolder {

    private View view;

    @BindView(R.id.imageDisplay)
    ImageView imageDisplay;
    @BindView(R.id.imageProgress)
    ProgressBar imageProgress;


    public ImageViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, view);
    }

    @SuppressLint("SetTextI18n")
    void bind(Image image) {
        Glide.with(view.getContext())
                .load(image.getLargeImageURL())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageProgress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageProgress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade()
                .into(imageDisplay);
    }

}
