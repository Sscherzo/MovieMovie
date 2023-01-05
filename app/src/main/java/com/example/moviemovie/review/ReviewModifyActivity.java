package com.example.moviemovie.review;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.review.model.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class ReviewModifyActivity extends AppCompatActivity implements View.OnClickListener {
    // 객체 선언
    RatingBar ratingBar;
    EditText editText_title, editText_director, editText_playdate, editText_runtime, editText_genre,
            editText_actor, editText_plot, editText_watch, editText_place, editText_review;
    Button button_cancle, button_ok;
    ImageView image_movie;
    public static int yy, mm, dd;
    AsyncHttpClient client;
    HttpResponse response;
    Review item;
    String id = pref.getString("id",null);

    // 업로드할 사진파일의 경로
    String filePath = null;
    // 업로드할 URL의 경로
    String urlpath = null;



    String url ="http://192.168.123.102:8082/moviemovie/review/review_modify.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_modify.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_modify.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_modify);

        // 객체 초기화
        ratingBar = findViewById(R.id.ratingBar);
        editText_title = findViewById(R.id.editText_title);
        editText_director = findViewById(R.id.editText_director);
        editText_playdate = findViewById(R.id.editText_playdate);
        editText_runtime = findViewById(R.id.editText_runtime);
        editText_genre = findViewById(R.id.editText_genre);
        editText_actor = findViewById(R.id.editText_actor);
        editText_plot = findViewById(R.id.editText_plot);
        editText_watch = findViewById(R.id.editText_watch);
        editText_place = findViewById(R.id.editText_place);
        editText_review = findViewById(R.id.editText_review);
        button_cancle = findViewById(R.id.button_cancle);
        button_ok = findViewById(R.id.button_ok);
        image_movie = findViewById(R.id.image_movie);
        client = new AsyncHttpClient();
        response = new HttpResponse();

        editText_watch.setInputType(0);

        item = (Review) getIntent().getSerializableExtra("item");

        // 화면 설정
        ratingBar.setRating(item.getRate());
        editText_title.setText(item.getTitle());
        editText_director.setText(item.getDirector());
        editText_playdate.setText(item.getPlaydate());
        editText_runtime.setText(item.getRuntime() + "분");
        editText_genre.setText(item.getGenre());
        editText_actor.setText(item.getActor());
        editText_plot.setText(item.getPlot());
        editText_watch.setText(item.getWatch());
        editText_place.setText(item.getPlace());
        editText_review.setText(item.getReview());
        if (item.getFilename() != null) {
            Glide.with(this).load(item.getFilename()).into(image_movie);
        } else {
            Glide.with(this).load(item.getImageUrl()).into(image_movie);
        }

        // 이벤트 설정
        button_cancle.setOnClickListener(this);
        button_ok.setOnClickListener(this);
        editText_watch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogFragment datePickerFragment = null;
        switch (v.getId()) {
            case R.id.button_cancle:        // 취소
                finish();
                break;
            case R.id.button_ok:            // 수정
                float rate = ratingBar.getRating();
                String watch = editText_watch.getText().toString().trim();
                String place = editText_place.getText().toString().trim();
                String review = editText_review.getText().toString().trim();

                // 입력검사
                String msg = null;
                if (msg == null && rate == 0) {
                    msg = "별점(0.5)점 이상 입력해 주세요.";
                } else if (msg == null && watch.equals("")) {
                    msg = "관람일을 입력해 주세요.";
                } else if (msg == null && place.equals("")) {
                    msg = "관람 장소를 입력해 주세요.";
                } else if (msg == null && review.equals("")) {
                    msg = "나의 리뷰를 입력해 주세요.";
                }

                // 입력값 없는 항목이 있을 경우
                if (msg != null) {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 요청 및 데이터 전송
                RequestParams params = new RequestParams();
                params.put("seq", item.getSeq());
                params.put("rate", rate);
                params.put("watch", watch);
                params.put("place", place);
                params.put("review", review);
                params.put("id", id);

                client.post(url, params, response);
                break;
            case R.id.editText_watch:       // 관람일
                // DatePickerFragment 초기화
                datePickerFragment = new DatePickerHoloLightwatch();
                // DatePickerFragment 표시
                datePickerFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    public static class DatePickerHoloLightwatch extends android.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // 현재 날짜
            Calendar calendar = Calendar.getInstance();
            yy = calendar.get(Calendar.YEAR);
            mm = calendar.get(Calendar.MONTH);
            dd = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            yy = year;
            mm = month + 1;
            dd = day;

            EditText editText_date = (EditText) getActivity().findViewById(R.id.editText_watch);
            editText_date.setText(String.format("%d년 %d월 %d일", yy, mm, dd));
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        ProgressDialog dialog;

        @Override
        public void onStart() {
            dialog = new ProgressDialog(ReviewModifyActivity.this);
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
                if (rt.equals("OK")) {
                    finish();   // 이전 화면으로 돌아가기
                    Toast.makeText(ReviewModifyActivity.this, "수정 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReviewModifyActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewModifyActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}