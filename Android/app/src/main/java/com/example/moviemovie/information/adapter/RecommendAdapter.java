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
import com.example.moviemovie.information.model.Recommend;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    Context context;
    ArrayList<Recommend> list;
    private OnItemClickListener listener;

    public RecommendAdapter(Context context, ArrayList<Recommend> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reco, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView3;
        private TextView textView4;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
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

        public void setItem(Recommend reco) {
            textView1.setText(reco.getTitle());
            textView3.setText(reco.getDirectorNm());
            textView4.setText(reco.getActorNm());
            if(!reco.getPosterUrl().equals("")) {
                Glide.with(context).load(reco.getPosterUrl()).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.moviemovie_poster).into(imageView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recommend reco = list.get(position);
        holder.setItem(reco);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Recommend reco) {
        list.add(reco);
    }
}
