package com.example.moviemovie.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class CheckIDActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText_checkID;
    Button button_checkID, button_back_checkID, button_confirm_checkID;
    String checkID;
    AsyncHttpClient client;
    HttpResponse response;

    // 내 집
    String url ="http://192.168.123.102:8082/moviemovie/member/checkId.do";
    // 집
    // String url = "http://192.168.0.165:8081/moviemovie/member/checkId.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/checkId.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_i_d);

        editText_checkID = findViewById(R.id.editText_checkID);
        button_checkID = findViewById(R.id.button_checkID);
        button_back_checkID = findViewById(R.id.button_back_checkID);
        button_confirm_checkID = findViewById(R.id.button_confirm_checkID);
        checkID = getIntent().getStringExtra("checkID");
        client = new AsyncHttpClient();
        response = new HttpResponse();

        editText_checkID.setText(checkID);

        button_checkID.setOnClickListener(this);
        button_back_checkID.setOnClickListener(this);
        button_confirm_checkID.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_checkID:
                checkID = editText_checkID.getText().toString().trim();
                // 관리자 id 중복 불가
                if(checkID.equals("moviemovie")) {
                    Toast.makeText(CheckIDActivity.this, "사용할 수 없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams params = new RequestParams();
                    params.put("id", checkID);
                    client.post(url, params, response);
                }
                break;
            case R.id.button_back_checkID:
                finish();
                break;
            case R.id.button_confirm_checkID:
                Intent intent = new Intent();
                intent.putExtra("checkID", checkID);
                setResult(RESULT_OK, intent);
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

                if(rt.equals("OK")) {   // 사용할 수 있는 아이디
                    Toast.makeText(CheckIDActivity.this, "사용할 수 있는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckIDActivity.this, "사용할 수 없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(CheckIDActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
            Log.v(String.valueOf(responseBody),"...ResponseBody 확인"+ statusCode);
        }
    }
}