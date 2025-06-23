package com.study.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.board.domain.BoardEntity;
import com.study.board.projection.BoardViewProjection;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String>{
	
	//목록 조회(작성일 내림차순 정렬)
	List<BoardViewProjection> findAllByOrderByRegdateDesc();
	
	//상세 조회
	BoardViewProjection findByUkey(String ukey);
	
	//수정 시 password 체크를 위한 조회, Dirty Checking 상세 조회
	int countByUkeyAndPassword(String ukey, String password);

}
