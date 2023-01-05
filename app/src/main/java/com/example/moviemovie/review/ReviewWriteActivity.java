package com.example.moviemovie.review;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.example.moviemovie.review.helper.FileUtils;
import com.example.moviemovie.review.helper.PhotoHelper;
import com.example.moviemovie.review.model.Movie;
import com.example.moviemovie.review.model.Search;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.editor;
import static com.example.moviemovie.LoginActivity.pref;

public class ReviewWriteActivity extends AppCompatActivity implements View.OnClickListener {
    // 객체 선언
    RatingBar ratingBar;
    EditText editText_title, editText_director, editText_playdate, editText_runtime, editText_genre,
            editText_actor, editText_plot, editText_watch, editText_place, editText_review;
    Button button_cancle, button_ok, button_search, button_edit;
    ImageView image_movie;
    public static int yy, mm, dd;
    AsyncHttpClient client;
    HttpResponse response;
    HttpResponseGenre responseGenre;
    HttpResponseGenreModify responseGenreModify;
    String id;

    //영화 장르
    int     fantasy = 0,
            horror = 0,
            mellow = 0,
            kid = 0,
            drama = 0,
            comedy = 0,
            crime = 0,
            character = 0,
            musical = 0,
            action = 0,
            sf = 0,
            war = 0,
            etc = 0;

    // 업로드할 사진파일의 경로
    String filePath = null;
    // 업로드할 URL의 경로
    String urlpath = null;


    String url ="http://192.168.123.102:8082/moviemovie/review/review_insert.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_insert.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_insert.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

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
        button_search = findViewById(R.id.button_search);
        button_edit = findViewById(R.id.button_edit);
        image_movie = findViewById(R.id.image_movie);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        responseGenre = new HttpResponseGenre();
        responseGenreModify = new HttpResponseGenreModify();
        id = getIntent().getStringExtra("id");

        editText_playdate.setInputType(0);
        editText_watch.setInputType(0);

        // 이벤트 설정
        button_cancle.setOnClickListener(this);
        button_ok.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_edit.setOnClickListener(this);
        editText_playdate.setOnClickListener(this);
        editText_watch.setOnClickListener(this);

        // 내용 초기화
        editText_title.setText("");
        editText_director.setText("");
        editText_playdate.setText("");
        editText_runtime.setText("");
        editText_genre.setText("");
        editText_actor.setText("");
        editText_plot.setText("");
        editText_watch.setText("");
        editText_place.setText("");
        editText_review.setText("");

        Log.d("[ID]", id);

