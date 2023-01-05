package genre.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import genre.bean.GenreDTO;
import member.bean.MemberDTO;

@Repository
public class GenreDAO {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public int genreWrite(GenreDTO genreDTO) {
		return sqlSession.insert("mybatis.memberMapper.genreWrite", genreDTO);
	}
	
	public int genreModify(GenreDTO genreDTO) {
		return sqlSession.update("mybatis.memberMapper.genreModify", genreDTO);
	}
	
	public GenreDTO genreSelect(String id) {
		return sqlSession.selectOne("mybatis.memberMapper.genreSelect", id);
	}
	
	public int genreDelete(String id) {
		return sqlSession.delete("mybatis.memberMapper.genreDelete", id);
	}
}
