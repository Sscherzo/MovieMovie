package com.example.moviemovie.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.moviemovie.R;
import com.example.moviemovie.signup.adapter.TermsAdapter;
import com.example.moviemovie.signup.model.Terms;

import java.util.ArrayList;
import java.util.List;

public class TermsList3 extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    TermsAdapter adapter;
    List<Terms> list;
    View buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list3);

        buttonList = findViewById(R.id.buttonList);
        listView = this.findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new TermsAdapter(this,R.layout.activity_terms, list);
        listView.setAdapter(adapter);
        buttonList.setVisibility(View.VISIBLE);
        buttonList.setOnClickListener(this);
        addData();
    }

    private void addData() {
        adapter.add(new Terms(
                "무비무비는 특별 프로모션 멤버십이나 타사가 자체 제품 및 서비스 공급과 연계하여 제공하는 멤버십 등 다양한 멤버십을 제공할 수 있습니다. 일부 멤버십에는 상이한 조건 및 제약이 있으며, 이는 가입 시 또는 회원이 사용할 수 있는 기타 커뮤니케이션을 통해 공개됩니다. \n" +
                        "\n" +
                        "신작, 인기, 추천 콘텐츠 안내 및 이벤트, 마케팅 정보 안내 등을 주 목적으로 하며 수신 동의 시 메일, 문자 메시지, 푸시 알림에 수신 동의 처리됩니다. 동의하지 않으셔도 서비스 이용이 가능하며 동의하신 이후에도 정보 수신 시 안내에 따라 수신 동의를 철회할 수 있습니다.\n" +
                        "\n" +
                        "회원 가입 시 수집된 개인 정보를 이용하여 탈퇴 시까지 각종 서비스, 상품 및 타사 서비스와 결합된 상품에 대하여 홍보, 가입 권유, 프로모션, 이벤트 안내 목적으로 본인에게 정보, 광고를 전화, DM, SMS, MMS, 이메일, 우편 등의 방법으로 전달합니다.\n" +
                        "\n" +
                        "정부의 스팸 메일 등 광고성 정보 전송 행위에 대한 규제 강화 정책 및 개인 정보에 관한 법률에 따라 고객 여러분께서는 당사의 서비스를 통한 광고성 팩스, 문자 발송 시 “정보통신망 이용 촉진 및 정보 보호 등에 관한 법률 및 시행령”을 반드시 준수하시어 불이익을 당하는 사례가 없도록 각별히 주의하시기 바랍니다."));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.buttonList:
                boolean x = true;
                Intent intent = new Intent();
                intent.putExtra("x",x);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}