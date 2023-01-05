package com.example.moviemovie.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.notice.adapter.NoticeAdapter;
import com.example.moviemovie.cs.notice.model.Notice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AdminNoticeActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<Notice> list;
    NoticeAdapter adapter;
    ListView listViewAdmin;
    Button buttonWriteN, buttonBackN;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/admin/notice_list.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/notice_list.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_list.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice);

        list = new ArrayList<>();
        adapter = new NoticeAdapter(this, R.layout.notice_list_item, list);
        listViewAdmin = findViewById(R.id.listViewAdmin);
        buttonWriteN = findViewById(R.id.buttonWriteN);
        buttonBackN = findViewById(R.id.buttonBackN);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        // 리스트 뷰 설정
        listViewAdmin.setAdapter(adapter);
        //이벤트 설정
        buttonWriteN.setOnClickListener(this);
        buttonBackN.setOnClickListener(this);
        listViewAdmin.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();  // 기존 데이터 삭제
        client.post(url, response);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonWriteN:
                Intent intent = new Intent(this, AdminWriteActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonBackN:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notice item = adapter.getItem(position);
        Intent intent = new Intent(this, AdminReadActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                int total = json.getInt("total");
                JSONArray item = json.getJSONArray("item");

                if(rt.equals("OK")) {
                    if (total > 0) {
                        for (int i = 0; i < item.length(); i++) {
                            JSONObject temp = item.getJSONObject(i);
                            Notice notice = new Notice();
                            notice.setSeq(temp.getInt("seq"));
                            notice.setNotice_subject(temp.getString("notice_subject"));
                            notice.setNotice_content(temp.getString("notice_content"));
                            notice.setNotice_date(temp.getString("notice_date"));

                            // 리스트에 저장
                            adapter.add(notice);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminNoticeActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}