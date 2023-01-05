package reviewurl.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reviewurl.bean.ReviewUrlDTO;

@Repository
public class ReviewUrlDAO {
	
	@Autowired
	SqlSessionTemplate sql;
	
	// Review 타이틀의 마지막 저장된 seq값 얻어오기
	public int getReviewUrlFirstSeq() {
		return sql.selectOne("mybatis.reviewurlMapper.getReviewUrlFirstSeq");
	}
	
	// 입력 내용 저장
	public int urlWrite(ReviewUrlDTO reviewUrlDTO) {
		return sql.insert("mybatis.reviewurlMapper.urlWrite", reviewUrlDTO);
	}
	
	// Url이 있는 지 검사
	public String checkUrl(int review_seq) {
		return sql.selectOne("mybatis.reviewurlMapper.checkUrl", review_seq);
	}
	   
	// Url 삭제
	public int urlDelete(int review_seq) {
		return sql.delete("mybatis.reviewurlMapper.urlDelete", review_seq);
	}
}

