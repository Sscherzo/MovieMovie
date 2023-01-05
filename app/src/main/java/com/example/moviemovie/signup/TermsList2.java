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

public class TermsList2 extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener {
    ListView listView;
    TermsAdapter adapter;
    List<Terms> list;
    View buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list2);

        buttonList = findViewById(R.id.buttonList);

        listView = this.findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new TermsAdapter(this,R.layout.activity_terms, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        buttonList.setVisibility(View.GONE);
        buttonList.setOnClickListener(this);
        addData();
    }

    private void addData() {
        adapter.add(new Terms(
                "주식회사 무비무비(이하 ‘회사’라고 합니다)는 무비무비(MOVIE MOVIE) 및 무비무비 관련 제반 서비스(이하 “서비스”라고 합니다)를 이용하는 회원의 개인 정보 보호를 소중하게 생각하고, 회원의 개인 정보를 보호하기 위하여 항상 최선을 다해 노력하고 있습니다.\n" +
                        "회사는 개인 정보 보호 관련 주요 법률인 개인 정보 보호법, 정보통신망 이용 촉진 및 정보보호 등에 관한 법률(이하 “정보통신망법”이라고 합니다)을 비롯한 모든 개인 정보 보호에 관련 법률 규정 및 국가기관 등이 제정한 고시, 훈령, 지침 등을 준수합니다. 본 개인정보처리방침은 회사의 서비스를 이용하는 회원에 대하여 적용되며, 회원이 제공하는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며, 개인 정보 보호를 위하여 회사가 어떠한 조치를 취하고 있는지 알립니다.\n" +
                        "\n" +
                        "정보통신망법 규정에 따라 회원 가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용 목적, 개인정보의 보유 및 이용 기간을 안내드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.\n" +
                        "\n" +
                        "개인정보의 수집범위 및 수집방법\n" +
                        "회원 가입, 상담, 서비스 신청 등 서비스 제공 및 계약이행을 위해 회원 가입 시점에 회사가 회원으로부터 수집하는 필수 개인정보는 아래와 같습니다.\n" +
                        "- 아이디(이메일 주소), 비밀번호, 페이스북 이메일과 회원 번호(페이스북 연동 회원에 한함), 만약 회원의 생년월일이 만14세 미만 아동일 경우에는 법정대리인 정보(법정대리인의 이름, 생년월일, 성별, 중복가입확인정보(DI), 휴대전화번호)를 추가로 수집합니다.\n" +
                        "\n" +
                        "서비스 이용과정에서 아래와 같은 정보들이 생성되어 수집될 수 있습니다.\n" +
                        "회사는 다음과 같은 방식으로 개인정보를 수집합니다.\n" +
                        "(1) 홈페이지 이용 및 서면양식, 경품 행사 응모, 배송요청\n" +
                        "(2) 제휴 회사로부터의 제공\n" +
                        "(3) 생성정보 수집툴을 통한 수집\n" +
                        "(4) 고객센터를 통한 상담 과정에서 웹페이지, 메일, 팩스, 전화 등을 통한 수집\n" +
                        "(5) 기기정보와 같은 생성정보는 PC웹, 모바일 웹/앱 이용 과정에서 자동으로 생성되어 수집될 수 있습니다.\n" +
                        "1. PC : PC MacAddress, PC 사양정보, 브라우저 정보, 기타 서비스 이용 시 사용되는 프로그램 버전 정보\n" +
                        "2. 휴대전화(스마트폰) & 스마트OS 탑재 모바일 기기(Tablet PC 등) : 모델명, 기기별 고유번호(UDID,IMEI 등), OS정보, 이동통신사, 구글/애플 광고 ID\n" +
                        "3. 기타 정보 : 서비스 이용(정지) 기록, 접속 로그, 쿠키, 접속 IP정보\n" +
                        "4. 회사는 회원이 유료 서비스를 이용하고자 하는 경우 결제 시점에 아래와 같이 결제에 필요한 정보를 수집할 수 있습니다.\n" +
                        "- 결제수단 소유자 정보(이름), 카드정보, 휴대전화번호, 유선전화번호\n" +
                        "5. 회사는 회원이 이벤트, 프로모션에 참여하는 경우 아래의 정보를 수집할 수 있습니다.\n" +
                        "- 이름, 이메일 주소, 휴대전화번호, 주소, 생년월일\n" +
                        "6. 연령에 따른 콘텐츠 및 상품의 이용/구매 제한, 서비스 부정 이용 방지를 위해 본인인증이 진행되며 이름, 생년월일, 성별, 내/외국인 여부, 아이핀 번호(아이핀 사용자의 경우), 휴대전화번호, 연계정보(CI), 중복확인정보(DI)가 수집될 수 있습니다.\n" +
                        "7. 현금 환불(필요시)을 위해 예금주명, 계좌은행명, 계좌번호, 관계증명서류(필요시)가 수집될 수 있습니다.\n" +
                        "회사는 기본적 인권침해의 우려가 있는 개인정보(인종 및 민족, 사상 및 신조, 출신지 및 본적지, 정치적 성향 및 범죄기록, 건강상태 및 성생활 등)를 요구하지 않으며, 위의 항목 이외에 다른 어떠한 목적으로도 수집, 사용하지 않습니다.\n" +
                        "\n" +
                        "개인 정보의 수집 목적 및 이용 목적\n" +
                        "회사는 수집한 개인정보를 다음의 목적으로 활용합니다.\n" +
                        "1. 서비스 제공에 관한 계약 이행 유료 서비스 제공에 따른 요금 정산\n" +
                        "콘텐츠 제공, 유료 서비스 이용에 대한 과금, 구매 및 요금 결제, 본인 인증, 물품 배송 또는 청구서 등 발송, 요금 추심\n" +
                        "2. 회원 관리\n" +
                        "회원제 서비스 이용에 따른 본인 확인, 개인식별, 불량회원의 부정 이용 방지와 비인가 사용 방지, 중복가입확인, 가입 의사 확인, 연령확인, 만 14세 미만 아동 개인정보 수집 시 법정대리인 동의 여부 확인 및 본인 확인, 분쟁 조정을 위한 기록보존, 불만 처리 등 민원처리, 고지사항 전달\n" +
                        "3. 마케팅 및 광고에 활용\n" +
                        "신규 서비스 개발 및 맞춤 서비스 제공, 통계학적 특성에 따른 서비스 제공 및 광고 게재, 서비스 유효성 확인, 접속 빈도 파악, 회원의 서비스 이용에 대한 통계, 이벤트 및 광고성 정보와 참여 기회 제공\n" +
                        "\n" +
                        "개인정보의 파기절차 및 방법\n" +
                        "회사는 수집한 개인정보의 이용 목적이 달성된 후 별도의 DB로 옮겨 보관 기간 및 이용 기간에 따라 해당 정보를 지체 없이 파기합니다. 파기 절차 및 방법은 다음과 같습니다.\n" +
                        "1. 파기 절차\n" +
                        "회원이 서비스 가입 등을 위해 기재한 개인 정보는 서비스 해지 등 이용 목적이 달성된 후 내부 방침 및 기타 관련 법령에 의한 정보 보호 사유에 따라 일정 기간(개인 정보 보관 기간 및 이용 기간 참조) 동안 저장 보관된 후 삭제하거나 파기합니다.\n" +
                        "2. 파기 방법\n" +
                        "서면 양식에 기재하거나, 종이에 출력된 개인 정보는 분쇄기로 분쇄하여 파기하고, 전자적 파일 형태로 저장된 개인 정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.\n" +
                        "\n" +
                        "전자상거래 등에서의 소비자 보호에 관한 법률, 전자금융거래법, 통신비밀보호법 등 법령에서 일정 기간 정보의 보관을 규정하는 경우는 아래와 같습니다. 이 기간 동안 법령의 규정에 따라 개인 정보를 보관하며, 본 정보를 다른 목적으로는 절대 이용하지 않습니다.\n" +
                        "가. 계약 또는 청약철회 등에 관한 기록 : 5년\n" +
                        "나. 대금결제 및 재화의 공급에 관한 기록 : 5년\n" +
                        "다. 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년\n" +
                        "라. 표시, 광고에 관한 기록 : 1년\n" +
                        "마. 웹사이트 방문기록 : 1년"));
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            buttonList.setVisibility(View.VISIBLE);
        }else{
            buttonList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}