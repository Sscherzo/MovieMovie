package com.example.moviemovie.modify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviemovie.R;
import com.example.moviemovie.SettingActivity;
import com.example.moviemovie.helper.FileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.moviemovie.LoginActivity.editor;
import static com.example.moviemovie.LoginActivity.pref;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView circleImageView;
    Button button1,button2;
    EditText editText1,editText2,editText3,editText4,editText5;
    String id,pw,nickName,email,img,tel=null;
    AsyncHttpClient client;
    HttpResponse response;

    String url ="http://192.168.123.102:8082/moviemovie/member/member_modify.do";
    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/member/member_modify.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/member_modify.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        client = new AsyncHttpClient();
        response = new HttpResponse();
        circleImageView = findViewById(R.id.circleImageView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        circleImageView.setOnClickListener(this);

        //받은 값으로 editText에 값 넣기
        id = pref.getString("id",null);
        pw = pref.getString("pw",null);
        nickName = pref.getString("nickName",null);
        email = pref.getString("email",null);
        tel = pref.getString("tel",null);
        img = pref.getString("img",img);

        editText1.setText(id);
        editText2.setText(pw);
        editText3.setText(nickName);
        editText4.setText(email);
        editText5.setText(tel);

        editText1.setEnabled(false);
        editText2.setEnabled(false);

        if(img != null && !img.equals("X")){
            File imgFile = new File(img);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            circleImageView.setImageBitmap(myBitmap);
        } else {
            Glide.with(this).load(R.drawable.moviemovie_profile).into(circleImageView);
        }
        permissionCheck();
    }

    private void permissionCheck() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                RequestParams params = new RequestParams();
                params.put("id",id);
                params.put("pw",pw);
                params.put("nickname",editText3.getText().toString().trim());
                params.put("email",editText4.getText().toString().trim());
                params.put("tel",editText5.getText().toString().trim());

                if(img == null) {
                    params.put("img","X");
                }else {
                    params.put("img", img);
                }

                editor.commit();
                client.post(url, params, response);
                break;
            case R.id.button2:
                finish();
                break;
            case R.id.circleImageView:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // 이미지 파일만 필터링 => MIME 형태
                intent.setType("image/*");
                // 구글 클라우드에 싱크된 이미지 파일 제외
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                // 선택된 파일을 돌려받아야 함
                startActivityForResult(intent, 200);
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
                    Toast.makeText(ModifyActivity.this,"수정 성공", Toast.LENGTH_SHORT).show();
                    editor.putString("nickName",editText3.getText().toString().trim());
                    editor.putString("email",editText4.getText().toString().trim());
                    editor.putString("tel",editText5.getText().toString().trim());
                    editor.commit();
                    Intent intent = new Intent(ModifyActivity.this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ModifyActivity.this,"수정 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ModifyActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200) {
            if(resultCode == RESULT_OK) {
                // 사용자가 선택한 파일 정보
                Uri photoUri = data.getData();
                // photoUri = content://com.android.providers.media.documents/document/image%3A41
                Log.d("[TEST]", "photoUri = " + photoUri);
                // 절대 경로로 변환
                String filePath = FileUtils.getPath(this, photoUri);
                Log.d("[TEST]", "filePath = " + filePath);

                circleImageView.setImageURI(photoUri);
                img = filePath;
                editor.putString("img",img);

            }
        }
    }
}