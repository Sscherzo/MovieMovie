-- ȸ�� ���� ���̺�
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

-- ���̺� ���� Ȯ��
desc member;

-- ���̺� ����
drop table member purge;

-- ���̺� ���
select * from tab;

-- ������ ��ü ����
create sequence seq_mem nocache nocycle;

-- ������ ��ü ����
drop sequence seq_mem;

-- ������ �߰�
insert into member values (seq_mem.nextval, 'hong', '1234', 'ȫȫ', 'ȫ�浿',
        'hong@naver.com', '01012345678', 'X', sysdate);

-- ������ �˻�
select * from member;
select * from member where name='ȫ�浿';
select * from member where id='hong';
select * from member where id='hong' and pw='1234';
select * from member where name='ȫ�浿' and tel='01012345678';
select id from member where name='ȫ�浿' and tel='01012345678';
select pw from member where id='hong' and name='ȫ�浿' and tel='01012345678';

-- ������ ����
update member set pw='4321' where id='hong';
update member set img='X' where id='kim';
update member set nickname='seok' where id='seok';
update member set tel='01013572468' where id='choi';
update member set pw='4321' where id='hong';
update member set nickname='���' where id='yoon';
update member set nickname='����' where id='kim';
update member set nickname='����' where id='seok';
update member set nickname='�˸�' where id='choi';
update member set
    nickname='ȫ��',
    name='ȫ�浿',
    email='dong@naver.com',
    tel='01056781234',
    img ='test'
    where id='hong' and pw='1234';

-- ������ ����
delete member where seq='12' and pw='1234';

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------

-- ȸ�� ���� ���� ���̺�
create table memberfiles (
    seq number not null,               -- �Ϸù�ȣ
    mem_seq number not null,           -- ��� ��ȣ
    dir varchar2(256) not null,        -- ���� ���� �̸�
    filename varchar2(256) not null,   -- ���� �̸�
    originname varchar2(256) not null, -- �� ���� �̸�
    filesize number not null,          -- ���� ũ��
    filetype varchar2(50) not null,    -- ���� ����
    reg_date date,                     -- �����
    primary key(seq)
);

-- ���̺� ����
drop table memberfiles purge;

-- ������ ��ü ����
create sequence seq_memfiles nocache nocycle;

-- ������ ��ü ����
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

-- ���̺��� ������ ����� seq�� ��� ����
select seq from (select rownum rn, tt.* from
(select * from memberfiles order by seq desc) tt)
where rn=1;

select max(seq) as seq from memberfiles;

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------

-- ���� ���̺�
create table review (
    seq number not null,                -- �ϷĹ�ȣ
    rate number(5,2) not null,          -- ����
    title varchar2(150) not null,       -- ����
    director varchar2(150) not null,    -- ����
    playdate varchar2(150) not null,    -- ������
    runtime varchar2(150) not null,     -- �󿵽ð�
    genre varchar2(150) not null,       -- �帣
    actor varchar2(150) not null,       -- ���
    plot varchar2(4000) not null,       -- �ٰŸ�
    watch varchar2(150) not null,       -- ������
    place varchar2(150) not null,       -- ���� ���
    review varchar2(4000) not null,     -- ���� ����
    reg_date date not null,             -- ��� �Ͻ�
    edit_date date not null,            -- ���� �Ͻ�
    id varchar2(30) not null,           -- �ۼ���
    primary key(seq)
);

-- ���̺� ����
drop table review purge;

-- ���̺� ����
desc review;

-- ���̺� Ȯ��
select * from tab;

-- ������ ��ü ����
create sequence seq_review nocache nocycle;

-- ������ ��ü ����
drop sequence seq_review;

-- insert
insert into review values 
(seq_review.nextval, 5, '�ظ� ���Ϳ� �������� ��', 'ũ���� �ݷ�����', '2001�� 12�� 14��', '120', '��Ÿ��', '�ٴϿ� ����Ŭ����|����Ʈ �׸�Ʈ|���� �ӽ�', '�ظ����� �ٰŸ�', '2020�� 11�� 24��', '��', '�λ� ��ȭ �ظ� ����', sysdate, sysdate, 'test');
 
-- select
select * from review;
select * from review where id='test';
select * from review where seq=2 and id='hong';
select * from review where id='lee' order by seq desc;
select count(*) as cnt from review where id='park';
select count(*) as cnt from review where id='park' and watch    ='2020�� 12�� 3��';
select * from review where id='park' and watch='2020�� 12�� 3��';
 
-- update
update review set
    rate=2,
    watch='2020�� 11�� 25��',
    place='�п�',
    review='�ʹ� ��մ� �ظ� ����',
    edit_date=sysdate
where seq=2 and id='hong';
 
-- delete
delete from review where seq=1 and id='hong';

-- review ���̺��� ������ ����� seq �� ������
select max(seq) as seq from review where id='hong';

-- �� �� ��
select count(*) as cnt from review where id='hong';

-- runtime �հ�
select sum(runtime) from review where id='hong';

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------

-- �̹��� URL ���� ���̺�
create table reviewurl (
    seq number not null,                -- �ϷĹ�ȣ
    review_seq number not null,         -- review �� ��ȣ
    imageUrl varchar2(1000) not null,   -- �̹��� URL
    reg_date date,                      -- �����
    primary key(seq)
);

