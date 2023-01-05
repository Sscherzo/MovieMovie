package com.example.moviemovie.review.response;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviemovie.review.adapter.SearchAdapter;
import com.example.moviemovie.review.model.Search;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchResponse extends AsyncHttpResponseHandler {
    Activity activity;
    SearchAdapter adapter;
    ListView listView;          // 끝에 도착했는지 확인용
    View footerView;            // "기다려주세요" footer

    public SearchResponse(Activity activity, SearchAdapter adapter, ListView listView, View footerView) {
        this.activity = activity;
        this.adapter = adapter;
        this.listView = listView;
        this.footerView = footerView;
    }

    @Override
    public void onStart() {
        footerView.setVisibility(View.VISIBLE);     // "기다려주세요" 보이기
        // 리스트뷰의 맨마지막 요소 선택
        // adpater.getCount() : 리스트에 저장된 총 데이터수
        listView.setSelection(adapter.getCount() -1);
        // URI 확인
        Log.d("[TEST]", getRequestURI().toString());
    }

    @Override
    public void onFinish() {
        footerView.setVisibility(View.GONE);        // "기다려주세요" 안보이기
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String str = new String(responseBody);
        try {
            JSONObject json = new JSONObject(str);
            JSONObject meta = json.getJSONObject("meta");
            // 공통변수 저장
            Search.setTotal_count(meta.getInt("total_count"));
            Search.setPageable_count(meta.getInt("pageable_count"));
            Search.setIs_end(meta.getBoolean("is_end"));
            // 검색 결과 저장
            JSONArray documents = json.getJSONArray("documents");
            for (int a=0; a<documents.length(); a++) {
                JSONObject temp = documents.getJSONObject(a);
                Search search = new Search();
                search.setCollection(temp.getString("collection"));
                search.setThumbnail_url(temp.getString("thumbnail_url"));
                search.setImage_url(temp.getString("image_url"));
                search.setWidth(temp.getInt("width"));
                search.setHeight(temp.getInt("height"));
                search.setDisplay_sitename(temp.getString("display_sitename"));
                search.setDoc_url(temp.getString("doc_url"));
                search.setDatetime(temp.getString("datetime"));
                // 리스트에 저장
                adapter.add(search);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity, "통신 실패", Toast.LENGTH_SHORT).show();
    }
}
