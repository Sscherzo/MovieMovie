package com.example.moviemovie.review.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.review.model.Search;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<Search> {
    Activity activity;
    int resource;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        Search item = getItem(position);

        if (item != null) {
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView1 = convertView.findViewById(R.id.textView1);
            TextView textView2 = convertView.findViewById(R.id.textView2);

            Glide.with(activity)
                    .load(item.getThumbnail_url())
                    .placeholder(R.drawable.ic_stub)
                    .error(R.drawable.ic_error)
                    .fallback(R.drawable.ic_empty)
                    .into(imageView);
            if (item.getDisplay_sitename().equals("")) {
                textView1.setText("등록된 정보가 없습니다.");
            } else {
                textView1.setText(item.getDisplay_sitename());
            } textView2.setText(item.getWidth() + "x" + item.getHeight());
        }
        return convertView;
    }
}