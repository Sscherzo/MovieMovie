package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.notice.model.Notice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AdminModifyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextSubjectM, editTextContentM;
    Button buttonModify, buttonBack;
    AsyncHttpClient client;
    HttpResponse response;
    Notice item;

    String url ="http://192.168.123.102:8082/moviemovie/admin/notice_modify.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/notice_modify.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_modify.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify);

        editTextSubjectM = findViewById(R.id.editTextSubjectM);
        editTextContentM = findViewById(R.id.editTextContentM);
        buttonModify = findViewById(R.id.buttonModify);
        buttonBack = findViewById(R.id.buttonBack);

        client = new AsyncHttpClient();
        response = new HttpResponse();

        buttonModify.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        // 화면 설정
        item = (Notice) getIntent().getSerializableExtra("item");
        editTextSubjectM.setText(item.getNotice_subject());
        editTextContentM.setText(item.getNotice_content());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonModify:  // 수정하기
                String notice_subject = editTextSubjectM.getText().toString().trim();
                String notice_content = editTextContentM.getText().toString().trim();
                // 입력값 검사
                String msg = null;
                if (msg == null && notice_subject.equals("")) {
                    msg = "제목을 입력해 주세요";
                } else if (msg == null && notice_content.equals("")) {
                    msg = "내용을 입력해 주세요";
                }
                // 입력값 없는 항목이 있을 경우
                if (msg != null) {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestParams params = new RequestParams();
                params.put("seq", item.getSeq());
                params.put("notice_subject", notice_subject);
                params.put("notice_content", notice_content);

                client.post(url, params, response);
                break;
            case R.id.buttonBack:  // 취소하기
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
                    Toast.makeText(AdminModifyActivity.this,"수정 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AdminModifyActivity.this,"수정 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminModifyActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}