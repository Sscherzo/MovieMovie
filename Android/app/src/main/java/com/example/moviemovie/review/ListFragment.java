package com.example.moviemovie.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.example.moviemovie.review.adapter.ListAdapter;
import com.example.moviemovie.review.model.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.pref;

public class ListFragment extends Fragment {
    ArrayList<Review> list;
    ListAdapter adapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    AsyncHttpClient client;
    HttpResponse response;
    String id = pref.getString("id",null);


    String url ="http://192.168.123.102:8082/moviemovie/review/review_list.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/review/review_list.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/review/review_list.do";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        adapter = new ListAdapter(getContext(), R.layout.fragment_list, list);
        client =  new AsyncHttpClient();
        response = new HttpResponse();
        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getReview();

        return view;
    }

    private void getReview() {
        recyclerView.setAdapter(adapter);
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(url, params, response);
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
                            adapter.addItem(review);
                            Log.d("[TEST]", "HttpResponse 호출");
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//            Toast.makeText(getContext(), "통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}