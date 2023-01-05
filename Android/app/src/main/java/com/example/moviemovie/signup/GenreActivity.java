package com.example.moviemovie.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton imageButton_mel, imageButton_fan, imageButton_hor,
                imageButton_mus, imageButton_com, imageButton_kid,
                imageButton_war, imageButton_dra, imageButton_cha,
                imageButton_sf, imageButton_act, imageButton_cri;
    Button button_genreOK, button_genreSkip;
    String id;
    HttpResponse response;
    AsyncHttpClient client;

    //영화 장르
    int     fantasy = 0,
            horror = 0,
            mellow = 0,
            kid = 0,
            drama = 0,
            comedy = 0,
            crime = 0,
            character = 0,
            musical = 0,
            action = 0,
            sf = 0,
            war = 0,
            etc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        imageButton_mel = findViewById(R.id.imageButton_mel);
        imageButton_fan = findViewById(R.id.imageButton_fan);
        imageButton_hor = findViewById(R.id.imageButton_hor);
        imageButton_mus = findViewById(R.id.imageButton_mus);
        imageButton_com = findViewById(R.id.imageButton_com);
        imageButton_kid = findViewById(R.id.imageButton_kid);
        imageButton_war = findViewById(R.id.imageButton_war);
        imageButton_dra = findViewById(R.id.imageButton_dra);
        imageButton_cha = findViewById(R.id.imageButton_cha);
        imageButton_sf = findViewById(R.id.imageButton_sf);
        imageButton_act = findViewById(R.id.imageButton_act);
        imageButton_cri = findViewById(R.id.imageButton_cri);
        button_genreOK = findViewById(R.id.button_genreOK);
        button_genreSkip = findViewById(R.id.button_genreSkip);
        id = getIntent().getStringExtra("id");
        response = new HttpResponse();
        client = new AsyncHttpClient();

        imageButton_mel.setOnClickListener(this);
        imageButton_fan.setOnClickListener(this);
        imageButton_hor.setOnClickListener(this);
        imageButton_mus.setOnClickListener(this);
        imageButton_com.setOnClickListener(this);
        imageButton_kid.setOnClickListener(this);
        imageButton_war.setOnClickListener(this);
        imageButton_dra.setOnClickListener(this);
        imageButton_cha.setOnClickListener(this);
        imageButton_sf.setOnClickListener(this);
        imageButton_act.setOnClickListener(this);
        imageButton_cri.setOnClickListener(this);
        button_genreOK.setOnClickListener(this);
        button_genreSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RequestParams params = null;

        String url ="http://192.168.123.102:8082/moviemovie/genre/genreModify.do";
        // 집
        //String url = "http://192.168.0.165:8081/moviemovie/genre/genreModify.do";
        // 학원
        // String url = "http://192.168.1.69:8081/moviemovie/genre/genreModify.do";
        switch (v.getId()) {
            case R.id.imageButton_mel:
                mellow = 1;
                break;
            case R.id.imageButton_fan:
               fantasy = 1;
               break;
            case R.id.imageButton_hor:
                horror = 1;
                break;
            case R.id.imageButton_mus:
                musical = 1;
                break;
            case R.id.imageButton_com:
                comedy = 1;
                break;
            case R.id.imageButton_kid:
                kid = 1;
                break;
            case R.id.imageButton_war:
                war = 1;
                break;
            case R.id.imageButton_dra:
                drama = 1;
                break;
            case R.id.imageButton_cha:
                character = 1;
                break;
            case R.id.imageButton_sf:
                sf = 1;
                break;
            case R.id.imageButton_act:
                action = 1;
                break;
            case R.id.imageButton_cri:
                crime = 1;
                break;
            case R.id.button_genreOK:
                params = new RequestParams();
                params.put("sf", sf);
                params.put("fantasy",fantasy);
                params.put("horror", horror);
                params.put("drama", drama);
                params.put("mellow", mellow);
                params.put("musical", musical);
                params.put("kid", kid);
                params.put("character",character);
                params.put("action", action);
                params.put("comedy", comedy);
                params.put("war", war);
                params.put("crime",crime);
                params.put("etc", etc);
                params.put("id", id);
                client.get(url, params, response);
                finish();
                break;
            case R.id.button_genreSkip:
                params = new RequestParams();
                params.put("sf", 0);
                params.put("fantasy",0);
                params.put("horror", 0);
                params.put("drama", 0);
                params.put("mellow", 0);
                params.put("musical", 0);
                params.put("kid", 0);
                params.put("character", 0);
                params.put("action", 0);
                params.put("comedy", 0);
                params.put("war", 0);
                params.put("crime", 0);
                params.put("etc", 0);
                params.put("id", id);
                client.get(url, params, response);
                finish();
                break;
        }
    }

    public class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);

            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("OK")) {
                    Toast.makeText(GenreActivity.this,"회원 가입 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GenreActivity.this,"회원 가입 실패", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(GenreActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}