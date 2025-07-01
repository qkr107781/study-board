package com.study.util.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
	BOARD_EMPTY(ErrorStatusCode.BOARDEMPTY, "BOARD_EMPTY", "조회된 게시글이 없습니다."),
	INVALID_INPUT(ErrorStatusCode.INVALIDINPUT, "INVALID_INPUT", "입력값을 확인해주세요."),
	INVALID_PASSWORD(ErrorStatusCode.INVALIDPASSWORD, "INVALID_PASSWORD", "게시글 패스워드가 일치하지 않습니다."),
	USERNAME_ALREADY_USED(ErrorStatusCode.USERNAMEALREADYUSED, "USERNAME_ALREADY_USED", "이미 존재하는 계정 입니다.");
	
	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	private final int status;
    private final String code;
    private final String message;

	class ErrorStatusCode{
		private final static int INVALIDINPUT = 600;
		private final static int INVALIDPASSWORD = 601;
		private final static int BOARDEMPTY = 604;
		private final static int USERNAMEALREADYUSED = 605;
	}
}