-- ���̺� ����
drop table reviewurl purge;

-- ������ ��ü ����
create sequence seq_reviewurl nocache nocycle;

-- ������ ��ü ����
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

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------

-- ���� ���� ���� ���̺�
create table reviewfiles (
    seq number not null,                -- �ϷĹ�ȣ
    review_seq number not null,         -- review �� ��ȣ
    dir varchar2(256) not null,         -- ���� ���� �̸�
    filename varchar2(256) not null,    -- ���� �̸�
    originname varchar2(256) not null,  -- ���� ���� �̸�
    filesize number not null,           -- ���� ũ��
    filetype varchar2(50) not null,     -- ���� ����
    reg_date date,                      -- �����
    primary key(seq)
);

-- ���̺� ����
drop table reviewfiles purge;

-- ������ ��ü ����
create sequence seq_reviewfiles nocache nocycle;

-- ������ ��ü ����
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

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------

-- �����ڸ�� ���� id : moviemovie, pw : movie!
-- faq ���̺�
create table faq (
    faq_no varchar2(30) primary key,
    faq_subject varchar2(100) not null,
    faq_content varchar2(2000) not null
);

-- ���̺� ���� Ȯ��
desc faq;

-- ���̺� ����
drop table faq purge;

-- ���̺� ���
select * from tab;

-- insert
insert into faq values (
    '1', '���̵�/��й�ȣ�� ��ﳪ�� �ʾƿ�.', '���񹫺� �α��� ���������� �����Ǵ� ���̵�/��й�ȣ ã�⸦ �̿��� ���̵� ��й�ȣ�� ã���� �� �ֽ��ϴ�.'
);

insert into faq values (
    '2', '���̵� �ٲٰ� ������ ������ �� �ֳ���?', '���̵� ������ Ż�� �� �簡��(�ű԰���)�� ���� ���ο� ���̵�� �����Ͻ� �� �ֽ��ϴ�.
Ż�� �� �������� �簡�� �����մϴ�. '
);

insert into faq values (
    '3', '���� �� ��ȭ ����� �ȵǾ� �־��. ��� �ϳ���?', '���񹫺� ���� �Է��� ��û������ ���� �� ��ȭ�� ǥ����� �ʴ� ���,
���񹫺� ������(1544-1687)�� �����ֽø� ��� Ȯ�� �� �ȳ��帮�ڽ��ϴ�.'
);

insert into faq values (
    '4', '������ǰ� ����� ���� �ʾƿ�. ��� �ϳ���?', '���ø����̼� ������ ������ǰ� ���������� ó������ �ʴ� ���
���� ���� ������(1544-1687)�� �����ֽø� ��� Ȯ�� �� �ȳ��帮�ڽ��ϴ�.'
);

insert into faq values (
    '5', '���̵�, ��й�ȣ�� �ؾ����.', '���񹫺� �α��� ���������� �����Ǵ� ���̵�/��й�ȣ ã�⸦ �̿��� ���̵� ��й�ȣ�� ã���� �� �ֽ��ϴ�.'
);

insert into faq values (
    '6', '�������� ���� �̸� ������ ��𿡼� �ϳ���?', '���񹫺� �α��� �� ����������->������ �������� �����Ͻ� �� �ֽ��ϴ�.'
);

insert into faq values (
    '7', '���񹫺� �ۿ����� � ���񽺸� �̿��� �� �ֳ���?', 'ȸ�� ���� �� �Է��� ���̵�� ������ �Ұ��ϸ�, Ż�� �� �簡���� ���� ���ο� ���̵�� ������ �� �ֽ��ϴ�.'
);

insert into faq values (
    '8', '���̵� �ٲٰ� ������ ������ �� �ֳ���?', '��ȭ �˻�,���� �ڽ� ���ǽ� Ȯ�� ��ɵ��� �����ϰ� �帮�� ������
�Ϻ� ���񽺴� �̿뿡 ������ ���� �� ������,
�پ��� ���� �̿��� ���Ͻ� ���, ��ȭ�� APP�� �̿� ��Ź�帳�ϴ�.'
);

-- select
select * from faq order by faq_no asc;

-- delete
delete from faq where faq_no=0;

-- DB ����
commit;

----------------------------------------------------------------------------------------------------------------------

-- notice ���̺�
create table notice (
    seq number not null,
    notice_subject varchar2(100) not null,
    notice_content varchar2(2000) not null,
    notice_date date,
    modify_date date,
    primary key(seq)
);

-- ���̺� ���� Ȯ��
desc notice;

-- ���̺� ����
drop table notice purge;

-- ���̺� ���
select * from tab;

-- ������ ��ü ����
create sequence seq_notice nocache nocycle;

-- ������ ��ü ����
drop sequence seq_notice;

-- insert
insert into notice values (
    seq_notice.nextval, '�ڷγ�19�� �����Ͽ� �� ���� ��ȭ ��� �ӽ� �ߴ� �ȳ�', '����', sysdate, sysdate
);

-- delete
delete from notice where seq=1;

-- update
update notice set
    notice_subject='���� ���� ����',
    notice_content='���� ���� ����',
    modify_date=sysdate
    where seq='1';

-- select
select * from notice order by seq asc;

-- DB ����
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

-- �ܷ�Ű ����
alter table genre drop constraint id;

commit;