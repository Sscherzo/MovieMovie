package com.example.moviemovie.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.example.moviemovie.SettingActivity;
import com.example.moviemovie.calendar.decorator.EventDecorator;
import com.example.moviemovie.calendar.decorator.OneDayDecorator;
import com.example.moviemovie.calendar.decorator.SaturdayDecorator;
import com.example.moviemovie.calendar.decorator.SundayDecorator;
import com.example.moviemovie.cs.CSActivity;
import com.example.moviemovie.information.FilmActivity;
import com.example.moviemovie.review.ReviewWriteActivity;
import com.example.moviemovie.review.TicketActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class CalendarActivity extends AppCompatActivity implements OnDateSelectedListener, View.OnClickListener {
    MaterialCalendarView materialCalendarView;
    AsyncHttpClient client;
    HttpResponse response;
    String id = pref.getString("id",null);
    ArrayList<String> list = new ArrayList<String>();
    Button button_cal, button_info, button_home, button_my, button_serv;


    String url ="http://192.168.123.102:8082/moviemovie/review/review_watchtotal.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_watchtotal.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_watchtotal.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        materialCalendarView = findViewById(R.id.materialCalendarView);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_cal));
        getSupportActionBar().setTitle("");
        client = new AsyncHttpClient();
        response = new HttpResponse();
        button_cal = findViewById(R.id.button_cal);
        button_info = findViewById(R.id.button_info);
        button_home = findViewById(R.id.button_home);
        button_my = findViewById(R.id.button_my);
        button_serv = findViewById(R.id.button_serv);

        // 일주일 시작을 월요일로 설정
        materialCalendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).commit();
        // 달력 데코레이션
        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());
        // 요일 클릭 이벤트
        materialCalendarView.setOnDateChangedListener(this);

        // 버튼 클릭 이벤트
        button_cal.setOnClickListener(this);
        button_info.setOnClickListener(this);
        button_home.setOnClickListener(this);
        button_my.setOnClickListener(this);
        button_serv.setOnClickListener(this);

        // 날짜 불러오기
//        getDay();
    }

    private void getDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String startString = "20201201";
        String endString = "20201208";
        {
            try {
                Date startDate = simpleDateFormat.parse(startString);
                Date endDate = simpleDateFormat.parse(endString);

                Calendar startCal = Calendar.getInstance();
                Calendar endCal = Calendar.getInstance();
                startCal.setTime(startDate);
                endCal.setTime(endDate);

                while (startCal.compareTo(endCal) != 1) {
                    startCal.add(Calendar.DATE, 1);
                    // str = Wed Jan 01 00:00:00 GMT+09:00 2031
                    String str = String.valueOf(startCal.getTime());
                    String ye = str.substring(str.length()-4, str.length());
                    String mo = str.substring(4, 7);
                    if(mo.equals("Jan")) {
                        mo = "1";
                    } else if(mo.equals("Feb")) {
                        mo = "2";
                    } else if(mo.equals("Mar")) {
                        mo = "3";
                    } else if(mo.equals("Apr")) {
                        mo = "4";
                    } else if(mo.equals("May")) {
                        mo = "5";
                    } else if(mo.equals("Jun")) {
                        mo = "6";
                    } else if(mo.equals("Jul")) {
                        mo = "7";
                    } else if(mo.equals("Aug")) {
                        mo = "8";
                    } else if(mo.equals("Sep")) {
                        mo = "9";
                    } else if(mo.equals("Oct")) {
                        mo = "10";
                    } else if(mo.equals("Nov")) {
                        mo = "11";
                    } else if(mo.equals("Dec")) {
                        mo = "12";
                    }
                    String da = str.substring(8, 10);
                    if(da.equals("01")) {
                        da = "1";
                    } else if(da.equals("02")) {
                        da = "2";
                    } else if(da.equals("03")) {
                        da = "3";
                    } else if(da.equals("04")) {
                        da = "4";
                    } else if(da.equals("05")) {
                        da = "5";
                    } else if(da.equals("06")) {
                        da = "6";
                    } else if(da.equals("07")) {
                        da = "7";
                    } else if(da.equals("08")) {
                        da = "8";
                    } else if(da.equals("09")) {
                        da = "9";
                    }
                    String getDay = ye + "년 " + mo + "월 " + da + "일";

                    list.add(getDay);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            test();
//            for(int i=0; i<list.size(); i++) {
//                Log.d("[LIST]", list.get(i));
//            }
        }
    }

//    private void test() {
//        for (int i = 0; i < list.size(); i++) {
//            Log.d("[LIST]", list.get(i));
//            RequestParams params = new RequestParams();
//            params.put("id", id);
//            params.put("watch", list.get(i));
//
//            Log.d("[ID]", id);
//            Log.d("[DAY]", list.get(i));
//
//            client.post(url, params, response);
//        }
//    }

    // 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return true;
    }
    // 옵션메뉴 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent (CalendarActivity.this, ReviewWriteActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        return true;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Intent intent = null;
        Date today = date.getDate();
        int yy = date.getYear();
        int mm = date.getMonth() + 1;
        int dd = date.getDay();

        String day = yy + "년 " + mm + "월 " + dd + "일";

        Log.d("[TEST]", "date = " + date);
        Log.d("[TEST]", "day = " + day);
        Log.d("[TEST]", "today = " + today);
        Log.d("[TEST]", "widget = " + widget);

        intent = new Intent(CalendarActivity.this, TicketActivity.class);
        // date를 꺼내서 쓸 때는 month에 +1을 해줘야 됨
        // D/[TEST]: date = CalendarDay{2020-10-18}
        // D/[TEST]: day = 2020년 11월 18일
        intent.putExtra("day", day);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_cal:       // 캘린더
//                intent = new Intent(this, CalendarActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "현재 위치입니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_info:      // 영화정보
                intent = new Intent(this, FilmActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
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

    class HttpResponse extends AsyncHttpResponseHandler {
//        ProgressDialog dialog;
//
//        @Override
//        public void onStart() {
//            dialog = new ProgressDialog(CalendarActivity.this);
//            dialog.setMessage("잠시만 기다려 주세요.");
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//
//        @Override
//        public void onFinish() {
//            dialog.dismiss();
//            dialog = null;
//        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                int watchCnt = json.getInt("watchCnt");

                if(rt.equals("OK")) {
                    Toast.makeText(CalendarActivity.this, "조회 성공",  Toast.LENGTH_SHORT).show();
                    Log.d("[TEST]", "watchCnt = " + watchCnt);
                    materialCalendarView.addDecorators(new EventDecorator(Color.RED));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(CalendarActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 100) {
//
//        }
//    }
}