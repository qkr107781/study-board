package com.study.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "std_user")
@Getter
@NoArgsConstructor
public class UserEntity {

	@Builder
	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Id
	@Column(name="username",columnDefinition = "VARCHAR(10)")
	@GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 설정된 방식으로 기본키 생성
	private String username;
	
	@Column(name="password",columnDefinition = "VARCHAR(15)")
	private String password;
	
}
