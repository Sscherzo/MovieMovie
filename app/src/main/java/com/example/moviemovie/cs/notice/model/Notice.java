package com.example.moviemovie.cs.notice.model;

import java.io.Serializable;

public class Notice implements Serializable {
    private int seq;                // 공지 일련번호
    private String notice_subject;  // 공지 제목
    private String notice_content;  // 공지 내용
    private String notice_date;     // 공지 등록 일시
    private String modify_date;     // 공지 수정 일시

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getNotice_subject() {
        return notice_subject;
    }

    public void setNotice_subject(String notice_subject) {
        this.notice_subject = notice_subject;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }
}
