package com.example.moviemovie.find;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class FindIDActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView_find_id;
    Button button_find_id_ok;
    String name, tel;
    AsyncHttpClient client;
    HttpResponse response;
    String str1 = "님의 아이디는 ";
    String str2 = " 입니다";

    String url ="http://192.168.123.102:8082/moviemovie/member/find_id.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/member/find_id.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/find_id.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_i_d);

        textView_find_id = findViewById(R.id.textView_find_id);
        button_find_id_ok = findViewById(R.id.button_find_id_ok);
        name = getIntent().getStringExtra("name");
        tel = getIntent().getStringExtra("tel");
        client = new AsyncHttpClient();
        response = new HttpResponse();

        textView_find_id.setText("아이디가 없습니다");

        button_find_id_ok.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("name", name);
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
                String id = json.getString("id");
                if(rt.equals("OK")) {
//                    Log.d("[TEST]", "id = " + id);
                    textView_find_id.setText("");
                    textView_find_id.setText(name + str1 + id + str2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FindIDActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}