package com.study.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.board.domain.BoardEntity;
import com.study.board.repository.BoardViewMapping;
import com.study.board.service.BoardService;


@RestController
@RequestMapping("/api/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
    @GetMapping("/getList")
    public List<BoardViewMapping> getList(){
    	List<BoardViewMapping> resultList = boardService.getList();
    	System.out.println("결과 리스트 사이즈 : "+resultList.size());
    	return resultList;
    }
    
    @GetMapping("/getDetail/{ukey}")
    public BoardViewMapping getDetail(@PathVariable String ukey){
    	//@PathVariable - path에 있는 파라미터 사용
    	BoardViewMapping result = boardService.getDetail(ukey);
    	return result;
    }

    @PostMapping("/insertBoard")
    public BoardEntity insertBoard(@RequestBody BoardEntity board){
    	BoardEntity result = boardService.insertBoard(board);
    	return result;
    }
    
    @PostMapping("/updateBoard")
    public BoardEntity updateBoard(){
    	BoardEntity board = new BoardEntity();
    	BoardEntity result = boardService.updateBoard(board);
    	return result;
    }
    
    @PostMapping("/deleteBoard")
    public String deleteBoard(){
    	String id = "";
    	boardService.deleteBoard(id);
    	String result = "ok";
    	return result;
    }
}
