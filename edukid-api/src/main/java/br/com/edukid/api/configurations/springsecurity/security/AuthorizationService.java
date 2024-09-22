package br.com.edukid.api.configurations.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	UserFatherRepository fatherRepository;
	@Autowired
	UserChildRepository childRepository;
	/**
	 * FAZ A CONSULTA DO USUÁRIO PARA O SPRING SECURITY
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(fatherRepository.existsByEmail(username))
			return fatherRepository.findByEmailUserDetails(username);
		else
			return childRepository.findByNicknameUserDetails(username);
	}
		

}
