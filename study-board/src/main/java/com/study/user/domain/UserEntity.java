package com.study.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	public UserEntity(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	@Id
	@Column(name="username",columnDefinition = "VARCHAR(10)")
	private String username;
	
	@Column(name="password",columnDefinition = "VARCHAR(80)")
	private String password;
	
	@Column(name="role",columnDefinition = "VARCHAR(10)")
	private String role;
	
}
