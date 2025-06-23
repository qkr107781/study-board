package com.study.board.repository;

import java.time.LocalDateTime;

public interface BoardViewMapping {

	String getSubject();
	String getWriter();
	String getContent();
	LocalDateTime getRegdate();	
	LocalDateTime getModifydate();	
	
}