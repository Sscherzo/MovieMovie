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
import com.example.moviemovie.review.model.Movie;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    Activity activity;
    int resource;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List objects) {
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

        Movie item = getItem(position);

        if (item != null) {
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView_title = convertView.findViewById(R.id.textView_title);
            TextView textView_genre = convertView.findViewById(R.id.textView_genre);
            TextView textView_date = convertView.findViewById(R.id.textView_date);
            TextView textView_director = convertView.findViewById(R.id.textView_director);
            TextView textView_actor = convertView.findViewById(R.id.textView_actor);

            if(!item.getPosterUrl().equals("")) {
                Glide.with(activity).load(item.getPosterUrl()).into(imageView);
            } else {
                Glide.with(activity).load(R.drawable.moviemovie_poster).into(imageView);
            }

            textView_title.setText(item.getTitle());
                textView_genre.setText("장르 : " + item.getGenre());
            textView_date.setText("개봉일 : " + item.getReleaseDate());
            textView_director.setText("감독 : " + item.getDirectorNm());
            textView_actor.setText("배우 : " + item.getActorNm());
        }
        return convertView;
    }
}
