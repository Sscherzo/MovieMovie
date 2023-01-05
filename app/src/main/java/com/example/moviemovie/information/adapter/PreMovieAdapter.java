package com.example.moviemovie.information.adapter;

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
import com.example.moviemovie.information.model.PreMovie;

import java.util.ArrayList;

public class PreMovieAdapter extends RecyclerView.Adapter<PreMovieAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    Context context;
    ArrayList<PreMovie> list;
    private OnItemClickListener listener;

    public PreMovieAdapter(Context context, ArrayList<PreMovie> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pre, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, pos);
                    }
                    return;
                }
            });
        }

        public void setItem(PreMovie pre) {
            textView1.setText(pre.getTitle());
            textView2.setText(pre.getReleaseDate());
            textView3.setText(pre.getDirectorNm());
            textView4.setText(pre.getActorNm());
            if(!pre.getPosterUrl().equals("")) {
                Glide.with(context).load(pre.getPosterUrl()).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.moviemovie_poster).into(imageView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreMovie pre = list.get(position);
        holder.setItem(pre);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(PreMovie pre) {
        list.add(pre);
    }
}
