package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import faq.bean.FaqDTO;
import notice.bean.NoticeDTO;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	// notice
	@RequestMapping(value = "/admin/notice_write.do")
	public ModelAndView noticeWrite(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		// 데이터 처리
		String notice_subject = request.getParameter("notice_subject");
		String notice_content = request.getParameter("notice_content");
		// DB 처리
		NoticeDTO noticeDTO = new NoticeDTO();
		
		noticeDTO.setNotice_subject(notice_subject);
		noticeDTO.setNotice_content(notice_content);
		
		int su = adminService.noticeWrite(noticeDTO);
		// json
		String rt = null;
		if(su > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/notice_delete.do")
	public ModelAndView noticeDelete(HttpServletRequest request) throws IOException {
		// 데이터 처리
		int seq = Integer.parseInt(request.getParameter("seq"));
		// DB
		int su = adminService.noticeDelete(seq);
		
		// json
		String rt = null;
		if(su > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/notice_modify.do")
	public ModelAndView noticeModify(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		// 데이터 처리
		String notice_subject = request.getParameter("notice_subject");
		String notice_content = request.getParameter("notice_content");
		int seq = Integer.parseInt(request.getParameter("seq"));
		// DB 처리
		NoticeDTO noticeDTO = new NoticeDTO();

		noticeDTO.setNotice_subject(notice_subject);
		noticeDTO.setNotice_content(notice_content);
		noticeDTO.setSeq(seq);

		int su = adminService.noticeModify(noticeDTO);
		
		// json
		String rt = null;
		if(su > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}

		// json 출력
		JSONObject json = new JSONObject();
		json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/notice_list.do")
	public ModelAndView noticeList(HttpServletRequest request) throws IOException {
		List<NoticeDTO> list = adminService.noticeList();
		
		// json
		String rt = null;
		int total = list.size();
		
		if(total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}

		// json 출력
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("total", total);
		
		if(total > 0) {
			JSONArray item = new JSONArray();
			
			for(int i=0; i<total; i++) {
				NoticeDTO noticeDTO = list.get(i);
				
				JSONObject temp = new JSONObject();
				temp.put("seq", noticeDTO.getSeq());
				temp.put("notice_subject", noticeDTO.getNotice_subject());
				temp.put("notice_content", noticeDTO.getNotice_content());
				temp.put("notice_date", noticeDTO.getNotice_date());
				temp.put("modify_date", noticeDTO.getModify_date());
				
				item.put(i, temp);
			}
			json.put("item", item);
		}
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/notice_select.do")
	public ModelAndView noticeSelect(HttpServletRequest request) throws Exception {
		// 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		// DB
		NoticeDTO noticeDTO = adminService.noticeSelect(seq);
		// json
		String rt = "FAIL";
		int total = 0;
		
		if(noticeDTO != null) {
			rt = "OK";
			total = 1;
		}
		
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("total", total);
		
		if(total > 0) {
			JSONArray item = new JSONArray();
			
			JSONObject temp = new JSONObject();
			temp.put("seq", noticeDTO.getSeq());
			temp.put("notice_subject", noticeDTO.getNotice_subject());
			temp.put("notice_content", noticeDTO.getNotice_content());
			temp.put("notice_date", noticeDTO.getNotice_date());
			temp.put("modify_date", noticeDTO.getModify_date());
				
			// JSONArray에 추가
			item.put(0, temp);
		
			json.put("item", item);
		}
		
		// 화면 내비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	// faq
	@RequestMapping(value = "/admin/faq_write.do")
	public ModelAndView faqWrite(HttpServletRequest request) throws IOException {
		// 한글 설정
		request.setCharacterEncoding("utf-8");
		// 데이터 처리
		int faq_no = Integer.parseInt(request.getParameter("faq_no"));
		String faq_subject = request.getParameter("faq_subject");
		String faq_content = request.getParameter("faq_content");
		// DB 처리
		FaqDTO faqDTO = new FaqDTO();
		
		faqDTO.setFaq_no(faq_no);
		faqDTO.setFaq_subject(faq_subject);
		faqDTO.setFaq_content(faq_content);
		
		int su = adminService.faqWrite(faqDTO);
		// json
	    String rt = "FAIL";   
	    if(su > 0) rt = "OK";
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/faq_list.do")
	public ModelAndView faqList(HttpServletRequest request) throws IOException {
		// DB 처리
		List<FaqDTO> list = adminService.faqList();
		
		// json
	    String rt = "FAIL";
	    int total = list.size();
	    
	    if(total > 0) {
	    	rt = "OK";
	    }
		
	    // json 출력
	    JSONObject json = new JSONObject();
	    json.put("rt", rt);
	    json.put("total", total);
	    
	    if(total > 0) {
			JSONArray item = new JSONArray();
			
			for(int i=0; i<total; i++) {
				FaqDTO faqDTO = list.get(i);
				
				JSONObject temp = new JSONObject();
				temp.put("faq_no", faqDTO.getFaq_no());
				temp.put("faq_subject", faqDTO.getFaq_subject());
				temp.put("faq_content", faqDTO.getFaq_content());
				
				// JSONArray에 추가
				item.put(i, temp);
			}
			json.put("item", item);
		}
	    
		// 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("admin.jsp");
		return modelAndView;
	}
}
