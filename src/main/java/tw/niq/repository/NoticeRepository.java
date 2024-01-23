package tw.niq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
