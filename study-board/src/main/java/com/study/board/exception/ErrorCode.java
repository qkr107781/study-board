package com.study.board.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	BOARD_EMPTY(HttpStatus.NOT_FOUND, "001_BOARD_EMPTY", "조회된 게시글이 없습니다.");

    ErrorCode(HttpStatus status, String code, String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
	}
	private final HttpStatus status;
    private final String code;
    private final String msg;
}