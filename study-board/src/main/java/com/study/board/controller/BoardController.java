package com.study.board.controller;

import java.util.List;

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
import com.study.board.repository.BoardViewMapping;
import com.study.board.service.BoardService;


@RestController
@RequestMapping("/api")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
    @GetMapping("board/list")
    public List<BoardViewMapping> getList(){
    	return boardService.getList();
    }
    
    @GetMapping("board/{ukey}")
    public BoardViewMapping getDetail(@PathVariable String ukey){
    	//@PathVariable - path에 있는 파라미터 사용
    	return boardService.getDetail(ukey);
    }

    @PostMapping("board")
    public BoardEntity insertBoard(@RequestBody BoardEntity board){
    	return boardService.insertBoard(board);
    }
    
    @PatchMapping("board")
    public BoardViewMapping updateBoard(@RequestBody BoardEntity board){
    	return boardService.updateBoard(board);
    }
    
    @DeleteMapping("board")
    public String deleteBoard(@RequestBody BoardEntity board){
    	int deleteCnt = boardService.deleteBoard(board);
    	String result = "ok";
    	if(deleteCnt <= 0) {
    		result = "fail";
    	}
    	return result;
    }
}
