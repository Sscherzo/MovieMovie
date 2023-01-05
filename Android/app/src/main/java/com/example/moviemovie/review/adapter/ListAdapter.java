package com.example.moviemovie.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.review.model.Review;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<Review> list;

    public ListAdapter(Context context, int resource, ArrayList<Review> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_title;
        private ImageView imageView_movie;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.textView_title);
            imageView_movie = itemView.findViewById(R.id.imageView_movie);
        }

        public void setItem(Review review) {
            textView_title.setText(review.getTitle());
            if (review.getFilename() != null) {
                Glide.with(context).load(review.getFilename()).into(imageView_movie);
            } else {
                Glide.with(context).load(review.getImageUrl()).into(imageView_movie);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = list.get(position);
        holder.setItem(review);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Review review) {
        list.add(review);
    }

    public void clear() {
        this.list.clear();
    }
}
