package com.study.board.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.board.domain.BoardEntity;
import com.study.board.exception.CustomException;
import com.study.board.exception.ErrorCode;
import com.study.board.projection.BoardViewProjection;
import com.study.board.repository.BoardRepository;

import jakarta.persistence.EntityManager;

@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final EntityManager em;
	
	public BoardService(BoardRepository boardRepository,EntityManager em) {
		this.boardRepository = boardRepository;
		this.em = em;
	}

    public List<BoardViewProjection> getList(){
    	List<BoardViewProjection> resultProjections = boardRepository.findAllByOrderByRegdateDesc();
    	if(resultProjections.isEmpty()) {
    		throw new CustomException(ErrorCode.BOARD_EMPTY);
    	}
    	return resultProjections;
    }
    
    public BoardViewProjection getDetail(String ukey){
    	BoardViewProjection resultProjections = boardRepository.findByUkey(ukey);
    	if(resultProjections == null) {
    		throw new CustomException(ErrorCode.BOARD_EMPTY);
    	}
    	return resultProjections;
    }

    public BoardEntity insertBoard(BoardEntity board){
    	return boardRepository.save(board);
    }
    
    @Transactional
    public boolean updateBoard(BoardEntity board){
    	boolean result = false;
    	
    	if(boardRepository.countByUkeyAndPassword(board.getUkey(), board.getPassword()) > 0) {
    		//Dirty Checking을 통해 update - EntityManager로 ukey값에 맞는 데이터 조회 후 값을 새로 세팅하여 Transaction 종료 후 update 진행되게함
    		BoardEntity beforeModifiedBoard = em.find(BoardEntity.class, board.getUkey());
    		
    		String subject = board.getSubject();
    		String writer = board.getWriter();
    		String content = board.getContent();
    		
    		if(StringUtils.isNotEmpty(subject)) {
    			beforeModifiedBoard.updateSubject(subject);
    		}
    		if(StringUtils.isNotEmpty(writer)) {
    			beforeModifiedBoard.updateWrite(writer);
    		}
    		if(StringUtils.isNotEmpty(content)) {
    			beforeModifiedBoard.updateContent(content);
    		}
    		beforeModifiedBoard.updateModifydate(LocalDateTime.now());
    		
    		result = true;
    	}else {
    		throw new CustomException(ErrorCode.BOARD_EMPTY);
    	}
    	
    	return result;
    }
    
    public void deleteBoard(BoardEntity board){
    	if(boardRepository.countByUkeyAndPassword(board.getUkey(), board.getPassword()) > 0) {
			boardRepository.deleteById(board.getUkey());
    	}else {
    		throw new CustomException(ErrorCode.BOARD_EMPTY);
    	}
    }
}
