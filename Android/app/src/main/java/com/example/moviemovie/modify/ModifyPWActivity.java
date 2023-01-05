package com.example.moviemovie.modify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.moviemovie.LoginActivity.editor;
import static com.example.moviemovie.LoginActivity.pref;

public class ModifyPWActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView_id;
    EditText editText_origin, editText_new;
    Button button_back, button_confirm;
    String id, pw;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/member/pw_modify.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/member/pw_modify.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/pw_modify.do";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_p_w);

        textView_id = findViewById(R.id.textView_id);
        editText_origin = findViewById(R.id.editText_origin);
        editText_new = findViewById(R.id.editText_new);
        button_back = findViewById(R.id.button_back);
        button_confirm = findViewById(R.id.button_confirm);
        client = new AsyncHttpClient();
        response = new HttpResponse();

        button_back.setOnClickListener(this);
        button_confirm.setOnClickListener(this);

        id = pref.getString("id",null);
        pw = pref.getString("pw",null);
        textView_id.setText(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_confirm:
                String pw_origin = editText_origin.getText().toString().trim();
                String pw_new = editText_new.getText().toString().trim();
                if(pw_new.equals("") || pw_origin.equals("")) {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if(!pw_origin.equals(pw)) {
                    Toast.makeText(this, "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams params = new RequestParams();
                    params.put("pw", pw_new);
                    params.put("id", id);
                    client.post(url, params, response);
                }
                break;
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);

            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");

                if(rt.equals("OK")) {
                    Toast.makeText(ModifyPWActivity.this,"비밀번호 수정 성공", Toast.LENGTH_SHORT).show();
                    editor.putString("pw",editText_new.getText().toString().trim());
                    editor.commit();
                    finish();
                } else {
                    Toast.makeText(ModifyPWActivity.this,"비밀번호 수정 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ModifyPWActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}