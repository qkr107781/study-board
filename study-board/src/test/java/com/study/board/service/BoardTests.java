package com.study.board.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.study.board.domain.BoardEntity;
import com.study.board.exception.CustomException;
import com.study.board.exception.ErrorCode;
import com.study.board.projection.BoardViewProjection;
import com.study.board.repository.BoardRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BoardTests {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	EntityManager em;
	
	@Test
	@DisplayName("목록 조회 성공 - list가 null이 아니면")
	void listSuccess() {
		//Given
		//When
		BoardService boardServie = new BoardService(boardRepository, em);
		List<BoardViewProjection> boardList = boardServie.getList();
		
		//Then
		assertNotNull(boardList);
	}
	
	@Test
	@DisplayName("목록 조회 실패 - DB에서 조회할 게시판 데이터가 없는 경우")
	void listFailByEmpty() {
		//Given
		BoardRepository mockRepo = Mockito.mock(BoardRepository.class);
		Mockito.when(mockRepo.findAllByOrderByRegdateDesc()).thenThrow(new CustomException(ErrorCode.BOARD_EMPTY));
		
		//When
		CustomException exception = assertThrows(CustomException.class,
													() -> new BoardService(mockRepo, em).getList());
		
		//Then
		assertEquals(ErrorCode.BOARD_EMPTY, exception.getErrorCode());	
	}
	
	@Test
	@DisplayName("상세 조회 성공 - 조회된 게시글이 존재")
	void detailSuccess() {
		//Given
		
		//When
		BoardService boardServie = new BoardService(boardRepository, em);
		BoardViewProjection boardView =  boardServie.getDetail("1");
		
		//Then
		assertNotNull(boardView);
	}
	
	@Test
	@DisplayName("상세 조회 실패 - DB에서 조회할 게시판 데이터가 없는 경우")
	void detailFailByEmpty() {
		//Given
		//When
		CustomException exception = assertThrows(CustomException.class,
													() -> new BoardService(boardRepository, em).getDetail("A"));
		
		//Then
		assertEquals(ErrorCode.BOARD_EMPTY, exception.getErrorCode());	
	}
	
	@Test
	@DisplayName("작성 성공")
	void insertSuccess() {
		//Given
		 BoardEntity entity = BoardEntity.builder()
				 							.subject("test insert")
				 							.writer("test writer")
				 							.content("test content")
				 							.password("12345")
				 							.build();
		
		//When
		BoardService boardServie = new BoardService(boardRepository, em);
		BoardEntity insertEntity =  boardServie.insertBoard(entity);
		
		//Then
		assertNotNull(insertEntity);
		assertEquals(entity.getSubject(), insertEntity.getSubject());
		assertEquals(entity.getWriter(), insertEntity.getWriter());
		assertEquals(entity.getContent(), insertEntity.getContent());
		assertEquals(entity.getPassword(), insertEntity.getPassword());
	}
	
	@Test
	@DisplayName("수정 성공")
	void updateSuccess() {
		//Given
		 BoardEntity entity = BoardEntity.builder()
				 							.ukey("1")
				 							.password("1")
				 							.subject("test update")
				 							.build();
		
		//When
		BoardService boardServie = new BoardService(boardRepository, em);
		boolean updateEntity =  boardServie.updateBoard(entity);
		
		//Then
		assertTrue(updateEntity);
	}

	@Test
	@DisplayName("수정 실패 - 게시글 password가 틀린 경우 - status code 601 리턴")
	void updateFailByWrongPassword() {
		//Given
		BoardEntity entityWrongPassword = BoardEntity.builder()
				.subject("test update")
				.ukey("1")
				.password("A")
				.build();
		
		//When
		CustomException entityWrongPasswordException = assertThrows(CustomException.class,
				() -> new BoardService(boardRepository, em).updateBoard(entityWrongPassword));
		
		//Then
		assertEquals(ErrorCode.INVALID_PASSWORD, entityWrongPasswordException.getErrorCode());	
	}
	
	@Test
	@DisplayName("삭제 성공")
	void deleteSuccess() {
		//Given
		 BoardEntity entity = BoardEntity.builder()
				 							.ukey("1")
				 							.password("1")
				 							.build();
		
		//When
		BoardService boardServie = new BoardService(boardRepository, em);
		boardServie.deleteBoard(entity);
		//삭제 후 해당 Ukey로 조회
		CustomException exception = assertThrows(CustomException.class,
													() -> new BoardService(boardRepository, em).getDetail(entity.getUkey()));
		
		//Then
		assertEquals(ErrorCode.BOARD_EMPTY, exception.getErrorCode());	
	}

	@Test
	@DisplayName("삭제 실패 - 게시글 password가 틀린 경우 - status code 601 리턴")
	void deleteFailByWrongPassword() {
		//Given
		BoardEntity entityWrongPassword = BoardEntity.builder()
				.ukey("1")
				.password("A")
				.build();
		
		//When
		CustomException entityWrongPasswordException = assertThrows(CustomException.class,
				() -> new BoardService(boardRepository, em).deleteBoard(entityWrongPassword));
		
		//Then
		assertEquals(ErrorCode.INVALID_PASSWORD, entityWrongPasswordException.getErrorCode());
	}
}
