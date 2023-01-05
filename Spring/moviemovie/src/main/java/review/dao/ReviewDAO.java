package review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import review.bean.ReviewDTO;

@Repository
public class ReviewDAO {
	@Autowired
	SqlSessionTemplate sql;
	
	// 내용 저장
	public int reviewWrite(ReviewDTO reviewDTO) {
		return sql.insert("mybatis.reviewMapper.reviewWrite", reviewDTO);
	}
	
	// 내용 삭제
	public int reviewDelete(int seq, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("id", id);
		return sql.delete("mybatis.reviewMapper.reviewDelete", map);
	}
	
	// 내용 수정
	public int reviewModify(ReviewDTO reviewDTO) {
		return sql.update("mybatis.reviewMapper.reviewModify", reviewDTO);
	}
	
	// 목록 확인 : 전체 목록
	public List<ReviewDTO> reviewList(String id) {
		return sql.selectList("mybatis.reviewMapper.reviewList", id);
	}
	
	// 목록 확인 : 관람일 기준 목록
	public List<ReviewDTO> reviewWatchList(String id, String watch) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("watch", watch);
		return sql.selectList("mybatis.reviewMapper.reviewWatchList", map);
	}
	
	// 목록 확인 : 상세 보기
	public ReviewDTO reviewSelect(int seq, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("id", id);
		return sql.selectOne("mybatis.reviewMapper.reviewSelect", map);
	}
	
	// 총 글 수
	public int getTotalCnt(String id) {
		return sql.selectOne("mybatis.reviewMapper.getTotalCnt", id);
	}
	
	// 총 관람 시간
	public String getTotalRunTime(String id) {
		return sql.selectOne("mybatis.reviewMapper.getTotalRunTime", id);
	}
	
	// 관람일의 글 수 구하기
	public int getWatchCnt(String id, String watch) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("watch", watch);
		return sql.selectOne("mybatis.reviewMapper.getWatchCnt", map);
	}
}