        getGenre();
    }

    @Override
    public void onClick(View v) {
        DialogFragment datePickerFragment = null;
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_cancle:        // 취소
                finish();
                break;
            case R.id.button_ok:            // 저장
                float rate = ratingBar.getRating();
                String title = editText_title.getText().toString().trim();
                String director = editText_director.getText().toString().trim();
                String playdate = editText_playdate.getText().toString().trim();
                String runtime_mm = editText_runtime.getText().toString().trim();
                String runtime = runtime_mm.replaceAll("분", "");
                String genre = editText_genre.getText().toString().trim();
                String actor = editText_actor.getText().toString().trim();
                String plot = editText_plot.getText().toString().trim();
                String watch = editText_watch.getText().toString().trim();
                String place = editText_place.getText().toString().trim();
                String review = editText_review.getText().toString().trim();

                // 입력검사
                String msg = null;
                if (msg == null && rate == 0) {
                    msg = "별점(0.5)점 이상 입력해 주세요.";
                } else if (msg == null && title.equals("") || director.equals("") || playdate.equals("") || runtime.equals("")
                        || genre.equals("") || actor.equals("") || plot.equals("")) {
                    msg = "영화 정보를 검색해 주세요.";
                } else if (msg == null && watch.equals("")) {
                    msg = "관람일을 입력해 주세요.";
                } else if (msg == null && place.equals("")) {
                    msg = "관람 장소를 입력해 주세요.";
                } else if (msg == null && review.equals("")) {
                    msg = "나의 리뷰를 입력해 주세요.";
                } else if (msg == null && filePath == null && urlpath == null) {
                    msg = "사진을 선택해 주세요.";
                }

                // 입력값 없는 항목이 있을 경우
                if (msg != null) {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 요청 및 데이터 전송
                RequestParams params = new RequestParams();
                params.setForceMultipartEntityContentType(true);
                params.put("rate", rate);
                params.put("title", title);
                params.put("director", director);
                params.put("playdate", playdate);
                params.put("runtime", runtime);
                params.put("genre", genre);
                params.put("actor", actor);
                params.put("plot", plot);
                params.put("watch", watch);
                params.put("place", place);
                params.put("review", review);
                params.put("id", id);
                try {
                    if (filePath != null) {
                        params.put("photo", new File(filePath));
                    } else {
                        params.put("imageUrl", urlpath);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // multipart로 보내기 설정
                params.setForceMultipartEntityContentType(true);
                client.post(url, params, response);
                modifyGenre(genre);
                break;
            case R.id.button_search:   // 영화 검색
                intent = new Intent(ReviewWriteActivity.this, MovieActivity.class);
                // 선택된 파일을 돌려받아야 함
                startActivityForResult(intent, 500);
                break;
            case R.id.button_edit:     // 사진 등록
                showListDialog();
                break;
            case R.id.editText_playdate:    // 개봉일
                // DatePickerFragment 초기화
                datePickerFragment = new DatePickerHoloLightdate();
                // DatePickerFragment 표시
                datePickerFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.editText_watch:       // 관람일
                // DatePickerFragment 초기화
                datePickerFragment = new DatePickerHoloLightwatch();
                // DatePickerFragment 표시
                datePickerFragment.show(getFragmentManager(), "datePicker");
                break;
        }
    }

    private void modifyGenre(String genre) {
        Log.d("[TEST]", "modifyGenre 호출 성공");

        String url ="http://192.168.123.102:8082/moviemovie/genre/genreModify.do";
        // 집
        // String url = "http://192.168.0.165:8081/moviemovie/genre/genreModify.do";
        // 학원
        //String url = "http://192.168.1.69:8081/moviemovie/genre/genreModify.do";
        if(genre.indexOf(",") != -1) {      // 문자열이 포함되지 않으면 -1 리턴
            String genreCut[] = genre.split(",");
            for(int i=0; i<genreCut.length; i++) {
                if(genreCut[i].equals("SF")) {
                    ++sf;
                } else if(genreCut[i].equals("아동")) {            // 애니메이션
                    ++kid;
                } else if(genreCut[i].equals("공포") || genreCut[i].equals("미스터리")
                        || genreCut[i].equals("스릴러")) {
                    ++horror;
                } else if(genreCut[i].equals("드라마") || genreCut[i].equals("가족")) {
                    ++drama;
                } else if(genreCut[i].equals("멜로/로맨스")) {
                    ++mellow;
                } else if(genreCut[i].equals("뮤지컬")) {
                    ++musical;
                } else if(genreCut[i].equals("인물")) {            // 다큐멘터리
                    ++character;
                } else if(genreCut[i].equals("범죄")) {
                    ++crime;
                } else if(genreCut[i].equals("액션")) {
                    ++action;
                } else if(genreCut[i].equals("코메디")) {
                    ++comedy;
                } else if(genreCut[i].equals("판타지") || genreCut[i].equals("어드벤처")) {
                    ++fantasy;
                } else if(genreCut[i].equals("전쟁")) {
                    ++war;
                } else {
                    ++etc;
                }
                Log.d("[GENRE]",genreCut[i]);
                RequestParams params = new RequestParams();
                params.put("sf", sf);
                params.put("fantasy",fantasy);
                params.put("horror", horror);
                params.put("drama", drama);
                params.put("mellow", mellow);
                params.put("musical", musical);
                params.put("kid", kid);
                params.put("character",character);
                params.put("action", action);
                params.put("comedy", comedy);
                params.put("war", war);
                params.put("crime",crime);
                params.put("etc", etc);
                params.put("id", id);
                client.get(url, params, responseGenreModify);
            }
        } else {
            if(genre.equals("SF")) {
                ++sf;
            } else if(genre.equals("아동")) {            // 애니메이션
                ++kid;
            } else if(genre.equals("공포") || genre.equals("미스터리")
                    || genre.equals("스릴러")) {
                ++horror;
            } else if(genre.equals("드라마") || genre.equals("가족")) {
                ++drama;
            } else if(genre.equals("멜로/로맨스")) {
                ++mellow;
            } else if(genre.equals("뮤지컬")) {
                ++musical;
            } else if(genre.equals("인물")) {            // 다큐멘터리
                ++character;
            } else if(genre.equals("범죄")) {
                ++crime;
            } else if(genre.equals("액션")) {
                ++action;
            } else if(genre.equals("코메디")) {
                ++comedy;
            } else if(genre.equals("판타지") || genre.equals("어드벤처")) {
                ++fantasy;
            } else if(genre.equals("전쟁")) {
                ++war;
            } else {
                ++etc;
            }
            Log.d("[GENRE]", genre);
            RequestParams params = new RequestParams();
            params.put("sf", sf);
            params.put("fantasy",fantasy);
            params.put("horror", horror);
            params.put("drama", drama);
            params.put("mellow", mellow);
            params.put("musical", musical);
            params.put("kid", kid);
            params.put("character",character);
            params.put("action", action);
            params.put("comedy", comedy);
            params.put("war", war);
            params.put("crime",crime);
            params.put("etc", etc);
            params.put("id", id);
            client.get(url, params, responseGenreModify);
        }
    }

    private void getGenre() {
        String url ="http://192.168.123.102:8082/moviemovie/genre/genreSelect.do";
        // 집
        // String url = "http://192.168.0.165:8081/moviemovie/genre/genreSelect.do";
        // 학원
        //String url = "http://192.168.1.69:8081/moviemovie/genre/genreSelect.do";
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(url, params, responseGenre);
        Log.d("[TEST]", "getGenre 호출 성공");
    }

    public static class DatePickerHoloLightwatch extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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

    public static class DatePickerHoloLightdate extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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

            EditText editText_playdate = (EditText) getActivity().findViewById(R.id.editText_playdate);
            editText_playdate.setText(String.format("%d년 %d월 %d일", yy, mm, dd));
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        ProgressDialog dialog;

        @Override
        public void onStart() {
            dialog = new ProgressDialog(ReviewWriteActivity.this);
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
                    Toast.makeText(ReviewWriteActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReviewWriteActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewWriteActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void showListDialog() {
        // 퍼미션 체크
        permissionCheck();
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
//        builder.setCancelable(false);
        final String[] items = {"새로 촬영하기", "갤러리에서 가져오기", "웹에서 검색하기"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:     // 새로 촬영하기
                        // 저장할 사진 경로
                        filePath = PhotoHelper.getInstance().getNewPhotoPath();
                        // filePath = /storage/emulated/0/DCIM/p2020-11-10 16-43-00.jpg
                        Log.d("[TEST]", "filePath = " + filePath);
                        // 카메라 앱 호출
                        File file = new File(filePath);
                        Uri uri = null;

                        // 카메라 앱 호출을 위한 암묵적 인텐트 => action과 uri 필요
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", file);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        Log.d("[TEST]", "uri = " + uri);
                        // uri = content://com.example.imageboard.fileprovider/external_files/DCIM/2020-11-10%2016-52-24.jpg
                        // 저장될 경로를 파라미터로 설정
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        intent.putExtra(AUDIO_SERVICE, false);  // 사진이기때문에 오디오 서비스 사용안함 처리
                        // 카메라 앱 호출
                        startActivityForResult(intent, 200);
                        break;
                    case 1:     // 갤러리에서 가져오기
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        // 이미지 파일만 필터링 -> MIME 형태
                        intent.setType("image/*");
                        // 구글 클라우드에 싱크된 이미지 파일 제외
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        // 선택된 파일을 돌려받아야 함
                        startActivityForResult(intent, 300);
                        break;
                    case 2:     // 웹에서 검색하기
                        intent = new Intent(ReviewWriteActivity.this, SearchActivity.class);
                        // 선택된 파일을 돌려받아야 함
                        startActivityForResult(intent, 400);
                        break;
                }
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        InputStream in = null;
        switch (requestCode) {
            case 200:       // 카메라 앱 호출 후
                // 촬영 결과물을 MediaStore에 등록한다.
                // MediaStore에 등록하지 않으면, 우리 앱에서 만든 파일을 다른 앱에서는 사용할 수 없음
                Intent photoIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + filePath));
                sendBroadcast(photoIntent);
                image_movie.setImageURI(Uri.parse(filePath));
                break;
            case 300:       // 갤러리 앱 호출 후
                if (resultCode == RESULT_OK) {
                    filePath = FileUtils.getPath(this, data.getData());
                    // content://com.android.providers.media.documents/document/image%3A47
                    Log.d("[TEST]", data.getData().toString());
                    // filePath = /storage/emulated/0/DCIM/2020-11-10 16-52-24.jpg
                    Log.d("[TEST]", "filePath = " + filePath);
                    image_movie.setImageURI(Uri.parse(filePath));
                }
                break;
            case 400:       // 웹검색 호출 후
                // 검색 없이 뒤로가기 눌렀을 경우 어플 종료 해야함
                Search item_search = (Search) data.getSerializableExtra("item");
                // 화면 설정
                //image_movie.setImageURI(Uri.parse(item.getImage_url()));
                Glide.with(this).load(item_search.getImage_url()).into(image_movie);
                urlpath = item_search.getImage_url();
                break;
            case 500:       // 영화 검색 후
                Movie item_movie = (Movie) data.getSerializableExtra("item");
                // 화면 설정
                Glide.with(this).load(item_movie.getPosterUrl()).into(image_movie);
                urlpath = item_movie.getPosterUrl();
                editText_title.setText(item_movie.getTitle());
                editText_director.setText(item_movie.getDirectorNm());
                editText_playdate.setText(item_movie.getReleaseDate());
                editText_runtime.setText(item_movie.getRuntime() + "분");
                editText_genre.setText(item_movie.getGenre());
                editText_actor.setText(item_movie.getActorNm());
                editText_plot.setText(item_movie.getPlot());
                break;
        }
    }

    class HttpResponseGenre extends AsyncHttpResponseHandler {

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewWriteActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public class HttpResponseGenreModify extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);

            try {
                JSONObject json = new JSONObject(str);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ReviewWriteActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}