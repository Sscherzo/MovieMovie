package com.example.moviemovie.cs.notice.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.notice.model.Notice;

import java.util.List;

public class NoticeAdapter extends ArrayAdapter<Notice> {
    Activity activity;
    int resource;

    public NoticeAdapter(@NonNull Context context, int resource, @NonNull List<Notice> objects) {
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
        Notice item = getItem(position);

        if(item != null) {
            TextView textViewNoticeS = convertView.findViewById(R.id.textViewNoticeS);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);

            textViewNoticeS.setText(item.getNotice_subject());
            textViewDate.setText(item.getNotice_date());
        }
        return convertView;
    }
}
