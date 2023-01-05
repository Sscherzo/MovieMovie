package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moviemovie.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAdminN, buttonAdminM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        buttonAdminN = findViewById(R.id.buttonAdminN);
        buttonAdminM = findViewById(R.id.buttonAdminM);

        buttonAdminN.setOnClickListener(this);
        buttonAdminM.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.buttonAdminN:
                intent = new Intent(this, AdminNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonAdminM:
                intent = new Intent(this, AdminMemberActivity.class);
                startActivity(intent);
                break;
        }
    }
}