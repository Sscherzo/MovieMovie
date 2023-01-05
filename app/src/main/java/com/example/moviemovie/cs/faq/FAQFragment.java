package com.example.moviemovie.cs.faq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.cs.faq.adapter.FaqAdapter;
import com.example.moviemovie.cs.faq.model.Faq;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FAQFragment extends Fragment {
    List<Faq> list;
    FaqAdapter adapter;
    ListView listViewFaq;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/admin/faq_list.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/admin/faq_list.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/admin/faq_list.do";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

        list = new ArrayList<>();
        adapter = new FaqAdapter(getContext(), R.layout.faq_list_item, list);
        listViewFaq = view.findViewById(R.id.listViewFaq);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        // 리스트 뷰 설정
        listViewFaq.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();  // 기존 데이터 삭제
        client.post(url, response);
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
                            Faq faq = new Faq();
                            faq.setFaq_no(temp.getInt("faq_no"));
                            faq.setFaq_subject(temp.getString("faq_subject"));
                            faq.setFaq_content(temp.getString("faq_content"));

                            // 리스트에 저장
                            adapter.add(faq);
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