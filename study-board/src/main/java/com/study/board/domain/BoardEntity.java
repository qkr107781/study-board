package com.study.board.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "std_board")
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //ukey값 자동 생성을 위함(auto_increament 설정한 컬럼임)
	@JsonProperty //이거 해줘야 response에 json 리턴값 넘겨줄 수 있음
	private String ukey;
	
	@Column
	@JsonProperty
	private String subject;
	
	@Column
	@JsonProperty
	private String writer;
	
	@Column
	@JsonProperty
	private String content;
	
	@Column
	@JsonProperty
	private String regdate;
	
	@Column
	@JsonProperty
	private String username;
	
	@Column
	@JsonProperty
	private String password;
	
}
