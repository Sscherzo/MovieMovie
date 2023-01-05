package com.example.moviemovie.information.model;

public class PreMovie {
    private String title;       // 영화명
    private String directorNm;  // 감독명
    private String actorNm;     // 배우명
    private String nation;      // 제작국가
    private String plot;        // 줄거리
    private String runtime;     // 상영시간
    private String genre;       // 장르
    private String releaseDate; // 개봉일자
    private String posterUrl;   // 포스터 URL
    private String webPage;     // 웹페이지
    private String id;          // 아이디

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectorNm() {
        return directorNm;
    }

    public void setDirectorNm(String directorNm) {
        this.directorNm = directorNm;
    }

    public String getActorNm() {
        return actorNm;
    }

    public void setActorNm(String actorNm) {
        this.actorNm = actorNm;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
