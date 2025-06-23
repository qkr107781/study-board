package com.study.board.dto;

import java.time.LocalDateTime;

public interface BoardViewDto {

	String getSubject();
	String getWriter();
	String getContent();
	LocalDateTime getRegdate();	
	LocalDateTime getModifydate();	
	
}
