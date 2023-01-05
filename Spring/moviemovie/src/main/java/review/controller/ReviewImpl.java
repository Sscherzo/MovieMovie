package review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import review.bean.ReviewDTO;
import review.dao.ReviewDAO;
import reviewfiles.bean.ReviewFilesDTO;
import reviewfiles.dao.ReviewFilesDAO;
import reviewurl.bean.ReviewUrlDTO;
import reviewurl.dao.ReviewUrlDAO;

@Service
public class ReviewImpl implements ReviewService {
	@Autowired
	ReviewDAO reviewDAO;
	
	@Autowired
	ReviewFilesDAO reviewfileDAO;
	
	@Autowired
	ReviewUrlDAO reviewurlDAO;

	@Override
	public int reviewWrite(ReviewDTO reviewDTO) {
		return reviewDAO.reviewWrite(reviewDTO);
	}

	@Override
	public int reviewDelete(int seq, String id) {
		return reviewDAO.reviewDelete(seq, id);
	}

	@Override
	public int reviewModify(ReviewDTO reviewDTO) {
		return reviewDAO.reviewModify(reviewDTO);
	}

	@Override
	public List<ReviewDTO> reviewList(String id) {
		return reviewDAO.reviewList(id);
	}

	@Override
	public List<ReviewDTO> reviewWatchList(String id, String watch) {
		return reviewDAO.reviewWatchList(id, watch);
	}

	@Override
	public ReviewDTO reviewSelect(int seq, String id) {
		return reviewDAO.reviewSelect(seq, id);
	}

	@Override
	public int getTotalCnt(String id) {
		return reviewDAO.getTotalCnt(id);
	}

	@Override
	public String getTotalRunTime(String id) {
		return reviewDAO.getTotalRunTime(id);
	}
	
	@Override
	public int getWatchCnt(String id, String watch) {
		return reviewDAO.getWatchCnt(id, watch);
	}
	
	@Override
	public int getReviewFirstSeq() {
		return reviewfileDAO.getReviewFirstSeq();
	}
	

	@Override
	public int filesWrite(ReviewFilesDTO reviewFilesDTO) {
		return reviewfileDAO.filesWrite(reviewFilesDTO);
	}

	@Override
	public String checkFiles(int review_seq) {
		return reviewfileDAO.checkFiles(review_seq);
	}

	@Override
	public int filesDelete(int review_seq, String filePath) {
		return reviewfileDAO.filesDelete(review_seq, filePath);
	}

	@Override
	public int getReviewUrlFirstSeq() {
		return reviewurlDAO.getReviewUrlFirstSeq();
	}

	@Override
	public int urlWrite(ReviewUrlDTO reviewUrlDTO) {
		return reviewurlDAO.urlWrite(reviewUrlDTO);
	}

	@Override
	public String checkUrl(int review_seq) {
		return reviewurlDAO.checkUrl(review_seq);
	}

	@Override
	public int urlDelete(int review_seq) {
		return reviewurlDAO.urlDelete(review_seq);
	}
}
