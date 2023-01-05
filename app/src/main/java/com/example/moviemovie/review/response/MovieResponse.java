package com.example.moviemovie.review.response;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviemovie.review.adapter.MovieAdapter;
import com.example.moviemovie.review.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieResponse extends AsyncHttpResponseHandler {
    Activity activity;
    MovieAdapter adapter;
    ListView listView;
    View footerView;          // "기다려주세요" footer

    public MovieResponse(Activity activity, MovieAdapter adapter, ListView listView, View footerView) {
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
            // 검색되는 총 개수
            int totalCount = json.getInt("TotalCount");
//            Toast.makeText(activity,"" + totalCount,Toast.LENGTH_SHORT).show();
            if(totalCount > 0) {
                for(int a = 0; a < totalCount; a++) {
                    Movie movie = new Movie();
                    JSONArray data = json.getJSONArray("Data");

                    // Result
                    JSONObject temp1 = data.getJSONObject(0);
                    JSONArray resultArray = temp1.getJSONArray("Result");
                    JSONObject movie_result = resultArray.getJSONObject(a);

                    // title
                    String title_basic = movie_result.getString("title").trim();
                    String title_empty1 = title_basic.replace("!HS", " ").trim();
                    String title_empty2 = title_empty1.replace("!HE", " ").trim();
                    String movie_title = title_empty2.replaceAll("   ","");

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

                    if (!result4.equals("")) {
                        String year = result4.substring(0, 4) + "년 ";
                        String month = result4.substring(4, 6) + "월 ";
                        String day = result4.substring(6, 8) + "일";

                        movie_date = year + month + day;
                    } else {
                        movie_date = "등록된 정보가 없습니다.";
                    }

                    // runtime
                    String movie_runtime = movie_result.getString("runtime");

                    // genre
                    String movie_genre_basic = movie_result.getString("genre");
                    String movie_genre = movie_genre_basic.replaceAll(",", ", ");
                    if(movie_genre.equals("")) {
                        movie_genre = "기타";
                    }

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

                    if(result_actor.equals("등록된 정보가 없습니다.")){
                        movie_actor="등록된 정보가 없습니다.";
                    } else {
                        movie_actor = result_actor.substring(0, result_actor.length()-1);
                    }

                    // plot
                    JSONObject temp4 = movie_result.getJSONObject("plots");
                    JSONArray plotArray = temp4.getJSONArray("plot");
                    JSONObject result_plot = plotArray.getJSONObject(0);

                    String movie_plot = "";
                    if (!result_plot.getString("plotText").equals("")) {
                        String movie_plot_basic = result_plot.getString("plotText");
                        String movie_plot_byte = movie_plot_basic.replaceAll("'", "\"");
                        if (movie_plot_byte.length() > 1500) {
                            movie_plot = movie_plot_byte.substring(0, 1500);
                        } else {
                            movie_plot = movie_plot_byte;
                        }
                    } else {
                        movie_plot = "등록된 정보가 없습니다.";
                    }

                    // posterUrl
                    String url = movie_result.getString("posters");
                    String movie_url = url.substring(url.lastIndexOf("|") + 1, url.length());
                    Log.d("[POSTER]", movie_url);

                    movie.setTitle(movie_title);
                    movie.setDirectorNm(movie_direc);
                    movie.setReleaseDate(movie_date);
                    movie.setRuntime(movie_runtime);
                    movie.setGenre(movie_genre);
                    movie.setActorNm(movie_actor);
                    movie.setPlot(movie_plot);
                    movie.setPosterUrl(movie_url);

                    //리스트에 적용
                    adapter.add(movie);

                    //초기화
                    result_actor = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(activity, "통신 실패", Toast.LENGTH_SHORT).show();
    }
}
