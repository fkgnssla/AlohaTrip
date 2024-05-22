package com.ssafy.controller;

import java.util.List;

import com.ssafy.model.PrincipalDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.FindBoardDto;
import com.ssafy.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/tripInfoShare")
@RestController
public class BoardController {
	private final BoardService boardService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody BoardDto boardDto) {
		boardService.create(boardDto);
		
		return ResponseEntity.ok("게시글 생성 완료");
	}
	
	@GetMapping("/{boardId}")
	public ResponseEntity<?> findById(@PathVariable long boardId, @AuthenticationPrincipal PrincipalDetail principalDetail) {
		FindBoardDto boardDto = boardService.findById(boardId, principalDetail == null ? null : principalDetail.getId());
		
		return ResponseEntity.ok(boardDto);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<FindBoardDto> boardDtos = boardService.findAll();
		
		return ResponseEntity.ok(boardDtos);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> findByKeyword(@Param("keyword") String keyword) {
		List<FindBoardDto> boardDtos = boardService.findByKeyword(keyword);
		return ResponseEntity.ok(boardDtos);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody BoardDto boardDto) {
		boardService.update(boardDto);
		
		return ResponseEntity.ok("게시글 수정 완료");
	}
	
	
	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<?> delete(@PathVariable long boardId) {
		boardService.delete(boardId);
		
		return ResponseEntity.ok("삭제 완료");
	}
}
