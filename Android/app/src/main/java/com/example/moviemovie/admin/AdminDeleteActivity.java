package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AdminDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewDelete;
    Button buttonDelete, buttonBack;
    AsyncHttpClient client;
    HttpResponse response;
    int seq;

    String url ="http://192.168.123.102:8082/moviemovie/admin/notice_delete.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/notice_delete.do";
    // 학원
   // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_delete.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete);

        textViewDelete = findViewById(R.id.textViewDelete);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBack = findViewById(R.id.buttonBack);
        client = new AsyncHttpClient();
        response = new HttpResponse();

        seq = getIntent().getIntExtra("seq",0);
        // 이벤트 설정
        buttonDelete.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        // 화면 설정
        textViewDelete.setText(seq + " 번 공지를 삭제하시겠습니까?");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDelete:  // 삭제하기
                RequestParams params = new RequestParams();
                params.put("seq", seq);
                client.post(url, params, response);
                finish();
                break;
            case R.id.buttonBack:
                finish();
                break;
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("OK")) {
                    Toast.makeText(AdminDeleteActivity.this,"삭제 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminDeleteActivity.this, AdminNoticeActivity.class);
                    // 새로 만들지 말고 기존 것을 보여 달라는 명령
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();  // 현재 액티비티 종료
                } else {
                    Toast.makeText(AdminDeleteActivity.this,"삭제 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminDeleteActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}