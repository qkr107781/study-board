package com.study.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.domain.BoardEntity;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.BoardViewMapping;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

    public List<BoardViewMapping> getList(){
    	return boardRepository.findAllByOrderByRegdateDesc();
    }
    
    public BoardViewMapping getDetail(String ukey){
    	return boardRepository.findByUkey(ukey);
    }

    public BoardEntity insertBoard(BoardEntity board){
    	return boardRepository.save(board);
    }
    
    public BoardEntity updateBoard(BoardEntity board){
    	return boardRepository.save(board);
    }
    
    public void deleteBoard(String id){
    	boardRepository.deleteById(id);
    }
}
