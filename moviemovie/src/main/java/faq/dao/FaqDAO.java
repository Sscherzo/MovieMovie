package faq.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import faq.bean.FaqDTO;

@Repository
public class FaqDAO {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 데이터 추가하기
	public int faqWrite(FaqDTO faqDTO) {
		return sqlSession.insert("mybatis.faqMapper.faqWrite", faqDTO);
	}
	
	// 전체 검색
	public List<FaqDTO> faqList() {
		return sqlSession.selectList("mybatis.faqMapper.faqList");
	}

}
