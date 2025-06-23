package com.study.board.projection;

import java.time.LocalDateTime;

public interface BoardViewProjection {

	String getSubject();
	String getWriter();
	String getContent();
	LocalDateTime getRegdate();	
	LocalDateTime getModifydate();	
	
}
