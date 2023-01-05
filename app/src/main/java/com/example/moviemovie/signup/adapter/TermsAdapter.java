package com.example.moviemovie.signup.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.moviemovie.R;
import com.example.moviemovie.signup.model.Terms;

import java.util.List;

public class TermsAdapter extends ArrayAdapter<Terms> {

    Activity activity;
    int resource;

    public TermsAdapter(@NonNull Context context, int resource, @NonNull List<Terms> objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        Terms item = getItem(position);

        if(item != null) {
            TextView textView = convertView.findViewById(R.id.textViewTerms);
            textView.setText(item.getText());

        }


        return convertView;
    }
}
