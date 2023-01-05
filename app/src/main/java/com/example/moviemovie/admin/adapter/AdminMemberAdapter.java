package com.example.moviemovie.admin.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.admin.model.AdminMember;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminMemberAdapter extends ArrayAdapter<AdminMember> {
    Activity activity;
    int resource;

    public AdminMemberAdapter(@NonNull Context context, int resource, @NonNull List<AdminMember> objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this. resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        AdminMember item = getItem(position);

        if(item !=null) {
            CircleImageView image_adMEM = convertView.findViewById(R.id.image_adMEM);
            TextView textViewadID = convertView.findViewById(R.id.textViewadID);
            TextView textViewadPW = convertView.findViewById(R.id.textViewadPW);
            TextView textViewadNICK = convertView.findViewById(R.id.textViewadNICK);
            TextView textViewadNAME = convertView.findViewById(R.id.textViewadNAME);
            TextView textViewadEMAIL = convertView.findViewById(R.id.textViewadEMAIL);
            TextView textViewadPHONE = convertView.findViewById(R.id.textViewadPHONE);


            textViewadID.setText(item.getId());
            textViewadPW.setText(item.getPw());
            textViewadNICK.setText(item.getNickname());
            textViewadNAME.setText(item.getName());
            textViewadEMAIL.setText(item.getEmail());
            textViewadPHONE.setText(item.getTel());
            if(item.getImg().equals("X")) {
                Glide.with(activity).load(R.drawable.moviemovie_profile).into(image_adMEM);
            } else {
                Glide.with(activity).load(item.getImg()).into(image_adMEM);
            }
        }
        return convertView;
    }
}
