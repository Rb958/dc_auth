package com.sabc.digitalchampions.security.services;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.exceptions.UserNotEnabledException;
import com.sabc.digitalchampions.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);

		if(user == null){
			throw new UsernameNotFoundException("User Not Found with email: " + email);
		}

		return UserDetailsImpl.build(user);
	}

}
