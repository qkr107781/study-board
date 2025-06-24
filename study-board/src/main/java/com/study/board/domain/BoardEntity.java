package com.study.board.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)//now()로 LocalDateTime 컬럼 default_value 입력
@Table(name = "std_board")
@Getter
@NoArgsConstructor
public class BoardEntity {

	@Builder
	public BoardEntity(String ukey, String subject, String writer, String content, LocalDateTime regdate,
			LocalDateTime modifydate, String username, String password) {
		this.ukey = ukey;
		this.subject = subject;
		this.writer = writer;
		this.content = content;
		this.regdate = regdate;
		this.modifydate = modifydate;
		this.username = username;
		this.password = password;
	}
	
	@Id
	@Column(name="ukey",columnDefinition = "INT")
	@GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 설정된 방식으로 기본키 생성
	private String ukey;
	
	@Column(name="subject",columnDefinition = "VARCHAR(1000)")
	private String subject;
	
	@Column(name="writer",columnDefinition = "VARCHAR(20)")
	private String writer;
	
	@Column(name="content",columnDefinition = "VARCHAR(4000)")
	private String content;
	
	@CreatedDate//now()로 LocalDateTime 컬럼 default_value 입력
	@Column(name="regdate",columnDefinition = "DATETIME")
	private LocalDateTime regdate;
	
	@Column(name="modifydate",columnDefinition = "DATETIME")
	private LocalDateTime modifydate;
	
	@Column(name="username",columnDefinition = "VARCHAR(10)")
	private String username;
	
	@Column(name="password",columnDefinition = "VARCHAR(30)")
	private String password;

	public void updateSubject(String subject) {
		this.subject = subject;
	}
	
	public void updateWrite(String writer) {
		this.writer = writer;
	}
	
	public void updateContent(String content) {
		this.content = content;
	}
	
	public void updateModifydate(LocalDateTime modifydate) {
		this.modifydate = modifydate;
	}

}
