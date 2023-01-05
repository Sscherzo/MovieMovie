package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AdminWriteActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextSubject, editTextContent;
    Button buttonWrite, buttonBack;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/admin/notice_write.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/notice_write.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_write.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_write);

        editTextSubject = findViewById(R.id.editTextSubject);
        editTextContent = findViewById(R.id.editTextContent);
        buttonWrite = findViewById(R.id.buttonWrite);
        buttonBack = findViewById(R.id.buttonBack);

        client = new AsyncHttpClient();
        response = new HttpResponse();
        // 이벤트 설정
        buttonWrite.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.buttonWrite:
                String notice_subject = editTextSubject.getText().toString().trim();
                String notice_content = editTextContent.getText().toString().trim();
                // 입력값 검사
                String msg = null;
                if(msg == null && notice_subject.equals("")) {
                    msg = "제목을 입력해 주세요.";
                } else if(msg == null && notice_content.equals("")) {
                    msg = "내용을 입력해 주세요.";
                }
                // 입력값 없는 항목이 있을 경우
                if(msg != null) {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                // 모든 값이 있으면
                RequestParams params = new RequestParams();
                params.put("notice_subject", notice_subject);
                params.put("notice_content", notice_content);

                client.post(url, params, response);
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
                    Toast.makeText(AdminWriteActivity.this,"저장 성공", Toast.LENGTH_SHORT).show();
                    finish();  // 이전 화면으로 돌아가기
                } else {
                    Toast.makeText(AdminWriteActivity.this,"저장 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminWriteActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}