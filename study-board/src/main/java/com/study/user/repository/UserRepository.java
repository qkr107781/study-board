package com.study.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.user.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

	public UserEntity findByUsernameAndPassword(String username, String password);
	
	public UserEntity findByUsername(String username);
	
	public int countByUsername(String username);
}
