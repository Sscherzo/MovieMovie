package com.example.moviemovie.cs.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class NoticeDetailActivity extends AppCompatActivity {
    TextView textViewSubjectN, textViewDateN, textViewContentN;
    Notice item;
    AsyncHttpClient client;
    HttpResponse response;
    // 집
    String url = "http://192.168.0.165:8081/moviemovie/admin/notice_select.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_select.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        textViewSubjectN = findViewById(R.id.textViewSubjectN);
        textViewDateN = findViewById(R.id.textViewDateN);
        textViewContentN = findViewById(R.id.textViewContentN);
        client = new AsyncHttpClient();
        response = new HttpResponse();

        item = (Notice) getIntent().getSerializableExtra("item");
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("seq", item.getSeq());
        client.post(url, params, response);
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

                        textViewSubjectN.setText(item.getNotice_subject());
                        textViewDateN.setText(item.getNotice_date());
                        textViewContentN.setText(item.getNotice_content());
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(NoticeDetailActivity.this,"통신 실패.", Toast.LENGTH_SHORT).show();
        }
    }
}