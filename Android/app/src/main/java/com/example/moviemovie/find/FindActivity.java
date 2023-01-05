package com.example.moviemovie.find;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.moviemovie.R;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_findID, button_findPW, button_yes_id, button_no_id, button_yes_pw, button_no_pw;
    LinearLayout linearLayout_findID, linearLayout_findPW;
    EditText editText_findID_name, editText_findID_tel, editText_findPW_name, editText_findPW_id, editText_findPW_tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        button_findID = findViewById(R.id.button_findID);
        button_yes_id = findViewById(R.id.button_yes_id);
        button_no_id = findViewById(R.id.button_no_id);
        button_findPW = findViewById(R.id.button_findPW);
        button_yes_pw = findViewById(R.id.button_yes_pw);
        button_no_pw = findViewById(R.id.button_no_pw);
        linearLayout_findID = findViewById(R.id.linearLayout_findID);
        linearLayout_findPW = findViewById(R.id.linearLayout_findPW);
        editText_findID_name = findViewById(R.id.editText_findID_name);
        editText_findID_tel = findViewById(R.id.editText_findID_tel);
        editText_findPW_name = findViewById(R.id.editText_findPW_name);
        editText_findPW_id = findViewById(R.id.editText_findPW_id);
        editText_findPW_tel = findViewById(R.id.editText_findPW_tel);

        // 이벤트 설정
        button_findID.setOnClickListener(this);
        button_yes_id.setOnClickListener(this);
        button_no_id.setOnClickListener(this);
        button_findPW.setOnClickListener(this);
        button_yes_pw.setOnClickListener(this);
        button_no_pw.setOnClickListener(this);

        linearLayout_findID.setVisibility(View.VISIBLE);
        linearLayout_findPW.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        String name, tel, id;
        switch (v.getId()) {
            case R.id.button_findID:
                linearLayout_findID.setVisibility(View.VISIBLE);
                linearLayout_findPW.setVisibility(View.GONE);
                break;
            case R.id.button_yes_id:
                name = editText_findID_name.getText().toString().trim();
                tel = editText_findID_tel.getText().toString().trim();
                if(name.equals("") || tel.equals("")) {
                    Toast.makeText(this, "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent = new Intent(this, FindIDActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("tel", tel);
                    startActivity(intent);
                }
                break;
            case R.id.button_no_id:
                finish();
                break;
            case R.id.button_findPW:
                linearLayout_findID.setVisibility(View.GONE);
                linearLayout_findPW.setVisibility(View.VISIBLE);
                break;
            case R.id.button_yes_pw:
                name = editText_findPW_name.getText().toString().trim();
                id = editText_findPW_id.getText().toString().trim();
                tel = editText_findPW_tel.getText().toString().trim();
                if(name.equals("") || id.equals("") || tel.equals("")) {
                    Toast.makeText(this, "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    intent = new Intent(this, FindPWActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("id", id);
                    intent.putExtra("tel", tel);
                    startActivity(intent);
                }
                break;
            case R.id.button_no_pw:
                finish();
                break;
        }
    }
}