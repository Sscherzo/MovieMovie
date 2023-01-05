package com.example.moviemovie.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.example.moviemovie.SettingActivity;
import com.example.moviemovie.calendar.CalendarActivity;
import com.example.moviemovie.cs.faq.FAQFragment;
import com.example.moviemovie.cs.notice.NoticeFragment;
import com.example.moviemovie.information.FilmActivity;

import static com.example.moviemovie.LoginActivity.pref;

public class CSActivity extends AppCompatActivity implements View.OnClickListener {
    NoticeFragment noticeFragment;
    FAQFragment faqFragment;
    Button button_cal, button_info, button_home, button_my, button_serv;
    String id = pref.getString("id",null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_s);

        noticeFragment = new NoticeFragment();
        faqFragment = new FAQFragment();
        button_cal = findViewById(R.id.button_cal);
        button_info = findViewById(R.id.button_info);
        button_home = findViewById(R.id.button_home);
        button_my = findViewById(R.id.button_my);
        button_serv = findViewById(R.id.button_serv);

        button_cal.setOnClickListener(this);
        button_info.setOnClickListener(this);
        button_home.setOnClickListener(this);
        button_my.setOnClickListener(this);
        button_serv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_cal:       // 캘린더
                intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.button_info:      // 영화정보
                intent = new Intent(this, FilmActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.button_home:      // 홈
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button_my:        // 마이페이지
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_serv:      // 고객센터
//                intent = new Intent(this, CSActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "현재 위치입니다", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}