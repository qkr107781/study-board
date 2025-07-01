package com.study.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ErrorCode errorCode;
}