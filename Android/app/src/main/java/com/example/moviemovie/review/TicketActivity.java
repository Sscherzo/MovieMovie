package com.example.moviemovie.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.review.adapter.TicketAdapter;
import com.example.moviemovie.review.model.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class TicketActivity extends AppCompatActivity {
    ArrayList<Review> list;
    TicketAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AsyncHttpClient client;
    HttpResponse response;
    Review item;
    String id = pref.getString("id",null);
    String day;


    String url ="http://192.168.123.102:8082/moviemovie/review/review_watchlist.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_watchlist.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_watchlist.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        list = new ArrayList<>();
        adapter = new TicketAdapter(this, list, this);
        recyclerView = findViewById(R.id.recyclerView);
        client =  new AsyncHttpClient();
        response = new HttpResponse();
        day = getIntent().getStringExtra("day");

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemLongClickListener(new TicketAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                item = adapter.getItem(pos);
                showListDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("watch", day);
        client.post(url, params, response);
    }

    private void showListDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        final String[] items = {"수정하기", "삭제하기", "자세히보기"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:                 // 수정
                        intent = new Intent(TicketActivity.this, ReviewModifyActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                        break;
                    case 1:                 // 삭제
                        intent = new Intent(TicketActivity.this, ReviewDeleteActivity.class);
                        intent.putExtra("seq", item.getSeq());
                        intent.putExtra("title", item.getTitle());
                        startActivity(intent);
                        break;
                    case 2:                 // 상세
                        intent = new Intent(TicketActivity.this, ReviewDetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                        break;
                }
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                int total  = json.getInt("total");

                if(rt.equals("OK")) {
                    if (total > 0) {
                        JSONArray item = json.getJSONArray("item");
                        for (int i = 0; i < item.length(); i++) {
                            JSONObject temp = item.getJSONObject(i);
                            Review review = new Review();
                            review.setSeq(temp.getInt("seq"));
                            review.setTitle(temp.getString("title"));
                            review.setRate((float) temp.getDouble("rate"));
                            review.setReview(temp.getString("review"));
                            review.setFilename(temp.optString("filename", null));
                            review.setImageUrl(temp.optString("imageUrl", null));

                            review.setDirector(temp.getString("director"));
                            review.setPlaydate(temp.getString("playdate"));
                            review.setRuntime(temp.getString("runtime"));
                            review.setGenre(temp.getString("genre"));
                            review.setActor(temp.getString("actor"));
                            review.setPlot(temp.getString("plot"));
                            review.setWatch(temp.getString("watch"));
                            review.setPlace(temp.getString("place"));
                            review.setId(temp.getString("id"));
                            adapter.addItem(review);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(TicketActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}