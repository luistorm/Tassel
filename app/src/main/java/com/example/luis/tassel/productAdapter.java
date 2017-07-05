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
 * Created by luis on 03/07/17.
 */

public class productAdapter extends RecyclerView.Adapter<productAdapter.productViewHolder> {
    private List<product> items;
    private static Context context;

    public static class productViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public NetworkImageView nIV;
        public TextView name;
        public TextView price;
        public TextView ingredients;
        public TextView description;

        public productViewHolder(View v) {
            super(v);
            nIV = (NetworkImageView) v.findViewById(R.id.photo);
            name = (TextView) v.findViewById(R.id.textView5);
            price = (TextView) v.findViewById(R.id.textView7);
            ingredients = (TextView) v.findViewById(R.id.textView9);
            description = (TextView) v.findViewById(R.id.textView10);
            context = v.getContext();
            nIV.setDefaultImageResId(R.drawable.loading);
            nIV.setErrorImageResId(R.drawable.error);
        }
    }

    public productAdapter(List<product> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public productViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_home_item, viewGroup, false);
        return new productViewHolder(v);
    }

    @Override
    public void onBindViewHolder(productViewHolder viewHolder, int i) {
        ImageLoader.ImageCache imageCache = new BitmapLruCache();
        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
        viewHolder.nIV.setImageUrl(utilities.serverAddress+items.get(i).getImagePath(),imageLoader);
        viewHolder.name.setText(items.get(i).getName());
        viewHolder.price.setText(Float.toString(items.get(i).getPrice()));
        viewHolder.ingredients.setText(items.get(i).getIngredients());
        viewHolder.description.setText("\""+items.get(i).getDescription()+"\"");
    }
}
