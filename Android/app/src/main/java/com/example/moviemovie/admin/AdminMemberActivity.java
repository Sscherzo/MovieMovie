package com.example.moviemovie.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.example.moviemovie.admin.adapter.AdminMemberAdapter;
import com.example.moviemovie.admin.model.AdminMember;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.editor;

public class AdminMemberActivity extends AppCompatActivity {
    List<AdminMember> list;
    AdminMemberAdapter adapter;
    ListView listViewAdmin;
    AsyncHttpClient client;
    HttpResponse response;
    TextView textView_admin;

    String url ="http://192.168.123.102:8082/moviemovie/member/member_list.do";
    // 집
    //String url ="http://192.168.0.165:8081/moviemovie/member/member_list.do";
    // 학원
    // String url ="http://192.168.1.69:8081/moviemovie/member/member_list.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_member);

        list = new ArrayList<>();
        adapter = new AdminMemberAdapter(this, R.layout.list_admin_member, list);
        listViewAdmin = findViewById(R.id.listViewAdmin);
        client = new AsyncHttpClient();
        response = new HttpResponse();
        textView_admin = findViewById(R.id.textView_admin);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_admem));
//        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.admin_textview);


        listViewAdmin.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();  // 기존 데이터 삭제
        client.post(url, response);
    }

    // 옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmember_menu, menu);
        return true;
    }
    // 옵션메뉴 클릭 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                int total = json.getInt("total");
                JSONArray item = json.getJSONArray("item");

                if(rt.equals("OK")) {
                    if(total > 0) {
                        for(int i=0; i<total; i++) {
                            JSONObject temp = item.getJSONObject(i);
                            AdminMember adminMember = new AdminMember();
                            adminMember.setSeq(temp.getInt("seq"));
                            adminMember.setId(temp.getString("id"));
                            adminMember.setPw(temp.getString("pw"));
                            adminMember.setNickname(temp.getString("nickName"));
                            adminMember.setName(temp.getString("name"));
                            adminMember.setEmail(temp.getString("email"));
                            adminMember.setTel(temp.getString("tel"));
                            adminMember.setImg(temp.getString("img"));

                            adapter.add(adminMember);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(AdminMemberActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}