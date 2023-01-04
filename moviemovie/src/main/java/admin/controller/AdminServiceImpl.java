package admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import faq.bean.FaqDTO;
import faq.dao.FaqDAO;
import notice.bean.NoticeDTO;
import notice.dao.NoticeDAO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	NoticeDAO noticeDAO;
	
	@Autowired
	FaqDAO faqDAO;
	
	// notice
	@Override
	public int noticeWrite(NoticeDTO noticeDTO) {
		return noticeDAO.noticeWrite(noticeDTO);
	}
	
	@Override
	public int noticeDelete(int seq) {
		return noticeDAO.noticeDelete(seq);
	}
	
	@Override
	public int noticeModify(NoticeDTO noticeDTO) {
		return noticeDAO.noticeModify(noticeDTO);
	}
	
	@Override
	public List<NoticeDTO> noticeList() {
		return noticeDAO.noticeList();
	}

	@Override
	public NoticeDTO noticeSelect(int seq) {
		return noticeDAO.noticeSelect(seq);
	}
	
	// faq
	@Override
	public int faqWrite(FaqDTO faqDTO) {
		return faqDAO.faqWrite(faqDTO);
	}

	@Override
	public List<FaqDTO> faqList() {
		return faqDAO.faqList();
	}

	

	

	

	
	
}
