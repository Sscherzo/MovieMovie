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
import com.example.moviemovie.information.model.Box;

import java.util.ArrayList;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    Context context;
    ArrayList<Box> list;
    private OnItemClickListener listener;

    public BoxAdapter(Context context, ArrayList<Box> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_box, parent, false);
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

        public void setItem(Box box) {
            textView1.setText(box.getRank());
            textView2.setText(box.getMovieNm());
            textView3.setText(box.getOpenDt());
            textView4.setText(box.getAudiAcc());
            if(!box.getImg().equals("")) {
                Glide.with(context).load(box.getImg()).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.moviemovie_poster).into(imageView);
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Box box = list.get(position);
        holder.setItem(box);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Box box) {
        list.add(box);
    }
}
