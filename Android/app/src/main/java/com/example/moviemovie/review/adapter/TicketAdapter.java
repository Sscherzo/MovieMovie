package com.example.moviemovie.review.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.review.TicketActivity;
import com.example.moviemovie.review.model.Review;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int pos);
    }

    Context context;
    ArrayList<Review> list;
    private OnItemLongClickListener longListener;

    public TicketAdapter(Context context, ArrayList<Review> list, TicketActivity ticketActivity) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ticket, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_title;
        private TextView textView_review;
        private ImageView imageView_movie;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.textView_title);
            textView_review = itemView.findViewById(R.id.textView_review);
            imageView_movie = itemView.findViewById(R.id.imageView_movie);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        longListener.onItemLongClick(v, pos);
                    }
                    return true;
                }
            });
        }

        public void setItem(Review review) {
            textView_title.setText(review.getTitle());
            textView_review.setText(review.getReview());
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
    
    public Review getItem(int position) {
        return list.get(position);
    }

    public void clear() {
        this.list.clear();
    }
}
