package com.study.user.service;

import org.springframework.stereotype.Service;

import com.study.user.domain.UserEntity;
import com.study.user.repository.UserRepository;
import com.study.util.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserEntity addUser(UserEntity user) {
		UserEntity userEntity = userRepository.save(user);
		return userEntity;
	}
	
	public UserEntity login(UserEntity user) throws Exception {
		UserEntity userEntity = userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
		if(userEntity == null) {
			throw new Exception("아이디 혹은 비밀번호를 확인해주세요.");
		}
		
		JwtUtil jwtUtil = new JwtUtil();
		String token = jwtUtil.createToken(user.getUsername());
		userEntity.grantToken(token);
		
		return userEntity;
	}
}
