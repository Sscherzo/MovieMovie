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
import com.example.moviemovie.review.adapter.MovieAdapter;
import com.example.moviemovie.review.model.Movie;
import com.example.moviemovie.review.response.MovieResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener{
    // 객체 선언
    List<Movie> list;
    MovieAdapter adapter;
    SearchView searchView;
    ListView listView;
    AsyncHttpClient client;
    MovieResponse response;
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
        setContentView(R.layout.activity_movie);

        // 객체 초기화
        list = new ArrayList<>();
        adapter = new MovieAdapter(this, R.layout.list_item_movie, list);
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        View footerView = getLayoutInflater().inflate(R.layout.list_footer, null);
        layoutFooter = footerView.findViewById(R.id.layoutFooter);

        client = new AsyncHttpClient();
        response = new MovieResponse(this, adapter, listView, layoutFooter);

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
                    Toast.makeText(MovieActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
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
        getMovieData(keyword);
    }

    private void getMovieData(String keyword) {
        RequestParams params = new RequestParams();
        // API 서비스 인증키
        params.put("ServiceKey", "06E1CQ572154KRS78LD5");
        // 통합검색 출력 결과수
        params.put("listCount", 50);
        // 결과 정렬	- 제목 가나다순
        params.put("sort", "title,0");
        // 영화명 검색
        params.put("title", keyword);

        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
        client.get(url, params, response);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("[TEST]", firstVisibleItem + ", " + visibleItemCount + ", " + totalItemCount);
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie item = adapter.getItem(position);
        Intent intent = new Intent(this, ReviewWriteActivity.class);
        intent.putExtra("item", item);
        setResult(500, intent);
        finish();
    }
}