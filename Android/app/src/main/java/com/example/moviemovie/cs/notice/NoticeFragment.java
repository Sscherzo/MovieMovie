package com.example.moviemovie.cs.notice;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class NoticeFragment extends Fragment implements AdapterView.OnItemClickListener {
    List<Notice> list;
    NoticeAdapter adapter;
    ListView listViewNotice;
    AsyncHttpClient client;
    HttpResponse response;
    // 집
    String url = "http://192.168.0.165:8081/moviemovie/admin/notice_list.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/notice_list.do";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        list = new ArrayList<>();
        adapter = new NoticeAdapter(getContext(), R.layout.notice_list_item, list);
        listViewNotice = view.findViewById(R.id.listViewNotice);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        // 리스트 뷰 설정
        listViewNotice.setAdapter(adapter);
        //이벤트 설정
        listViewNotice.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();  // 기존 데이터 삭제
        client.post(url, response);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notice item = adapter.getItem(position);
        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
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
            Toast.makeText(getContext(),"통신 실패.", Toast.LENGTH_SHORT).show();
        }
    }
}