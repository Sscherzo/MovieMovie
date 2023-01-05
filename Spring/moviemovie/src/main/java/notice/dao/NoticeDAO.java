package notice.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import notice.bean.NoticeDTO;

@Repository
public class NoticeDAO {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public int noticeWrite(NoticeDTO noticeDTO) {
		return sqlSession.insert("mybatis.noticeMapper.noticeWrite", noticeDTO);
	}
	
	public int noticeDelete(int seq) {
		return sqlSession.delete("mybatis.noticeMapper.noticeDelete", seq);
	}
	
	public int noticeModify(NoticeDTO noticeDTO) {
		return sqlSession.update("mybatis.noticeMapper.noticeModify", noticeDTO);
	}
	
	public List<NoticeDTO> noticeList() {
		return sqlSession.selectList("mybatis.noticeMapper.noticeList");
	}
	
	public NoticeDTO noticeSelect(int seq) {
		return sqlSession.selectOne("mybatis.noticeMapper.noticeSelect", seq);
	}

}
