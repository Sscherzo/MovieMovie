package com.example.moviemovie.cs.faq.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.faq.model.Faq;

import java.util.List;

public class FaqAdapter extends ArrayAdapter<Faq> {
    Activity activity;
    int resource;

    public FaqAdapter(@NonNull Context context, int resource, @NonNull List<Faq> objects) {
        super(context, resource, objects);
        activity = (Activity)context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        Faq item = getItem(position);

        if(item != null) {
            TextView textViewSubject = convertView.findViewById(R.id.textViewSubject);
            TextView textViewContent = convertView.findViewById(R.id.textViewContent);

            textViewSubject.setText(item.getFaq_subject());
            textViewContent.setText(item.getFaq_content());
        }
        return convertView;
    }
}
