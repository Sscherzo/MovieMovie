-- 회원 관리 테이블
create table member (
    seq number not null,
    id varchar2(30) not null,
    pw varchar2(30) not null,
    nickname varchar2(30) not null,
    name varchar2(30) not null,
    email varchar2(30) not null,
    tel varchar2(30) not null,
    img varchar2(200),
    logtime date,
    primary key(id)
);

-- 테이블 구조 확인
desc member;

-- 테이블 삭제
drop table member purge;

-- 테이블 목록
select * from tab;

-- 시퀀스 객체 생성
create sequence seq_mem nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_mem;

-- 데이터 추가
insert into member values (seq_mem.nextval, 'hong', '1234', '홍홍', '홍길동',
        'hong@naver.com', '01012345678', 'X', sysdate);

-- 데이터 검색
select * from member;
select * from member where name='홍길동';
select * from member where id='hong';
select * from member where id='hong' and pw='1234';
select * from member where name='홍길동' and tel='01012345678';
select id from member where name='홍길동' and tel='01012345678';
select pw from member where id='hong' and name='홍길동' and tel='01012345678';

-- 데이터 수정
update member set pw='4321' where id='hong';
update member set img='X' where id='kim';
update member set nickname='seok' where id='seok';
update member set tel='01013572468' where id='choi';
update member set pw='4321' where id='hong';
update member set nickname='우디' where id='yoon';
update member set nickname='랏쏘' where id='kim';
update member set nickname='버즈' where id='seok';
update member set nickname='알린' where id='choi';
update member set
    nickname='홍동',
    name='홍길동',
    email='dong@naver.com',
    tel='01056781234',
    img ='test'
    where id='hong' and pw='1234';

-- 데이터 삭제
delete member where seq='12' and pw='1234';

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------

-- 회원 파일 관리 테이블
create table memberfiles (
    seq number not null,               -- 일련번호
    mem_seq number not null,           -- 멤버 번호
    dir varchar2(256) not null,        -- 저장 폴더 이름
    filename varchar2(256) not null,   -- 파일 이름
    originname varchar2(256) not null, -- 원 파일 이름
    filesize number not null,          -- 파일 크기
    filetype varchar2(50) not null,    -- 파일 형식
    reg_date date,                     -- 등록일
    primary key(seq)
);

-- 테이블 삭제
drop table memberfiles purge;

-- 시퀀스 객체 생성
create sequence seq_memfiles nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_memfiles;

-- insert
insert into memberfiles values (
    seq_memfiles.nextval, 1, 'storage', 'img1.jpg', 'img1.jpg',
    1234, 'jpg', sysdate
);

-- delete
delete from memberfiles where mem_seq=1;

-- select
select * from memberfiles;
select * from memberfiles where mem_seq=1;

-- 테이블의 마지막 저장된 seq값 얻어 오기
select seq from (select rownum rn, tt.* from
(select * from memberfiles order by seq desc) tt)
where rn=1;

select max(seq) as seq from memberfiles;

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------

-- 리뷰 테이블
create table review (
    seq number not null,                -- 일렬번호
    rate number(5,2) not null,          -- 별점
    title varchar2(150) not null,       -- 제목
    director varchar2(150) not null,    -- 감독
    playdate varchar2(150) not null,    -- 개봉일
    runtime varchar2(150) not null,     -- 상영시간
    genre varchar2(150) not null,       -- 장르
    actor varchar2(150) not null,       -- 배우
    plot varchar2(4000) not null,       -- 줄거리
    watch varchar2(150) not null,       -- 관람일
    place varchar2(150) not null,       -- 관람 장소
    review varchar2(4000) not null,     -- 나의 리뷰
    reg_date date not null,             -- 등록 일시
    edit_date date not null,            -- 수정 일시
    id varchar2(30) not null,           -- 작성자
    primary key(seq)
);

-- 테이블 삭제
drop table review purge;

