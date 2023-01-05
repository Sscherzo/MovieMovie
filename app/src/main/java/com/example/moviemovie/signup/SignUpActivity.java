package com.example.moviemovie.signup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.moviemovie.LoginActivity;
import com.example.moviemovie.MainActivity;
import com.example.moviemovie.R;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    static final int SMS_SEND_PERMISSON = 1;
    static final int READ_PHONE_STATE = 2;

    String hashcode = null;

    Button buttonOverlap;  // 중복 체크
    Button buttonSend;  // 인증 번호 보내기
    Button buttonGet;  // 인증 번호 확인
    Button buttonSign;  // 회원 가입

    Button buttonView1;  // 보기 위에서부터 123
    Button buttonView2;
    Button buttonView3;

    EditText editTextId;  // 아이디
    EditText editTextPw;  // 비밀번호
    EditText editTextPwConfirm;  // 비밀번호 확인
    EditText editTextNickname;  // 닉네임
    EditText editTextName;  // 이름
    EditText editTextEmail;  // 이메일
    EditText editTextPhone;  // 휴대폰 번호
    EditText editTextPhone2;  // 인증번호

    CheckBox checkBoxAll;  // 모두 동의
    CheckBox checkBox1;  // 위에서 부터 123
    CheckBox checkBox2;
    CheckBox checkBox3;

    // 퍼미션
    int permissonCheck, permissonCheck1;

    AsyncHttpClient client;
    HttpResponse response;
    HttpResponseGenre responseGenre;

    // 내 집
    String url ="http://192.168.123.102:8082/moviemovie/member/member_write.do";

    // 집
    //String url = "http://192.168.0.165:8081/moviemovie/member/member_write.do";
    // 학원
    // String url = "http://192.168.1.69:8081/moviemovie/member/member_write.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        client = new AsyncHttpClient();
        response = new HttpResponse();
        responseGenre = new HttpResponseGenre();

        // 퍼미션
        permissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        permissonCheck1= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        // 체크 박스
        checkBoxAll = findViewById(R.id.checkBoxAll);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        // EditText
        editTextId = findViewById(R.id.editTextId);
        editTextPw = findViewById(R.id.editTextPw);
        editTextPwConfirm = findViewById(R.id.editTextPwConfirm);
        editTextNickname = findViewById(R.id.editTextNickname);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPhone2 = findViewById(R.id.editTextPhone2);

        // 버튼
        buttonOverlap = findViewById(R.id.buttonOverlap);
        buttonSend = findViewById(R.id.buttonSend);
        buttonGet = findViewById(R.id.buttonGet);
        buttonSign = findViewById(R.id.buttonSign);
        buttonView1 = findViewById(R.id.buttonView1);
        buttonView2 = findViewById(R.id.buttonView2);
        buttonView3 = findViewById(R.id.buttonView3);

        // onclick
        buttonOverlap.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        buttonGet.setOnClickListener(this);
        buttonSign.setOnClickListener(this);

        buttonView1.setOnClickListener(this);
        buttonView2.setOnClickListener(this);
        buttonView3.setOnClickListener(this);

        // onCheck
        checkBoxAll.setOnCheckedChangeListener(this);
        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);

        buttonSign.setEnabled(false);
        ///////////////////////
        Intent passedIntent = getIntent();
        processIntent(passedIntent);

        permissonCheck();//퍼미션 체크
    }

    private void processIntent(Intent intent){
        if(intent != null){
            // 인텐트에서 전달된 데이터를 추출하여, 활용한다.(여기서는 edittext를 통하여 내용을 화면에 뿌려주었다.)
            String string = intent.getStringExtra("string");
            editTextPhone2.setText(string);
        }
    }

    // (2) 이미 실행된 상태였는데 리시버에 의해 다시 켜진 경우
    // (이러한 경우 onCreate()를 거치지 않기 때문에 이렇게 오버라이드 해주어야 모든 경우에 SMS문자가 처리된다!
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.buttonOverlap:
                String checkID = editTextId.getText().toString().trim();
                if(checkID.equals("")) {
                    Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(this, CheckIDActivity.class);
                    intent.putExtra("checkID", checkID);
                    startActivityForResult(intent, 400);
                }
                break;
            case R.id.buttonSend:
                if(!editTextPhone.getText().toString().equals("")) {
                    HintRequest hintRequest = new HintRequest.Builder()
                            .setPhoneNumberIdentifierSupported(true)
                            .build();
                    hashcode = Integer.toString(hintRequest.hashCode()).substring(0, 4);
                    //String phone ="(650)5551212";
                    String phone = editTextPhone.getText().toString().trim();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, hashcode, null, null);
                    Toast.makeText(this,"인증 번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonGet:
                if(editTextPhone2.getText().toString().equals(hashcode)){
                    Toast.makeText(this,"인증 성공", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"인증 실패", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonSign:
                String id = editTextId.getText().toString().trim();
                String pw = editTextPw.getText().toString().trim();
                String pwConfirm = editTextPwConfirm.getText().toString().trim();
                String nickname = editTextNickname.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String tel = editTextPhone.getText().toString().trim();

                if (id.equals("moviemovie")) {
                    Toast.makeText(this, "사용할 수 없는 아이디 입니다", Toast.LENGTH_SHORT).show();
                } else if(!pw.equals(pwConfirm)){
                    Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams params = new RequestParams();
                    params.put("id", id);
                    params.put("pw", pw);
                    params.put("nickname", nickname);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("tel", tel);

                    client.post(url, params, response);

                    intent = new Intent(this, GenreActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                break;
            case R.id.buttonView1:
                intent = new Intent(this,TermsList1.class);
                startActivityForResult(intent,100);
                break;
            case R.id.buttonView2:
                intent = new Intent(this,TermsList2.class);
                startActivityForResult(intent,200);
                break;
            case R.id.buttonView3:
                intent = new Intent(this,TermsList3.class);
                startActivityForResult(intent,300);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkBoxAll:
                if(checkBoxAll.isChecked()){
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                } else {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
                break;
        }
        if(checkBox1.isChecked() && checkBox2.isChecked()){
            buttonSign.setEnabled(true);
        } else {
            buttonSign.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                boolean check = data.getBooleanExtra("x",false);
                checkBox1.setChecked(check);
            } else {   // RESULT_CANCEL
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                boolean check = data.getBooleanExtra("x",false);
                checkBox2.setChecked(check);
            } else {   // RESULT_CANCEL
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                boolean check = data.getBooleanExtra("x",false);
                checkBox3.setChecked(check);
            } else {   // RESULT_CANCEL
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                String checkID = data.getStringExtra("checkID");
                editTextId.setText(checkID);
            } else {
                editTextId.setText("");
            }
        }
    }

    private void permissonCheck() {
        //문자 보내기 퍼미션
        if(permissonCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS 송신 권한 있음", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "SMS 송신 권한 없음", Toast.LENGTH_SHORT).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                Toast.makeText(getApplicationContext(), "SMS 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS},  SMS_SEND_PERMISSON);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSON);
            }
        }

        //READ_PHONE_STATE
        if(permissonCheck1 == PackageManager.PERMISSION_GRANTED){
        }else{

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)){
                Toast.makeText(getApplicationContext(), "SMS 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
            }
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
                    log.v("A","conn1");
                    connGenre();
                    log.v("A","conn2");
                    finish();  // 이전 화면으로 돌아가기

                } else {
                    Toast.makeText(SignUpActivity.this,"회원 가입 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void connGenre() {
            // 내 집
            String url ="http://192.168.123.102:8082/moviemovie/genre/genreWrite.do";
            // 집
            //String url = "http://192.168.0.165:8081/moviemovie/genre/genreWrite.do";
            // 학원
            // String url = "http://192.168.1.69:8081/moviemovie/genre/genreWrite.do";
            String id = editTextId.getText().toString().trim();
            RequestParams params = new RequestParams();
            params.put("id",id);
            client.post(url, params, responseGenre);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(SignUpActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
            log.v(String.valueOf(statusCode),"에러 로그");
        }
    }

    class HttpResponseGenre extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("OK")) {
                    //Toast.makeText(SignUpActivity.this,"장르 저장 성공", Toast.LENGTH_SHORT).show();
                    finish();  // 이전 화면으로 돌아가기
                } else {
                   // Toast.makeText(SignUpActivity.this,"장르 저장 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(SignUpActivity.this,"통신 실패", Toast.LENGTH_SHORT).show();
        }
    }
}