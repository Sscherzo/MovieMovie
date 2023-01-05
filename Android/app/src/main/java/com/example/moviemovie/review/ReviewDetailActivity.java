package com.example.moviemovie.review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.calendar.CalendarActivity;
import com.example.moviemovie.review.model.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class ReviewDetailActivity extends AppCompatActivity {
    // 객체 선언
    RatingBar ratingBar;
    TextView textView_title, textView_director, textView_genre, textView_runtime, textView_playdate, textView_actor,
            textView_plot, textView_watch, textView_review;
    ImageView image_movie;
    AsyncHttpClient client;
    HttpResponse response;
    Review item;
    String id = pref.getString("id",null);


    String url ="http://192.168.123.102:8082/moviemovie/review/review_select.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_select.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_select.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        // 객체 초기화
        ratingBar = findViewById(R.id.ratingBar);
        textView_title = findViewById(R.id.textView_title);
        textView_director = findViewById(R.id.textView_director);
        textView_genre = findViewById(R.id.textView_genre);
        textView_runtime = findViewById(R.id.textView_runtime);
        textView_playdate = findViewById(R.id.textView_playdate);
        textView_actor = findViewById(R.id.textView_actor);
        textView_plot = findViewById(R.id.textView_plot);
        textView_watch = findViewById(R.id.textView_watch);
        textView_review = findViewById(R.id.textView_review);
        image_movie = findViewById(R.id.image_movie);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_rev));
        getSupportActionBar().setTitle("");

        item = (Review) getIntent().getSerializableExtra("item");
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("seq", item.getSeq());
        params.put("id", id);
        client.post(url, params, response);
    }

    // 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reviewdetail_menu, menu);
        return true;
    }
    // 옵션메뉴 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        ProgressDialog dialog;

        @Override
        public void onStart() {
            dialog = new ProgressDialog(ReviewDetailActivity.this);
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
            dialog = null;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                int total  = json.getInt("total");

                if(rt.equals("OK")) {
                    if (total > 0) {
                        JSONArray item1 = json.getJSONArray("item");
                        JSONObject temp = item1.getJSONObject(0);
                        item.setSeq(temp.getInt("seq"));
                        String filename = temp.optString("filename", null);
                        if (filename != null) {
                            Glide.with(ReviewDetailActivity.this).load(item.getFilename()).into(image_movie);
                        } else {
                            Glide.with(ReviewDetailActivity.this).load(item.getImageUrl()).into(image_movie);
                        }
                        item.setRate((float) temp.getDouble("rate"));
                        item.setTitle(temp.getString("title"));
                        item.setDirector(temp.getString("director"));
                        item.setPlaydate(temp.getString("playdate"));
                        item.setRuntime(temp.getString("runtime"));
                        item.setGenre(temp.getString("genre"));
                        item.setActor(temp.getString("actor"));
                        item.setPlot(temp.getString("plot"));
                        item.setWatch(temp.getString("watch"));
                        item.setPlace(temp.getString("place"));
                        item.setReview(temp.getString("review"));

                        // 화면 초기화
                        ratingBar.setRating(item.getRate());
                        textView_title.setText(item.getTitle());
                        textView_director.setText(item.getDirector());
                        textView_genre.setText(item.getGenre());
                        textView_runtime.setText(item.getRuntime() + "분");
                        textView_playdate.setText(item.getPlaydate());
                        textView_actor.setText(item.getActor());
                        textView_plot.setText(item.getPlot());
                        textView_watch.setText(item.getWatch());
                        textView_review.setText(item.getReview());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewDetailActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}