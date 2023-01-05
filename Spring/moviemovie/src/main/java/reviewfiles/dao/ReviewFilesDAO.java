package reviewfiles.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reviewfiles.bean.ReviewFilesDTO;

@Repository
public class ReviewFilesDAO {
	
	@Autowired
	SqlSessionTemplate sql;
	
	// Review 타이틀의 마지막 저장된 seq값 얻어오기
	public int getReviewFirstSeq() {
		return sql.selectOne("mybatis.reviewfilesMapper.getReviewFirstSeq");
	}
	
	// 입력 내용 저장
	public int filesWrite(ReviewFilesDTO reviewFilesDTO) {
		return sql.insert("mybatis.reviewfilesMapper.filesWrite", reviewFilesDTO);
	}
	
	// 파일이 있는 지 검사
	public String checkFiles(int review_seq) {
		return sql.selectOne("mybatis.reviewfilesMapper.checkFiles", review_seq);
	}
	   
	// 파일 삭제 : 1. db 삭제, 2. 실제 파일 삭제
	public int filesDelete(int review_seq, String filePath) {
		int su = sql.delete("mybatis.reviewfilesMapper.filesDelete", review_seq);
		if (su > 0) {	// 파일 삭제
			File file = new File(filePath);
			file.delete();
		}
		return su;
	}
}
