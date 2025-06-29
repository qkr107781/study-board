package com.study.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "std_user")
@Getter
@NoArgsConstructor
public class UserEntity {

	@Builder
	public UserEntity(String username, String password, String token) {
		this.username = username;
		this.password = password;
		this.token = token;
	}
	
	//최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
	@Size(min = 4, message = "최소 4글자 이상 입니다.")
	@Size(max = 10, message = "최대 10글자까지 입력가능 합니다.")
	@Pattern(regexp = "^[a-z0-9]*$", message = "영어 알파벳 소문자와 숫자만 입력할 수 있습니다.")
	@Id
	@Column(name="username",columnDefinition = "VARCHAR(10)")
	private String username;
	
	//최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
	@Size(min = 8, message = "최소 8글자 이상 입니다.")
	@Size(max = 15, message = "최대 15글자까지 입력가능 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 알파벳과 숫자만 입력할 수 있습니다.")
	@Column(name="password",columnDefinition = "VARCHAR(15)")
	private String password;
	
	@Transient
	private String token;
	
	public void grantToken(String token) {
		this.token = token;
	}
}
