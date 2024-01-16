package tw.niq.security;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.niq.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {

	private final UserRepository userRepository;
	
	@Scheduled(fixedRate = 5000)
	public void unlockAccounts() {
		
		Long lockingPeriod = 30L;	// 30 seconds
		Timestamp lockedTime = Timestamp.valueOf(LocalDateTime.now().minusSeconds(lockingPeriod));
		
		userRepository.findAllByAccountNonLockedAndLastModifiedDateIsBefore(false, lockedTime).ifPresent(lockedUsers -> {
			lockedUsers.forEach(user -> {
				log.debug("Unlocking user: " + user.getUsername());
				user.setAccountNonLocked(true);
			});
			userRepository.saveAll(lockedUsers);
		});
	}
	
}
