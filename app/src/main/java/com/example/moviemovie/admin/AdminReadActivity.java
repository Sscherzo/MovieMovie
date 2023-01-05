package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.notice.model.Notice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AdminReadActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewSubjectA, textViewDateA, textViewContentA;
    Button buttonList, buttonModify, buttonDelete;
    Notice item;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/admin/notice_select.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/notice_select.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_select.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_read);

        textViewSubjectA = findViewById(R.id.textViewSubjectA);
        textViewDateA = findViewById(R.id.textViewDateA);
        textViewContentA = findViewById(R.id.textViewContentA);

        buttonList = findViewById(R.id.buttonList);
        buttonModify = findViewById(R.id.buttonModify);
        buttonDelete = findViewById(R.id.buttonDelete);

        client = new AsyncHttpClient();
        response = new HttpResponse();

        item = (Notice) getIntent().getSerializableExtra("item");

        buttonList.setOnClickListener(this);
        buttonModify.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("seq", item.getSeq());
        client.post(url, params, response);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.buttonList:
                finish();
                break;
            case R.id.buttonModify:
                intent = new Intent(this, AdminModifyActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
                break;
            case R.id.buttonDelete:
                intent = new Intent(this, AdminDeleteActivity.class);
                intent.putExtra("seq", item.getSeq());
                startActivity(intent);
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
                int total = json.getInt("total");
                if(rt.equals("OK")) {
                    if(total > 0) {
                        JSONArray item1 = json.getJSONArray("item");
                        JSONObject temp = item1.getJSONObject(0);
                        item.setSeq(temp.getInt("seq"));
                        item.setNotice_subject(temp.getString("notice_subject"));
                        item.setNotice_content(temp.getString("notice_content"));
                        item.setNotice_date(temp.getString("notice_date"));

                        textViewSubjectA.setText(item.getNotice_subject());
                        textViewDateA.setText(item.getNotice_date());
                        textViewContentA.setText(item.getNotice_content());
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminReadActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}