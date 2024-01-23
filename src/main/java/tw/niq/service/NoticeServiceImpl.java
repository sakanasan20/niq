package tw.niq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.niq.domain.Notice;
import tw.niq.repository.NoticeRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeRepository noticeRepository;

	@Override
	public List<Notice> getAllNotices() {
		return noticeRepository.findAll();
	}

	@Override
	public Notice getNoticeById(Long id) {
		return noticeRepository.findById(id).orElseThrow();
	}

	@Override
	public Notice saveNotice(Notice notice) {

		log.debug("### Saving: " + notice);
		
		Notice savedNotice;
		
		if (notice.getId() != null) {
			Notice foundNotice = noticeRepository.findById(notice.getId()).get();
			foundNotice.setNoticeTitle(notice.getNoticeTitle());
			foundNotice.setNoticeContent(notice.getNoticeContent());
			savedNotice = noticeRepository.save(foundNotice);
		} else {
			savedNotice = noticeRepository.save(notice);
		}

		return savedNotice;
	}

	@Override
	public void deleteNoticeById(Long id) {
		noticeRepository.deleteById(id);
	}
	
}
