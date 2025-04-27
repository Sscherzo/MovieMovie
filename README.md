# MovieMovie
안드로이드 팀 프로젝트

# Description
팀 프로젝트 내에서 작성자가 한 부분만을 간략하게 설명하고 있습니다.

# Demonstration Video
[![Video Label](http://img.youtube.com/vi/8oJThbflak0/0.jpg)](https://youtu.be/8oJThbflak0) ← 옆의 사진을 클릭해주세요.

# Contents
1.[로딩화면](#로딩화면)

2.[SMS_발송](#sms_발송)

3.[이용약관](#이용약관)

4.[로그인_유지](#로그인_유지)

5.[영화_API](#영화_api)

# 로딩화면

![ezgif com-gif-maker](https://user-images.githubusercontent.com/90139096/210361313-ae459136-1a7e-4b6b-90d4-945a2aa35c3d.gif)

```java
 // -- 앱이 실행 될때 로딩화면을 전체화면으로 보여줍니다.
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading);

        startLoading();
    }

```
어플리케이션 시작시 로딩화면을 보여준 뒤
로그인 화면으로 전환합니다.

[:arrow_up: 목차로](#contents)

# SMS_발송

![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/90139096/210364015-0b7668d4-2af7-440d-9fc6-bf780081ee57.gif)

```java
 // -- 해쉬코드 생성
 hashcode = Integer.toString(hintRequest.hashCode()).substring(0, 4);
 
 // -- SmsManager
 SmsManager smsManager = SmsManager.getDefault();
 smsManager.sendTextMessage(phone, null, hashcode, null, null);

 // -- 해쉬코드 값 비교
 editTextPhone2.getText().toString().equals(hashcode)

```

 해쉬코드를 생성해 4자리로 잘라내고 SmsManger에서 해쉬코드를 담아 SMS를 보낸뒤
 입력값과 sms의 내용을 비교해서 인증을 완료합니다.

[:arrow_up: 목차로](#contents)

# 이용약관

![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/90139096/210372199-1bbfaae3-15b4-4651-9cac-3013185cf6e1.gif)

```java
listView.setAdapter(adapter);
listView.setOnScrollListener(this);

public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            buttonList.setVisibility(View.VISIBLE);
        }else{
            buttonList.setVisibility(View.GONE);
        }
    }
```
setAdapter를 통해 ListView와 만들어둔 Adapter를 연결합니다.
setOnScrollListener로 스크롤을 감지합니다.
스크롤을 멈추면 버튼이 보이게 한뒤,버튼을 클릭하면 체크박스가 체크 됩니다.

[:arrow_up: 목차로](#contents)

# 로그인_유지

![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/90139096/210376154-a14c7456-e251-4372-a977-d2c608b1e1a5.gif)

```java
// -- SharedPreferences 세팅
pref = getApplicationContext().getSharedPreferences("BuyyaPref",MODE_PRIVATE);
editor = pref.edit();

// -- 아이디 비밀번호 저장
  editor.putString("id",id);
  editor.putString("pw",pw);
  editor.commit();

// -- 로그아웃
  editor.clear();
  editor.commit();
```

SharedPreferences를 사용해 내부적으로 아이디와 패스워드를 저장하여
앱을 껐다 키더라도 로그인 상태가 유지 되도록 하였습니다.

[:arrow_up: 목차로](#contents)

# 영화_API

![ezgif com-gif-maker (5)](https://user-images.githubusercontent.com/90139096/210389067-7a4346f3-2749-4f77-85ee-7c8a13e6a7db.gif)


```java

AsyncHttpClient client;

// 박스오피스 가져오기
    public class FilmResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                JSONObject boxOfficeResult = json.getJSONObject("boxOfficeResult");
                JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");

                JSONObject temp = dailyBoxOfficeList.getJSONObject(9);
                box9 = new Box();
                box9.setRank(temp.getString("rank"));
                box9.setMovieNm(temp.getString("movieNm"));
                box9.setOpenDt("개봉일 : " + temp.getString("openDt"));
                box9.setAudiAcc("누적관객수 : " + temp.getString("audiAcc") + "명");
                
                String url9 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";
                RequestParams params9 = new RequestParams();
                params9.put("ServiceKey", ".......");
                params9.put("title", temp.getString("movieNm"));
                params9.put("sort", "title,0");
                params9.put("releaseDts", releaseDts);

                client.get(url9, params9, movieResponse9);
                
                

```

AsyncHttpClient를 사용, 비동기 통신을 통해 API를 사용하고 JSON으로 값을 가져왔습니다. 사용한 API는 영화진흥위원회 오픈 API와 한국영화데이터베이스 API를
사용했습니다. 서로 다른 API를 사용한 이유는 영화진흥 위원회는 박스 오피스나 개봉 예정작등을 알려주지만 영화의 Poster를 제공하지 않습니다. 따라서 한국영화데이터베이스에서 제공하는 API를 사용해 Poster를 가져왔습니다.


[:arrow_up: 목차로](#contents)
