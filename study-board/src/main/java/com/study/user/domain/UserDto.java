package com.study.user.domain;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	//최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
	@Size(min = 4, message = "최소 4글자 이상 입니다.")
	@Size(max = 10, message = "최대 10글자까지 입력가능 합니다.")
	@Pattern(regexp = "^[a-z0-9]*$", message = "영어 알파벳 소문자와 숫자만 입력할 수 있습니다.")
	private String username;
	
	//최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
	@Size(min = 8, message = "최소 8글자 이상 입니다.")
	@Size(max = 15, message = "최대 15글자까지 입력가능 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 알파벳과 숫자만 입력할 수 있습니다.")
	private String password;
	
	private String role;
	
	private String token;
}
