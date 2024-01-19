package tw.niq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tw.niq.domain.Authority;
import tw.niq.repository.AuthorityRepository;

@RequiredArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

	private final AuthorityRepository authorityRepository;

	@Override
	public List<Authority> getAllAuthorities() {
		return authorityRepository.findAll();
	}

	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findById(id).orElseThrow();
	}

	@Override
	public Authority saveAuthority(@Valid Authority authority) {
		
		Authority savedAuthority;
		
		if (authority.getId() != null) {
			Authority foundAuthority = authorityRepository.findById(authority.getId()).get();
			foundAuthority.setPermission(authority.getPermission());
			savedAuthority = authorityRepository.save(foundAuthority);
		} else {
			savedAuthority = authorityRepository.save(authority);
		}

		return savedAuthority;
	}

	@Override
	public void deleteAuthorityById(Long id) {
		authorityRepository.deleteById(id);
	}
	
}
