package com.example.luis.tassel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by luis on 08/07/17.
 */

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.commentViewHolder> {
    private List<comment> items;
    private static Context context;

    public static class commentViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView name, commentVal;
        public ImageView iV,iV2,iV3,iV4,iV5;

        public commentViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.textView17);
            commentVal = (TextView) v.findViewById(R.id.textView18);
            iV = (ImageView) v.findViewById(R.id.imageView8);
            iV2 = (ImageView) v.findViewById(R.id.imageView9);
            iV3 = (ImageView) v.findViewById(R.id.imageView10);
            iV4 = (ImageView) v.findViewById(R.id.imageView11);
            iV5 = (ImageView) v.findViewById(R.id.imageView12);
        }
    }

        public commentAdapter(List<comment> items) {
            this.items = items;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public commentAdapter.commentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.comment_item, viewGroup, false);
            return new commentAdapter.commentViewHolder(v);
        }

        @Override
        public void onBindViewHolder(commentAdapter.commentViewHolder viewHolder, int i) {
            viewHolder.name.setText(items.get(i).getName());
            viewHolder.commentVal.setText(items.get(i).getComment());
            switch (items.get(i).getStars()){
                case 0:
                    viewHolder.iV.setImageResource(R.drawable.star1);
                    viewHolder.iV2.setImageResource(R.drawable.star1);
                    viewHolder.iV3.setImageResource(R.drawable.star1);
                    viewHolder.iV4.setImageResource(R.drawable.star1);
                    viewHolder.iV5.setImageResource(R.drawable.star1);
                    break;
                case 1:
                    viewHolder.iV.setImageResource(R.drawable.star2);
                    viewHolder.iV2.setImageResource(R.drawable.star1);
                    viewHolder.iV3.setImageResource(R.drawable.star1);
                    viewHolder.iV4.setImageResource(R.drawable.star1);
                    viewHolder.iV5.setImageResource(R.drawable.star1);
                    break;
                case 2:
                    viewHolder.iV.setImageResource(R.drawable.star2);
                    viewHolder.iV2.setImageResource(R.drawable.star2);
                    viewHolder.iV3.setImageResource(R.drawable.star1);
                    viewHolder.iV4.setImageResource(R.drawable.star1);
                    viewHolder.iV5.setImageResource(R.drawable.star1);
                    break;
                case 3:
                    viewHolder.iV.setImageResource(R.drawable.star2);
                    viewHolder.iV2.setImageResource(R.drawable.star2);
                    viewHolder.iV3.setImageResource(R.drawable.star2);
                    viewHolder.iV4.setImageResource(R.drawable.star1);
                    viewHolder.iV5.setImageResource(R.drawable.star1);
                    break;
                case 4:
                    viewHolder.iV.setImageResource(R.drawable.star2);
                    viewHolder.iV2.setImageResource(R.drawable.star2);
                    viewHolder.iV3.setImageResource(R.drawable.star2);
                    viewHolder.iV4.setImageResource(R.drawable.star2);
                    viewHolder.iV5.setImageResource(R.drawable.star1);
                    break;
                case 5:
                    viewHolder.iV.setImageResource(R.drawable.star2);
                    viewHolder.iV2.setImageResource(R.drawable.star2);
                    viewHolder.iV3.setImageResource(R.drawable.star2);
                    viewHolder.iV4.setImageResource(R.drawable.star2);
                    viewHolder.iV5.setImageResource(R.drawable.star2);
                    break;
            }
        }
}
