package com.example.moviemovie.review;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.calendar.CalendarActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class ReviewDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Button button_ok, button_cancle;
    AsyncHttpClient client;
    HttpResponse response;
    int seq;
    String title;
    String id = pref.getString("id",null);

    String url ="http://192.168.123.102:8082/moviemovie/review/review_delete.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_delete.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_delete.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_delete);

        textView = findViewById(R.id.textView);
        button_ok = findViewById(R.id.button_ok);
        button_cancle = findViewById(R.id.button_cancle);
        client = new AsyncHttpClient();
        response = new HttpResponse();

        seq = getIntent().getIntExtra("seq", 0);
        title = getIntent().getStringExtra("title");

        textView.setText("'" + title + "' 리뷰를 삭제하시겠습니까?" );

        button_ok.setOnClickListener(this);
        button_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:  // 삭제하기
                // 서버에 요청
                RequestParams params = new RequestParams();
                params.put("seq", seq);
                params.put("id", id);
                client.post(url, params, response);
                break;
            case R.id.button_cancle:  // 취소하기
                finish();
                break;
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        ProgressDialog dialog;

        @Override
        public void onStart() {
            dialog = new ProgressDialog(ReviewDeleteActivity.this);
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
            dialog = null;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("OK")) {
                    Toast.makeText(ReviewDeleteActivity.this, "삭제 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReviewDeleteActivity.this, CalendarActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();   // 현재 액티비티 종료
                } else {
                    Toast.makeText(ReviewDeleteActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewDeleteActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}