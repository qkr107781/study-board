package com.study.board.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.board.domain.BoardEntity;
import com.study.board.dto.BoardViewDto;
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

    public List<BoardViewDto> getList(){
    	return boardRepository.findAllByOrderByRegdateDesc();
    }
    
    public BoardViewDto getDetail(String ukey){
    	return boardRepository.findByUkey(ukey);
    }

    public BoardEntity insertBoard(BoardEntity board){
    	return boardRepository.save(board);
    }
    
    @Transactional
    public BoardViewDto updateBoard(BoardEntity board){
    	
		int existBoard = boardRepository.countByUkeyAndPassword(board.getUkey(), board.getPassword());
    	
    	if(existBoard > 0) {
    		//Dirty Checking을 통해 update - EntityManager로 ukey값에 맞는 데이터 조회 후 값을 새로 세팅하여 Transaction 종료 후 update 진행되게함
    		BoardEntity beforeModifiedBoard = em.find(BoardEntity.class, board.getUkey());
    		
	    	String subject = board.getSubject();
	    	String writer = board.getWriter();
	    	String content = board.getContent();
	    	
	    	if(subject != null && subject != "") {
	    		beforeModifiedBoard.updateSubject(subject);
	    	}
	    	if(writer != null && writer != "") {
	    		beforeModifiedBoard.updateWrite(writer);
	    	}
	    	if(content != null && content != "") {
	    		beforeModifiedBoard.updateContent(content);
	    	}
	    	beforeModifiedBoard.updateModifydate(LocalDateTime.now());
    	}
    	return boardRepository.findByUkey(board.getUkey());
    }
    
    public int deleteBoard(BoardEntity board){
    	int deleteCnt = 0;
    	int existBoard = boardRepository.countByUkeyAndPassword(board.getUkey(), board.getPassword());
    	if(existBoard > 0) {
    		boardRepository.deleteById(board.getUkey());
    		deleteCnt++;
    	}
    	return deleteCnt;
    }
}
