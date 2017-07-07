package com.example.luis.tassel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by luis on 06/07/17.
 */

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.imageViewHolder> {
    private List<image> items;
    private static Context context;

    public static class imageViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public NetworkImageView nIV;

        public imageViewHolder(View v) {
            super(v);
            nIV = (NetworkImageView) v.findViewById(R.id.photo3);
            context = v.getContext();
            nIV.setDefaultImageResId(R.drawable.loading);
            nIV.setErrorImageResId(R.drawable.error);
        }
    }

    public imageAdapter(List<image> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public imageAdapter.imageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false);
        return new imageAdapter.imageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(imageAdapter.imageViewHolder viewHolder, int i) {
        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
        viewHolder.nIV.setImageUrl(utilities.serverAddress + items.get(i).getPath(), imageLoader);
    }
}