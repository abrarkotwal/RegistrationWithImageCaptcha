package com.abrarkotwal.socialapp.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.models.Image;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<Image> categoryList = new ArrayList<>();

    public void swapAdapter(List<Image> imageList) {
        categoryList.clear();
        categoryList.addAll(imageList);
        notifyDataSetChanged();
        Log.d("Abrar", String.valueOf(imageList.size()));
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_display, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = categoryList.get(position);
        holder.bind(image);
    }



    @Override
    public int getItemCount() {
        if (categoryList != null && categoryList.size() > 0) {
            return categoryList.size();
        } else {
            return 0;
        }
    }
}