-- 테이블 구조
desc review;

-- 테이블 확인
select * from tab;

-- 시퀀스 객체 생성
create sequence seq_review nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_review;

-- insert
insert into review values 
(seq_review.nextval, 5, '해리 포터와 마법사의 돌', '크리스 콜럼버스', '2001년 12월 14일', '120', '판타지', '다니엘 래드클리프|루퍼트 그린트|엠마 왓슨', '해리포터 줄거리', '2020년 11월 24일', '집', '인생 영화 해리 포터', sysdate, sysdate, 'test');
 
-- select
select * from review;
select * from review where id='test';
select * from review where seq=2 and id='hong';
select * from review where id='lee' order by seq desc;
select count(*) as cnt from review where id='park';
select count(*) as cnt from review where id='park' and watch    ='2020년 12월 3일';
select * from review where id='park' and watch='2020년 12월 3일';
 
-- update
update review set
    rate=2,
    watch='2020년 11월 25일',
    place='학원',
    review='너무 재밌다 해리 포터',
    edit_date=sysdate
where seq=2 and id='hong';
 
-- delete
delete from review where seq=1 and id='hong';

-- review 테이블의 마지막 저장된 seq 값 얻어오기
select max(seq) as seq from review where id='hong';

-- 총 글 수
select count(*) as cnt from review where id='hong';

-- runtime 합계
select sum(runtime) from review where id='hong';

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------

-- 이미지 URL 관리 테이블
create table reviewurl (
    seq number not null,                -- 일렬번호
    review_seq number not null,         -- review 글 번호
    imageUrl varchar2(1000) not null,   -- 이미지 URL
    reg_date date,                      -- 등록일
    primary key(seq)
);

-- 테이블 삭제
drop table reviewurl purge;

-- 시퀀스 객체 생성
create sequence seq_reviewurl nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_reviewurl;

-- insert
insert into reviewurl values 
(seq_reviewurl.nextval, 1, 'storage', sysdate);

-- delete
delete from reviewurl where review_seq=1;

-- select
select * from reviewurl;
select * from reviewurl where review_seq=1;
select * from reviewurl order by seq desc;

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------

-- 리뷰 파일 관리 테이블
create table reviewfiles (
    seq number not null,                -- 일렬번호
    review_seq number not null,         -- review 글 번호
    dir varchar2(256) not null,         -- 저장 폴더 이름
    filename varchar2(256) not null,    -- 파일 이름
    originname varchar2(256) not null,  -- 원래 파일 이름
    filesize number not null,           -- 파일 크기
    filetype varchar2(50) not null,     -- 파일 형식
    reg_date date,                      -- 등록일
    primary key(seq)
);

-- 테이블 삭제
drop table reviewfiles purge;

-- 시퀀스 객체 생성
create sequence seq_reviewfiles nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_reviewfiles;

-- insert
insert into reviewfiles values 
(seq_reviewfiles.nextval, 1, 'storage', 'img1.jpg', 'img1.jpg',
1234, 'jpg', sysdate);

-- delete
delete from reviewfiles where review_seq=1;

-- select
select * from reviewfiles;
select * from reviewfiles where review_seq=1;
select * from reviewfiles order by seq desc;

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------

-- 관리자모드 접속 id : moviemovie, pw : movie!
-- faq 테이블
create table faq (
    faq_no varchar2(30) primary key,
    faq_subject varchar2(100) not null,
    faq_content varchar2(2000) not null
);

-- 테이블 구조 확인
desc faq;

-- 테이블 삭제
drop table faq purge;

-- 테이블 목록
select * from tab;

-- insert
insert into faq values (
    '1', '아이디/비밀번호가 기억나지 않아요.', '무비무비 로그인 페이지에서 제공되는 아이디/비밀번호 찾기를 이용해 아이디나 비밀번호를 찾으실 수 있습니다.'
);

