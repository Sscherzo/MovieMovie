package com.example.moviemovie.find;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FindPWActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView_find_pw;
    Button button_find_pw_ok;
    String name, id, tel;
    AsyncHttpClient client;
    HttpResponse response;
    String str1 = "님의 비밀번호는 ";
    String str2 = " 입니다";

    String url ="http://192.168.123.102:8082/moviemovie/member/find_pw.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/member/find_pw.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/find_pw.do";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_p_w);

        textView_find_pw = findViewById(R.id.textView_find_pw);
        button_find_pw_ok = findViewById(R.id.button_find_pw_ok);
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        tel = getIntent().getStringExtra("tel");
        client = new AsyncHttpClient();
        response = new HttpResponse();

        textView_find_pw.setText("비밀번호를 찾을 수 없습니다");

        button_find_pw_ok.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("id", id);
        params.put("tel", tel);
        client.post(url, params, response);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);

            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                String pw = json.getString("pw");
                if(rt.equals("OK")) {
                    textView_find_pw.setText("");
                    textView_find_pw.setText(name + str1 + pw + str2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FindPWActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}