package member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import member.bean.MemberDTO;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/member/member_write.do")
	public ModelAndView memberWrite(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		
		// DB 처리
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(id);
		memberDTO.setPw(pw);
		memberDTO.setNickname(nickname);
		memberDTO.setName(name);
		memberDTO.setEmail(email);
		memberDTO.setTel(tel);
		int su = memberService.memberWrite(memberDTO);
		
		// json
	    String rt = "FAIL";   
	    if(su > 0) rt = "OK";
	    
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
		
	    // 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/member_modify.do")
	public ModelAndView memberModify(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String img = request.getParameter("img");
		
		// DB 처리
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setNickname(nickname);
		memberDTO.setEmail(email);
		memberDTO.setTel(tel);
		memberDTO.setId(id);
		memberDTO.setPw(pw);
		memberDTO.setImg(img);
		
		int su = memberService.memberModify(memberDTO);
		
		// json
	    String rt = "FAIL";
	    if(su > 0) rt = "OK";
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("su", su);
		
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/member_login.do")
	public ModelAndView login(HttpServletRequest request) {
		// 데이터 처리
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// DB
		String name = memberService.memberLogin(id, pw);
     	
		// 페이지 이동
		String rt = "FAIL";
		if(name != null) rt = "OK";
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/member_delete.do")
	public ModelAndView memberDelete(HttpServletRequest request) throws IOException {
		// 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		String pw = request.getParameter("pw");
		// DB
		int su = memberService.memberDelete(seq, pw);
		
		// json
	    String rt = "FAIL";
	    if(su > 0) rt = "OK";

		// json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
		
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/member_list.do")
	public ModelAndView memberList(HttpServletRequest request) throws Exception {
		String img = null;
		//DB
		List<MemberDTO> list = memberService.memberList();
		// json
		String rt = "FAIL";
		int total = list.size();
		if(total > 0) {
			rt = "OK";
		}
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("total", total);

		if(rt.equals("OK")) {
			JSONArray item = new JSONArray();
			for(int i=0; i<total; i++) {
				MemberDTO memberDTO = list.get(i);
				
				// json 객체
				JSONObject temp = new JSONObject();
				if(memberDTO.getImg() == null) {		
					img = null;
					
				    temp.put("rt", rt);
					temp.put("seq", memberDTO.getSeq());
					temp.put("id", memberDTO.getId());
				    temp.put("pw", memberDTO.getPw());
				    temp.put("nickName", memberDTO.getNickname());
				    temp.put("name", memberDTO.getName());
				    temp.put("email", memberDTO.getEmail());
				    temp.put("tel", memberDTO.getTel());
				    temp.put("img", img);
					
				} else {
					img = memberDTO.getImg();
					
					temp = new JSONObject();
					temp.put("rt", rt);
					temp.put("seq", memberDTO.getSeq());
					temp.put("id", memberDTO.getId());
				    temp.put("pw", memberDTO.getPw());
				    temp.put("nickName", memberDTO.getNickname());
				    temp.put("name", memberDTO.getName());
				    temp.put("email", memberDTO.getEmail());
				    temp.put("tel", memberDTO.getTel());
				    temp.put("img", img);
				}
				// JSONArray에 추가
				item.put(i, temp);
			}
			json.put("item", item);
		}

		// 화면 이동
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/getMember.do")
	public ModelAndView getMember(HttpServletRequest request) throws Exception {
		// 데이터 처리
		String id = request.getParameter("id");
		String pw = null;
		String nickName = null;
		String email = null;
		String tel = null;
		String img = null;
		int seq = 0;
		
		JSONObject json = new JSONObject();
		
		// DB
		MemberDTO memberDTO = memberService.getMember(id);
		// json
		String rt = "FAIL";
		if(memberDTO != null) {
			rt="OK";
			nickName = memberDTO.getNickname();
			seq = memberDTO.getSeq();
		}
			
		if(memberDTO.getImg() == null) {
			pw = memberDTO.getPw();
			email = memberDTO.getEmail();
			tel = memberDTO.getTel();		
			img = null;
			
			json = new JSONObject();
		    json.put("rt", rt);
		    json.put("seq", seq);
		    json.put("id",id);
		    json.put("pw",pw);
		    json.put("nickName",nickName);
		    json.put("email",email);
		    json.put("tel",tel);
		    json.put("img", img);
			
		} else {
			pw = memberDTO.getPw();
			email = memberDTO.getEmail();
			tel = memberDTO.getTel();	
			img = memberDTO.getImg();
			
			json = new JSONObject();
			json.put("rt", rt);
		    json.put("seq", seq);
		    json.put("id",id);
		    json.put("pw",pw);
		    json.put("nickName",nickName);
		    json.put("email",email);
		    json.put("tel",tel);
		    json.put("img", img);
		}
		// 화면 이동
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/member/checkId.do")
	public ModelAndView checkId(HttpServletRequest request) throws Exception {
		// 데이터 처리
		String id = request.getParameter("id");
		// DB 처리
		boolean exist = memberService.checkId(id);
		
		// json
	    String rt = "";
	    if(exist) {
	    	rt = "FAIL";	// 사용중인 아이디입니다
	    } else if(!exist) {
	    	rt = "OK";		// 사용가능한 아이디입니다.
	    }
	    
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
		
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	// id 찾기
	@RequestMapping(value = "/member/find_id.do")
	public ModelAndView findId(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		
		// DB 처리
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setName(name);
		memberDTO.setTel(tel);
		
		String id = memberService.findId(name, tel);
		
		// json
	    String rt = "FAIL";
	    if(id != null) rt = "OK";
	    
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("id", id);
		
		// 화면 내비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		return modelAndView;
	}
	
	// pw 찾기
    @RequestMapping(value = "/member/find_pw.do")
    public ModelAndView findPw(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("utf-8");
        // 데이터 처리
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String tel = request.getParameter("tel");
        // DB 처리
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName(name);
        memberDTO.setId(id);
        memberDTO.setTel(tel);

        String pw = memberService.findPw(name, id, tel);

        // json
        String rt = "FAIL";
	    if(pw != null) rt = "OK";

        JSONObject json = new JSONObject();
        json.put("rt", rt);
        json.put("pw", pw);

        // 화면 내비게이션
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("json", json);
        modelAndView.setViewName("member.jsp");
        return modelAndView;
    }
    
	@RequestMapping(value = "/member/pw_modify.do")
	public ModelAndView pwModify(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		
		// 데이터 처리
		String pw = request.getParameter("pw");
		String id = request.getParameter("id");
		
		// DB 처리
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setPw(pw);
		memberDTO.setId(id);
		
		int su = memberService.pwModify(pw, id);
		
		// json
	    String rt = "FAIL";
	    if(su > 0) rt = "OK";
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("su", su);
		
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("member.jsp");
		
		return modelAndView;
	}
}
