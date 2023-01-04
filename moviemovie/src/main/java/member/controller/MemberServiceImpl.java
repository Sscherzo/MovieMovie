package member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.bean.MemberDTO;
import member.dao.MemberDAO;
import memberfiles.bean.MemberfilesDTO;
import memberfiles.dao.MemberfilesDAO;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	MemberfilesDAO memberfilesDAO;
	
	// member
	@Override
	public int memberWrite(MemberDTO memberDTO) {
		return memberDAO.memberWrite(memberDTO);
	}

	@Override
	public int memberModify(MemberDTO memberDTO) {
		return memberDAO.memberModify(memberDTO);
	}
	
	@Override
	public String memberLogin(String id, String pw) {
		return memberDAO.memberLogin(id, pw);
	}

	@Override
	public int memberDelete(int seq, String pw) {
		return memberDAO.memberDelete(seq, pw);
	}
	
	@Override
	public List<MemberDTO> memberList() {
		return memberDAO.memberList();
	}
	
	@Override
	public MemberDTO getMember(String id) {
		return memberDAO.getMember(id);
	}
	
	@Override
	public boolean checkId(String id) {
		return memberDAO.checkId(id);
	}

	@Override
	public String findId(String name, String tel) {
		return memberDAO.findId(name, tel);
	}
	
	@Override
    public String findPw(String name, String id, String tel) {
        return memberDAO.findPw(name, id, tel);
    }
	
	@Override
	public int pwModify(String pw, String id) {
		return memberDAO.pwModify(pw, id);
	}	
	
	
	// file
	@Override
	public int getMemberFirstSeq() {
		return memberfilesDAO.getMemberFirstSeq();
	}

	@Override
	public int filesWrite(MemberfilesDTO memberfilesDTO) {
		return memberfilesDAO.filesWrite(memberfilesDTO);
	}

	@Override
	public String checkFiles(int mem_seq) {
		return memberfilesDAO.checkFiles(mem_seq);
	}

	@Override
	public int filesDelete(int mem_seq, String filePath) {
		return memberfilesDAO.filesDelete(mem_seq, filePath);
	}
}
