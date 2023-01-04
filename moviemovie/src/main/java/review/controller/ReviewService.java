package review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import review.bean.ReviewDTO;
import reviewfiles.bean.ReviewFilesDTO;
import reviewurl.bean.ReviewUrlDTO;

public interface ReviewService {
	// Review
	// 내용 저장
	public int reviewWrite(ReviewDTO reviewDTO);
	// 내용 삭제
	public int reviewDelete(int seq, String id);
	// 내용 수정
	public int reviewModify(ReviewDTO reviewDTO);
	// 목록 확인 : 전체 목록
	public List<ReviewDTO> reviewList(String id);
	// 목록 확인 : 관람일 기준 목록
	public List<ReviewDTO> reviewWatchList(String id, String watch);
	// 목록 확인 : 상세 보기
	public ReviewDTO reviewSelect(int seq, String id);
	// 총 글 수
	public int getTotalCnt(String id);
	// 총 관람 시간
	public String getTotalRunTime(String id);
	// 관람일의 글 수 구하기
	public int getWatchCnt(String id, String watch);
	
	// ReviewFiles
	// Review 테이블의 마지막 저장된 seq값 얻어오기
	public int getReviewFirstSeq();
	// 입력하기
	public int filesWrite(ReviewFilesDTO reviewFilesDTO);
	// 파일이 있는 지 검사
	public String checkFiles(int review_seq);
	// 파일 삭제 : 1. db 삭제, 2. 실제 파일 삭제
	public int filesDelete(int review_seq, String filePath);
	
	// ReviewUrl
	// Review 타이틀의 마지막 저장된 seq값 얻어오기
	public int getReviewUrlFirstSeq();
	// 입력 내용 저장
	public int urlWrite(ReviewUrlDTO reviewUrlDTO);
	// Url이 있는 지 검사
	public String checkUrl(int review_seq);
	// Url 삭제 
	public int urlDelete(int review_seq);
}
