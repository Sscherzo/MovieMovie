package memberfiles.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import memberfiles.bean.MemberfilesDTO;

@Repository
public class MemberfilesDAO {
   
   @Autowired
   SqlSessionTemplate sqlSession;
   
   // Community 타이틀의 마지막 저장된 seq값 얻어오기
   public int getMemberFirstSeq() {
      return sqlSession.selectOne("mybatis.memberfilesMapper.getMemberFirstSeq");
   }
   
   // 입력 내용 저장
   public int filesWrite(MemberfilesDTO memberfilesDTO) {
      return sqlSession.insert("mybatis.memberfilesMapper.filesWrite", memberfilesDTO);
   }
   
   // 파일이 있는지 검사
   public String checkFiles(int mem_seq) {
      return sqlSession.selectOne("mybatis.memberfilesMapper.checkFiles", mem_seq);
   }
      
   // 파일 삭제 : 1. db 삭제, 2. 실제 파일 삭제
   public int filesDelete(int mem_seq, String filePath) {
      int su = sqlSession.delete("mybatis.memberfilesMapper.filesDelete", mem_seq);
      if (su > 0) {   // 파일 삭제
         File file = new File(filePath);
         file.delete();
      }
      return su;
   }
}