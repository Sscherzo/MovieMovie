package member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import member.bean.MemberDTO;

@Repository
public class MemberDAO {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 데이터 추가하기
	public int memberWrite(MemberDTO memberDTO) {
		return sqlSession.insert("mybatis.memberMapper.memberWrite", memberDTO);
	}
	
	// 회원 정보 수정
	public int memberModify(MemberDTO memberDTO) {
		return sqlSession.update("mybatis.memberMapper.memberModify", memberDTO);
	}
	
	// 로그인 처리
	public String memberLogin(String id, String pw) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", pw);
		return sqlSession.selectOne("mybatis.memberMapper.memberLogin", map);
	}
	
	// 회원 탈퇴
	public int memberDelete(int seq, String pw) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("pw", pw);
		return sqlSession.delete("mybatis.memberMapper.memberDelete", map);
	}
	
	// 전체 검색
	public List<MemberDTO> memberList() {
		return sqlSession.selectList("mybatis.memberMapper.memberList");
	}
	
	// 1명 데이터 가져오기
	public MemberDTO getMember(String id) {
		return sqlSession.selectOne("mybatis.memberMapper.getMember", id);
	}
	
	// 아이디 중복 확인
	public boolean checkId(String id) {
		boolean exist = false;
		if(sqlSession.selectOne("mybatis.memberMapper.checkId", id) != null) {
			exist = true;
		}
		return exist;
	}
	
	// 아이디 찾기
	public String findId(String name, String tel) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("tel", tel);
		return sqlSession.selectOne("mybatis.memberMapper.findId", map);
	}
	
	// 비밀번호 찾기
	public String findPw(String name, String id, String tel) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("id", id);
        map.put("tel", tel);
        return sqlSession.selectOne("mybatis.memberMapper.findPw", map);
    }
	
	// 비밀번호 변경
	public int pwModify(String pw, String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pw", pw);
        map.put("id", id);
		return sqlSession.update("mybatis.memberMapper.pwModify", map);
	}
}