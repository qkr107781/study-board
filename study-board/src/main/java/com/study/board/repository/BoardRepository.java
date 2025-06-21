package com.study.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.board.domain.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String>{
	List<BoardViewMapping> findAllByOrderByRegdateDesc();
	BoardViewMapping findByUkey(String ukey);
}
