package member.controller;

import java.util.List;

import member.bean.MemberDTO;
import memberfiles.bean.MemberfilesDTO;

public interface MemberService {
	// member
	// 회원 정보 입력
	public int memberWrite(MemberDTO memberDTO);
	
	// 회원 정보 수정
	public int memberModify(MemberDTO memberDTO);
	
	// 로그인 처리
	public String memberLogin(String id, String pw);
	
	// 회원 정보 삭제
	public int memberDelete(int seq, String pw);
	
	// 회원 정보 검색
	public List<MemberDTO> memberList();
	
	// 1명 데이터 가져오기
	public MemberDTO getMember(String id);

	// 아이디 중복 확인
	public boolean checkId(String id);
	
	// 아이디 찾기
	public String findId(String name, String tel);
	
	// 비밀번호 찾기
	public String findPw(String name, String id, String tel);
	
	// 비밀번호 변경
	public int pwModify(String pw, String id);
	
	
	// file
	// member 타이틀의 마지막 저장된 seq값 얻어 오기
	public int getMemberFirstSeq();

	// 입력 내용 저장
	public int filesWrite(MemberfilesDTO memberfilesDTO);

	// 파일이 있는지 검사
	public String checkFiles(int mem_seq);

	// 파일 삭제 : 1. db 삭제, 2. 실제 파일 삭제
	public int filesDelete(int mem_seq, String filePath);
}