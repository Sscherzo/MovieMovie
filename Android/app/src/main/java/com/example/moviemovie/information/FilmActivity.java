package com.example.moviemovie.information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.example.moviemovie.SettingActivity;
import com.example.moviemovie.calendar.CalendarActivity;
import com.example.moviemovie.cs.CSActivity;
import com.example.moviemovie.information.adapter.BoxAdapter;
import com.example.moviemovie.information.adapter.PreMovieAdapter;
import com.example.moviemovie.information.adapter.RecommendAdapter;
import com.example.moviemovie.information.model.Box;
import com.example.moviemovie.information.model.PreMovie;
import com.example.moviemovie.information.model.Recommend;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FilmActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {
    // 오늘 날짜
    long now_today = System.currentTimeMillis();
    Date mdate_todat = new Date(now_today);

    SimpleDateFormat simpleDate_today = new SimpleDateFormat("yyyyMMdd");
    String getTime_today = simpleDate_today.format(mdate_todat);
    int timeTest_today = Integer.parseInt(getTime_today);
    int time_today = timeTest_today - 1;
    String day_today = Integer.toString(time_today);

    // 내일 날짜
    long now_tomo = System.currentTimeMillis();
    Date mdate_tomo = new Date(now_tomo);

    SimpleDateFormat simpleDate_tomo = new SimpleDateFormat("yyyyMMdd");
    String getTime_tomo = simpleDate_tomo.format(mdate_tomo);
    int timeTest_tomo = Integer.parseInt(getTime_tomo);
    int time_tomo = timeTest_tomo + 1;
    String day_tomo = Integer.toString(time_tomo);

    // 개봉 날짜 변경
    String releaseDts = "20000101";

    // film
    public static Box box = null;
    public static Box box1 = null;
    public static Box box2 = null;
    public static Box box3 = null;
    public static Box box4 = null;
    public static Box box5 = null;
    public static Box box6 = null;
    public static Box box7 = null;
    public static Box box8 = null;
    public static Box box9 = null;

    static List<String> titleA;

    String u = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";
    String r = "key=7457fbe3e653525bb4728f991a309884&targetDt=";
    String l = day_today;
    String urlBox = u + r + l;

    // 박스오피스
    ArrayList<Box> list_box;
    BoxAdapter adapter_box;
    RecyclerView recyclerView_box;
    LinearLayoutManager layoutManager_box;
    AsyncHttpClient client;

    // 개봉예정작
    ArrayList<PreMovie> list_pre;
    PreMovieAdapter adapter_pre;
    RecyclerView recyclerView_pre;
    LinearLayoutManager layoutManager_pre;
    PreMovieResponse response_pre;

    // 제목 받기
    FilmResponse response;

    // 이미지 받기
    MovieResponse movieResponse;
    MovieResponse1 movieResponse1;
    MovieResponse2 movieResponse2;
    MovieResponse3 movieResponse3;
    MovieResponse4 movieResponse4;
    MovieResponse5 movieResponse5;
    MovieResponse6 movieResponse6;
    MovieResponse7 movieResponse7;
    MovieResponse8 movieResponse8;
    MovieResponse9 movieResponse9;

    // 추천 영화
    ArrayList<Recommend> list_reco;
    RecommendAdapter adapter_reco;
    RecyclerView recyclerView_reco;
    LinearLayoutManager layoutManager_reco;
    RecommendResponse response_reco;
    GenreResponse response_genre;

    String id;

    //영화 장르
    int     fantasy =0,
            horror = 0,
            mellow = 0,
            kid =0,
            drama =0,
            comedy =0,
            crime = 0,
            character =0,
            musical =0,
            action =0,
            sf =0,
            war =0,
            etc =0;

    Button button_cal, button_info, button_home, button_my, button_serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        // 박스오피스
        list_box = new ArrayList<>();
        titleA = new ArrayList<>();
        adapter_box = new BoxAdapter(this, list_box);
        recyclerView_box = findViewById(R.id.recyclerView_box);
        client = new AsyncHttpClient();
        response = new FilmResponse();

        layoutManager_box = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_box.setLayoutManager(layoutManager_box);
        recyclerView_box.setAdapter(adapter_box);

        // 개봉예정작
        list_pre = new ArrayList<>();
        adapter_pre = new PreMovieAdapter(this, list_pre);
        recyclerView_pre = findViewById(R.id.recyclerView_pre);
        response_pre = new PreMovieResponse();

        layoutManager_pre = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_pre.setLayoutManager(layoutManager_pre);
        recyclerView_pre.setAdapter(adapter_pre);

        // 추천 영화
        list_reco = new ArrayList<>();
        adapter_reco = new RecommendAdapter(this, list_reco);
        recyclerView_reco = findViewById(R.id.recyclerView_reco);
        response_reco = new RecommendResponse();
        response_genre = new GenreResponse();

        layoutManager_reco = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_reco.setLayoutManager(layoutManager_reco);
        recyclerView_reco.setAdapter(adapter_reco);

        id = getIntent().getStringExtra("id");

        // 무비
        movieResponse = new MovieResponse(this, titleA);
        movieResponse1 = new MovieResponse1(this, titleA);
        movieResponse2 = new MovieResponse2(this, titleA);
        movieResponse3 = new MovieResponse3(this, titleA);
        movieResponse4 = new MovieResponse4(this, titleA);
        movieResponse5 = new MovieResponse5(this, titleA);
        movieResponse6 = new MovieResponse6(this, titleA);
        movieResponse7 = new MovieResponse7(this, titleA);
        movieResponse8 = new MovieResponse8(this, titleA);
        movieResponse9 = new MovieResponse9(this, titleA);

        adapter_box.setOnItemClickListener(new BoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Box box = list_box.get(pos);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(box.getWebPage()));
                startActivity(intent);
            }
        });

        adapter_pre.setOnItemClickListener(new PreMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                PreMovie pre = list_pre.get(pos);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pre.getWebPage()));
                startActivity(intent);
            }
        });

        adapter_reco.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Recommend reco = list_reco.get(pos);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reco.getWebPage()));
                startActivity(intent);
            }
        });

        button_cal = findViewById(R.id.button_cal);
        button_info = findViewById(R.id.button_info);
        button_home = findViewById(R.id.button_home);
        button_my = findViewById(R.id.button_my);
        button_serv = findViewById(R.id.button_serv);

        button_cal.setOnClickListener(this);
        button_info.setOnClickListener(this);
        button_home.setOnClickListener(this);
        button_my.setOnClickListener(this);
        button_serv.setOnClickListener(this);

        getGenre();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchData_box();
        searchData_pre(day_tomo);
    }

    private void getGenre() {


        String url ="http://192.168.123.102:8082/moviemovie/genre/genreSelect.do";
        // 집
        //String url = "http://192.168.0.165:8081/moviemovie/genre/genreSelect.do";
        // 학원
        // String url = "http://192.168.1.69:8081/moviemovie/genre/genreSelect.do";
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.get(url, params, response_genre);
        Log.d("[TEST]", "getGenre 호출 성공");
    }

    private void searchData_box() {
        String url = urlBox;
        client.get(url, response);
    }

    private void searchData_pre(String day_tomo) {
        RequestParams params = new RequestParams();
        params.put("ServiceKey", "06E1CQ572154KRS78LD5");
        params.put("listCount", 50);
        params.put("releaseDts", day_tomo);

        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
        client.get(url, params, response_pre);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_cal:       // 캘린더
                intent = new Intent(this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.button_info:      // 영화정보
//                intent = new Intent(this, FilmActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
                Toast.makeText(this, "현재 위치입니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_home:      // 홈
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button_my:        // 마이페이지
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_serv:      // 고객센터
                intent = new Intent(this, CSActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 박스오피스 가져오기
    public class FilmResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                JSONObject boxOfficeResult = json.getJSONObject("boxOfficeResult");
                JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");

                JSONObject temp = dailyBoxOfficeList.getJSONObject(9);
                box9 = new Box();
                box9.setRank(temp.getString("rank"));
                box9.setMovieNm(temp.getString("movieNm"));
                box9.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box9.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url9 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params9 = new RequestParams();
                params9.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params9.put("title", temp.getString("movieNm"));
                params9.put("sort", "title,0");
                params9.put("releaseDts", releaseDts);

                client.get(url9, params9, movieResponse9);

                temp = dailyBoxOfficeList.getJSONObject(8);
                box8 = new Box();
                box8.setRank(temp.getString("rank"));
                box8.setMovieNm(temp.getString("movieNm"));
                box8.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box8.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url8 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params8 = new RequestParams();
                params8.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params8.put("title", temp.getString("movieNm"));
                params8.put("sort", "title,0");
                params8.put("releaseDts", releaseDts);

                client.get(url8, params8, movieResponse8);

                temp = dailyBoxOfficeList.getJSONObject(7);
                box7 = new Box();
                box7.setRank(temp.getString("rank"));
                box7.setMovieNm(temp.getString("movieNm"));
                box7.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box7.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url7 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params7 = new RequestParams();
                params7.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params7.put("title", temp.getString("movieNm"));
                params7.put("sort", "title,0");
                params7.put("releaseDts", releaseDts);

                client.get(url7, params7, movieResponse7);

                temp = dailyBoxOfficeList.getJSONObject(6);
                box6 = new Box();
                box6.setRank(temp.getString("rank"));
                box6.setMovieNm(temp.getString("movieNm"));
                box6.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box6.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url6 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params6 = new RequestParams();
                params6.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params6.put("title", temp.getString("movieNm"));
                params6.put("sort", "title,0");
                params6.put("releaseDts", releaseDts);

                client.get(url6, params6, movieResponse6);

                temp = dailyBoxOfficeList.getJSONObject(5);
                box5 = new Box();
                box5.setRank(temp.getString("rank"));
                box5.setMovieNm(temp.getString("movieNm"));
                box5.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box5.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url5 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params5 = new RequestParams();
                params5.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params5.put("title", temp.getString("movieNm"));
                params5.put("sort", "title,0");
                params5.put("releaseDts", releaseDts);

                client.get(url5, params5, movieResponse5);

                temp = dailyBoxOfficeList.getJSONObject(4);
                box4 = new Box();
                box4.setRank(temp.getString("rank"));
                box4.setMovieNm(temp.getString("movieNm"));
                box4.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box4.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url4 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params4 = new RequestParams();
                params4.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params4.put("title", temp.getString("movieNm"));
                params4.put("sort", "title,0");
                params4.put("releaseDts", releaseDts);

                client.get(url4, params4, movieResponse4);

                temp = dailyBoxOfficeList.getJSONObject(3);
                box3 = new Box();
                box3.setRank(temp.getString("rank"));
                box3.setMovieNm(temp.getString("movieNm"));
                box3.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box3.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url3 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params3 = new RequestParams();
                params3.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params3.put("title", temp.getString("movieNm"));
                params3.put("sort", "title,0");
                params3.put("releaseDts", releaseDts);

                client.get(url3, params3, movieResponse3);

                temp = dailyBoxOfficeList.getJSONObject(2);
                box2 = new Box();
                box2.setRank(temp.getString("rank"));
                box2.setMovieNm(temp.getString("movieNm"));
                box2.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box2.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url2 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params2 = new RequestParams();
                params2.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params2.put("title", temp.getString("movieNm"));
                params2.put("sort", "title,0");
                params2.put("releaseDts", releaseDts);

                client.get(url2, params2, movieResponse2);

                temp = dailyBoxOfficeList.getJSONObject(1);
                box1 = new Box();
                box1.setRank(temp.getString("rank"));
                box1.setMovieNm(temp.getString("movieNm"));
                box1.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box1.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url1 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params1 = new RequestParams();
                params1.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params1.put("title", temp.getString("movieNm"));
                params1.put("sort", "title,0");
                params1.put("releaseDts", releaseDts);

                client.get(url1, params1, movieResponse1);

                temp = dailyBoxOfficeList.getJSONObject(0);
                box = new Box();
                box.setRank(temp.getString("rank"));
                box.setMovieNm(temp.getString("movieNm"));
                box.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");

                String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params = new RequestParams();
                params.put("ServiceKey", "06E1CQ572154KRS78LD5");
                params.put("title", temp.getString("movieNm"));

                client.get(url, params, movieResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FilmActivity.this, "통신 실패F", Toast.LENGTH_SHORT).show();
        }
    }

    // 이미지값 받고 set
    public class MovieResponse extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");

                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(1);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box.setImg(url1);
                    box.setWebPage(webPage);

                    adapter_box.addItem(box);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 이미지값 받고 set", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse1 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse1(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box1.setImg(url1);
                    box1.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box1);
                                }

                            });
                        }
                    };
                    m_timer.schedule(m_task, 100);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse1", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse2 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse2(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box2.setImg(url1);
                    box2.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box2);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 300);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse2", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse3 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse3(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box3.setImg(url1);
                    box3.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box3);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 500);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse3", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse4 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse4(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box4.setImg(url1);
                    box4.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box4);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 700);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse4", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse5 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse5(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box5.setImg(url1);
                    box5.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box5);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 900);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse5 ", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse6 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse6(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box6.setImg(url1);
                    box6.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box6);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 1100);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse6", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse7 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse7(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box7.setImg(url1);
                    box7.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box7);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 1300);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse7", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse8 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse8(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box8.setImg(url1);
                    box8.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box8);
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 1500);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse8", Toast.LENGTH_SHORT).show();
        }
    }

    public class MovieResponse9 extends AsyncHttpResponseHandler {
        Activity activity;
        List<String> list;

        public MovieResponse9(Activity activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    JSONArray data = json.getJSONArray("Data");
                    //Result
                    JSONObject temp = data.getJSONObject(0);
                    JSONArray resultTest = temp.getJSONArray("Result");
                    JSONObject result = resultTest.getJSONObject(0);
                    String urlTest = result.getString("posters");
                    String url1 = urlTest.substring(urlTest.lastIndexOf("|") + 1, urlTest.length());
                    String webPage = result.getString("kmdbUrl");

                    box9.setImg(url1);
                    box9.setWebPage(webPage);

                    Timer m_timer = new Timer();
                    TimerTask m_task = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_box.addItem(box9);
                                    adapter_box.notifyDataSetChanged();
                                }
                            });
                        }
                    };
                    m_timer.schedule(m_task, 1700);
                }
            } catch (Exception e) {

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(activity, "통신 실패 MovieResponse9", Toast.LENGTH_SHORT).show();
        }
    }

    // 개봉예정작
    public class PreMovieResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    for (int a = 0; a < totalCount; a++) {
                        PreMovie pre = new PreMovie();
                        JSONArray data = json.getJSONArray("Data");

                        // Result
                        JSONObject temp1 = data.getJSONObject(0);
                        JSONArray resultArray = temp1.getJSONArray("Result");
                        JSONObject movie_result = resultArray.getJSONObject(a);

                        // title
                        String title_basic = movie_result.getString("title").trim();
                        String title_empty1 = title_basic.replace("!HS", " ").trim();
                        String title_empty2 = title_empty1.replace("!HE", " ").trim();
                        String movie_title = title_empty2.replaceAll("   ", "");

                        // directorNm
                        JSONObject temp2 = movie_result.getJSONObject("directors");
                        JSONArray direcArray = temp2.getJSONArray("director");
                        JSONObject result_direc = direcArray.getJSONObject(0);

                        String movie_direc = "";
                        if (!result_direc.getString("directorNm").equals("")) {
                            movie_direc = result_direc.getString("directorNm");
                        } else {
                            movie_direc = "등록된 정보가 없습니다.";
                        }

                        // repRlsDate
                        String result4 = movie_result.getString("repRlsDate");
                        String movie_date = "";

                        log.v(result4,""+result4.length());

                        if (!result4.equals("")) {
                            if(result4.length() <8) break;
                            String year = result4.substring(0, 4) + "-";
                            String month = result4.substring(4, 6) + "-";
                            String day = result4.substring(6, 8);

                            movie_date = year + month + day;


                        } else {
                            movie_date = "등록된 정보가 없습니다.";
                        }

                        // genre
                        String movie_genre_basic = movie_result.getString("genre");
                        String movie_genre = movie_genre_basic.replaceAll(",", ", ");

                        // actorNm
                        JSONObject temp3 = movie_result.getJSONObject("actors");
                        JSONArray actorArray = temp3.getJSONArray("actor");

                        String result_actor = "";

                        for (int i = 0; i < actorArray.length(); i++) {
                            JSONObject result_actorNm = actorArray.getJSONObject(i);
                            if (!result_actorNm.getString("actorNm").equals("")) {
                                if (i < 3) {
                                    result_actor += result_actorNm.getString("actorNm") + "｜";
                                }
                            } else {
                                result_actor = "등록된 정보가 없습니다.";
                            }
                        }

                        String movie_actor = "";

                        if (result_actor.equals("등록된 정보가 없습니다.")) {
                            movie_actor = "등록된 정보가 없습니다.";
                        } else {
                            movie_actor = result_actor.substring(0, result_actor.length() - 1);
                        }

                        // posterUrl
                        String url = movie_result.getString("posters");
                        String movie_url = url.substring(url.lastIndexOf("|") + 1, url.length());

                        //webPage
                        String webPage = movie_result.getString("kmdbUrl");

                        pre.setTitle(movie_title);
                        pre.setActorNm("배우 : " + movie_actor);
                        pre.setDirectorNm("감독 : " + movie_direc);
                        pre.setReleaseDate("개봉일 :" + movie_date);
                        pre.setPosterUrl(movie_url);
                        pre.setWebPage(webPage);
                        pre.setGenre(movie_genre);

                        //리스트에 적용
                        adapter_pre.addItem(pre);
//                        adapter_pre.notifyDataSetChanged();
                        //초기화
                        movie_actor = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FilmActivity.this, "통신 실패 PreMovieResponse", Toast.LENGTH_SHORT).show();
        }
    }

    // 추천 영화
    public class RecommendResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                int totalCount = json.getInt("TotalCount");
                if (totalCount > 0) {
                    for (int a = 0; a < totalCount; a++) {
                        Recommend reco = new Recommend();
                        JSONArray data = json.getJSONArray("Data");

                        // Result
                        JSONObject temp1 = data.getJSONObject(0);
                        JSONArray resultArray = temp1.getJSONArray("Result");
                        JSONObject movie_result = resultArray.getJSONObject(a);

                        // title
                        String title_basic = movie_result.getString("title").trim();
                        String title_empty1 = title_basic.replace("!HS", " ").trim();
                        String title_empty2 = title_empty1.replace("!HE", " ").trim();
                        String movie_title = title_empty2.replaceAll("   ", "");

                        // directorNm
                        JSONObject temp2 = movie_result.getJSONObject("directors");
                        JSONArray direcArray = temp2.getJSONArray("director");
                        JSONObject result_direc = direcArray.getJSONObject(0);

                        String movie_direc = "";
                        if (!result_direc.getString("directorNm").equals("")) {
                            movie_direc = result_direc.getString("directorNm");
                        } else {
                            movie_direc = "등록된 정보가 없습니다.";
                        }

                        // genre
                        String movie_genre_basic = movie_result.getString("genre");
                        String movie_genre = movie_genre_basic.replaceAll(",", ", ");

                        // actorNm
                        JSONObject temp3 = movie_result.getJSONObject("actors");
                        JSONArray actorArray = temp3.getJSONArray("actor");

                        String result_actor = "";

                        for (int i = 0; i < actorArray.length(); i++) {
                            JSONObject result_actorNm = actorArray.getJSONObject(i);
                            if (!result_actorNm.getString("actorNm").equals("")) {
                                if (i < 3) {
                                    result_actor += result_actorNm.getString("actorNm") + "｜";
                                }
                            } else {
                                result_actor = "등록된 정보가 없습니다.";
                            }
                        }

                        String movie_actor = "";

                        if (result_actor.equals("등록된 정보가 없습니다.")) {
                            movie_actor = "등록된 정보가 없습니다.";
                        } else {
                            movie_actor = result_actor.substring(0, result_actor.length() - 1);
                        }

                        // posterUrl
                        String url = movie_result.getString("posters");
                        String movie_url = url.substring(url.lastIndexOf("|") + 1, url.length());

                        //webPage
                        String webPage = movie_result.getString("kmdbUrl");

                        reco.setTitle(movie_title);
                        reco.setActorNm("배우 : " + movie_actor);
                        reco.setDirectorNm("감독 : " + movie_direc);
                        reco.setPosterUrl(movie_url);
                        reco.setWebPage(webPage);
                        reco.setGenre(movie_genre);

                        //리스트에 적용
                        adapter_reco.addItem(reco);
//                        adapter_reco.notifyDataSetChanged();
                        //초기화
                        movie_actor = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FilmActivity.this, "통신 실패 RecommendResponse", Toast.LENGTH_SHORT).show();
        }
    }

    public class GenreResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if (rt.equals("OK")) {
                    mellow = json.getInt("mellow");
                    fantasy = json.getInt("fantasy");
                    horror = json.getInt("horror");
                    musical = json.getInt("musical");
                    comedy = json.getInt("comedy");
                    kid = json.getInt("kid");
                    war = json.getInt("war");
                    drama = json.getInt("drama");
                    character = json.getInt("character");
                    sf = json.getInt("sf");
                    etc = json.getInt("etc");
                    action = json.getInt("action");
                    crime = json.getInt("crime");
                }
                searchData_reco();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void searchData_reco() {
            //배열 생성
            int array[] = {
                    fantasy,
                    horror,
                    mellow,
                    kid,
                    drama,
                    comedy,
                    crime,
                    character,
                    musical,
                    action,
                    sf,
                    war,
                    etc
            };
            Arrays.sort(array);
            Log.d("max", String.valueOf(array[array.length-1]));

            //최대값
            int maxValue = array[array.length-1];

            //같은 값 찾기
            int arraySearch[] = {
                    fantasy,
                    horror,
                    mellow,
                    kid,
                    drama,
                    comedy,
                    crime,
                    character,
                    musical,
                    action,
                    sf,
                    war,
                    etc
            };
            int same=0;
            for(int i=0;i<arraySearch.length;i++){
                if(arraySearch[i] == maxValue){
                    same = i;
                }
            }

            //같은 배열 이름
            String[] arrayName = {"판타지","공포","멜로/로맨스","아동","드라마","코메디","범죄",
                    "인물","뮤지컬","액션","SF","전쟁",""};

            //검색할 내용
            String title = arrayName[same];

            RequestParams params = new RequestParams();
            params.put("ServiceKey", "06E1CQ572154KRS78LD5");
            params.put("genre", title);
            params.put("releaseDts", 2020);
            params.put("listCount", 20);

            String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
            client.get(url, params, response_reco);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(FilmActivity.this, "통신 실패 GenreResponse", Toast.LENGTH_SHORT).show();

        }
    }
}