insert into faq values (
    '2', '아이디를 바꾸고 싶은데 수정할 수 있나요?', '아이디 변경은 탈퇴 후 재가입(신규가입)을 통해 새로운 아이디로 변경하실 수 있습니다.
탈퇴 후 언제든지 재가입 가능합니다. '
);

insert into faq values (
    '3', '내가 본 영화 등록이 안되어 있어요. 어떻게 하나요?', '무비무비 정보 입력을 요청했으나 내가 본 영화에 표기되지 않는 경우,
무비무비 고객센터(1544-1687)로 문의주시면 즉시 확인 후 안내드리겠습니다.'
);

insert into faq values (
    '4', '약관동의가 제대로 되지 않아요. 어떻게 하나요?', '어플리케이션 내에서 약관동의가 정상적으로 처리되지 않는 경우
무비 무비 고객센터(1544-1687)로 문의주시면 즉시 확인 후 안내드리겠습니다.'
);

insert into faq values (
    '5', '아이디, 비밀번호를 잊었어요.', '무비무비 로그인 페이지에서 제공되는 아이디/비밀번호 찾기를 이용해 아이디나 비밀번호를 찾으실 수 있습니다.'
);

insert into faq values (
    '6', '개명으로 인한 이름 변경은 어디에서 하나요?', '무비무비 로그인 후 마이페이지->프로필 수정에서 변경하실 수 있습니다.'
);

insert into faq values (
    '7', '무비무비 앱에서는 어떤 서비스를 이용할 수 있나요?', '회원 가입 시 입력한 아이디는 변경이 불가하며, 탈퇴 후 재가입을 통해 새로운 아이디로 지정할 수 있습니다.'
);

insert into faq values (
    '8', '아이디를 바꾸고 싶은데 수정할 수 있나요?', '영화 검색,일일 박스 오피스 확인 기능들을 제공하고 드리고 있으나
일부 서비스는 이용에 제한이 있을 수 있으니,
다양한 서비스 이용을 원하실 경우, 영화사 APP을 이용 부탁드립니다.'
);

-- select
select * from faq order by faq_no asc;

-- delete
delete from faq where faq_no=0;

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------

-- notice 테이블
create table notice (
    seq number not null,
    notice_subject varchar2(100) not null,
    notice_content varchar2(2000) not null,
    notice_date date,
    modify_date date,
    primary key(seq)
);

-- 테이블 구조 확인
desc notice;

-- 테이블 삭제
drop table notice purge;

-- 테이블 목록
select * from tab;

-- 시퀀스 객체 생성
create sequence seq_notice nocache nocycle;

-- 시퀀스 객체 삭제
drop sequence seq_notice;

-- insert
insert into notice values (
    seq_notice.nextval, '코로나19와 관련하여 고객 센터 전화 상담 임시 중단 안내', 'ㅇㅇ', sysdate, sysdate
);

-- delete
delete from notice where seq=1;

-- update
update notice set
    notice_subject='공지 제목 수정',
    notice_content='공지 내용 수정',
    modify_date=sysdate
    where seq='1';

-- select
select * from notice order by seq asc;

-- DB 저장
commit;

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------

create table genre(
    id varchar(30),
    fantasy number,
    horror number,
    mellow number,
    kid number,
    drama number,
    comedy number,
    crime number,
    character number,
    musical number,
    action number,
    sf number,
    war number,
    etc number,
    CONSTRAINT MEMBER_KEY  foreign key (id) REFERENCES member(id)  ON DELETE CASCADE   
);

insert into genre values('hong',0,0,0,0,0,0,0,0,0,0,0,0,0);
select * from genre;
select * from genre where id='k';

update genre set fantasy=1,horror=2,mellow=3,kid=4,drama=5,comedy=6,crime=7,
character=8,musical=9,action=10,sf=11,war=12,etc =0 where id='kim';

delete from genre where id='kim';
drop table genre purge;

-- 외래키 삭제
alter table genre drop constraint id;

commit;