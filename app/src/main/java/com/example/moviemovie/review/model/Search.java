package com.example.moviemovie.review.model;

import java.io.Serializable;

public class Search implements Serializable {
    // 페이징 처리에 필요한 공통변수
    public static int total_count;
    public static int pageable_count;
    public static boolean is_end;
    // JSON 구조에 따른 멤버변수
    private String collection;
    private String thumbnail_url;
    private String image_url;
    private int width;
    private int height;
    private String display_sitename;
    private String doc_url;
    private String datetime;

    public static int getTotal_count() {
        return total_count;
    }

    public static void setTotal_count(int total_count) {
        Search.total_count = total_count;
    }

    public static int getPageable_count() {
        return pageable_count;
    }

    public static void setPageable_count(int pageable_count) {
        Search.pageable_count = pageable_count;
    }

    public static boolean isIs_end() {
        return is_end;
    }

    public static void setIs_end(boolean is_end) {
        Search.is_end = is_end;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDisplay_sitename() {
        return display_sitename;
    }

    public void setDisplay_sitename(String display_sitename) {
        this.display_sitename = display_sitename;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
