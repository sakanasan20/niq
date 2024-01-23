package tw.niq.service;

import java.util.List;

import tw.niq.domain.Notice;

public interface NoticeService {
	
	List<Notice> getAllNotices();

	Notice getNoticeById(Long id);
	
	Notice saveNotice(Notice notice);

	void deleteNoticeById(Long id);

}
