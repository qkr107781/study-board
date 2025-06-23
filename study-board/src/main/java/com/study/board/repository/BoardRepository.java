package com.study.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.board.domain.BoardEntity;
import com.study.board.dto.BoardViewDto;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String>{
	
	//목록 조회(작성일 내림차순 정렬)
	List<BoardViewDto> findAllByOrderByRegdateDesc();
	
	//상세 조회
	BoardViewDto findByUkey(String ukey);
	
	//수정 시 password 체크를 위한 조회, Dirty Checking 상세 조회
	int countByUkeyAndPassword(String ukey, String password);

}
