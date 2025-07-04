package com.study.board.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.board.domain.BoardEntity;
import com.study.board.projection.BoardViewProjection;
import com.study.board.service.BoardService;
import com.study.util.exception.CustomException;
import com.study.util.exception.ErrorCode;


@RestController
@RequestMapping("/api")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
    @GetMapping("board/list")
    public List<BoardViewProjection> getList(){
    	return boardService.getList();
    }
    
    @GetMapping("board/{ukey}")//@PathVariable - path에 있는 파라미터 사용
    public BoardViewProjection getDetail(@PathVariable String ukey){
    	return boardService.getDetail(ukey);
    }

    @PostMapping("board")
    public BoardEntity insertBoard(@RequestBody BoardEntity board){
    	return boardService.insertBoard(board);
    }
    
    @PatchMapping("board")
    public BoardViewProjection updateBoard(@RequestBody BoardEntity board){
		if(StringUtils.isEmpty(board.getUkey()) || StringUtils.isEmpty(board.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}
    	
    	BoardViewProjection resultView = null;
    	boolean isUpdated = boardService.updateBoard(board);
    	if(isUpdated) {
    		resultView = boardService.getDetail(board.getUkey());
    	}

    	return resultView;
    }
    
    @DeleteMapping("board")
    public String deleteBoard(@RequestBody BoardEntity board){
		if(StringUtils.isEmpty(board.getUkey()) || StringUtils.isEmpty(board.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}
    	
    	boardService.deleteBoard(board);
    	return "ok";
    }
}
