package com.study.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.user.domain.CustomUserDetails;
import com.study.user.domain.UserDto;
import com.study.user.domain.UserEntity;
import com.study.user.repository.UserRepository;
import com.study.util.exception.CustomException;
import com.study.util.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserEntity addUser(UserDto user) {
		if(userRepository.countByUsername(user.getUsername()) > 0) {
			throw new CustomException(ErrorCode.USERNAME_ALREADY_USED);
		}
		
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
		
	    UserEntity userEntity = UserEntity.builder().username(user.getUsername()).password(user.getPassword()).role(user.getRole()).build();
	    
		return userRepository.save(userEntity);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);

		if(userEntity != null) {
			return new CustomUserDetails(userEntity);
		}
		
		return null;
	}
}
