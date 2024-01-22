package tw.niq.service;

import java.util.List;

import tw.niq.domain.Authority;

public interface AuthorityService {

	List<Authority> getAllAuthorities();

	Authority getAuthorityById(Long id);

	Authority saveAuthority(Authority authority);

	void deleteAuthorityById(Long id);

}
