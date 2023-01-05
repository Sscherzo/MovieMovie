package com.example.moviemovie.review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.review.adapter.SearchAdapter;
import com.example.moviemovie.review.model.Search;
import com.example.moviemovie.review.response.SearchResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    List<Search> list;
    SearchAdapter adapter;
    SearchView searchView;
    ListView listView;
    AsyncHttpClient client;
    SearchResponse response;
    LinearLayout layoutFooter;  // footer

    /* 페이징 처리 */
    // 한 페이지에 보여질 목록 수
    public static final int PAGE_SIZE = 20;
    // 현재 페이지
    public static int PAGE = 1;
    // 화면에 리스트의 마지막 아이템이 보여지는지 체크
    boolean lastItemVisibleFlag = false;
    // 검색어 저장
    String keyword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        list = new ArrayList<>();
        adapter = new SearchAdapter(this, R.layout.list_item_search, list);
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        View footerView = getLayoutInflater().inflate(R.layout.list_footer, null);
        layoutFooter = footerView.findViewById(R.id.layoutFooter);

        client = new AsyncHttpClient();
        response = new SearchResponse(this, adapter, listView, layoutFooter);

        // liseView 설정
        listView.addFooterView(footerView);
        listView.setAdapter(adapter);
        footerView.setVisibility(View.GONE);
        // 이벤트 설정
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("")) {
                    Toast.makeText(SearchActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")) {
                    this.onQueryTextSubmit("");
                }
                return false;
            }
        });
    }

    private void search(String query) {
        keyword = query;
        // 버튼 클릭을 통한 검색은 신규 검색이므로, 페이징처리 변수 초기화
        PAGE = 1;
        adapter.clear();        // 리스트의 데이터 모두 삭제
        // 검색 요청
        getKakaoData(keyword);
    }

    private void getKakaoData(String keyword) {
        // 파라미터 저장 객체
        RequestParams params = new RequestParams();
        params.put("query", keyword);
        params.put("size", String.valueOf(PAGE_SIZE));
        params.put("page", String.valueOf(PAGE));
        // 헤더 파일에 api 키 추가
        client.addHeader("Authorization", "KakaoAK 8ea99fb82bea1f2210a31ed80bca65c9");
        // 요청
        String url = "https://dapi.kakao.com/v2/search/image";
        client.get(url, params, response);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 스크롤이 바닥에 닿아 멈춘 상태일 때의 처리
        if (scrollState == SCROLL_STATE_IDLE && lastItemVisibleFlag) {
            PAGE ++;    // 페이지 1 증가 -> 다음 페이지
            getKakaoData(keyword);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("[TEST]", firstVisibleItem + ", " + visibleItemCount + ", " + totalItemCount);
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Search item = adapter.getItem(position);
        Intent intent = new Intent(this, ReviewWriteActivity.class);
        intent.putExtra("item", item);
        setResult(400, intent);
        finish();
    }
}