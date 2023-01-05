package admin.controller;

import java.util.List;

import faq.bean.FaqDTO;
import notice.bean.NoticeDTO;

public interface AdminService {
	// notice
	// 입력
	public int noticeWrite(NoticeDTO noticeDTO);
	
	// 삭제
	public int noticeDelete(int seq);
	
	// 수정
	public int noticeModify(NoticeDTO noticeDTO);
	
	// 목록
	public List<NoticeDTO> noticeList();
	
	// 내용 보기
	public NoticeDTO noticeSelect(int seq);
	
	// faq
	// 입력
	public int faqWrite(FaqDTO faqDTO);
	
	// 리스트
	public List<FaqDTO> faqList();
}