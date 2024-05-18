package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dao.BoardMapper;
import com.ssafy.dto.BoardDto;
import com.ssafy.dto.FindBoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardMapper boardMapper;
	
	public void create(BoardDto boardDto) {
		boardDto.setViews(0);
		boardDto.setLikes(0);
		boardMapper.create(boardDto);
	}
	
	public FindBoardDto findById(Long id) {
		return boardMapper.findById(id);
	}
	
	public List<FindBoardDto> findAll() {
		return boardMapper.findAll();
	}
	
	public List<FindBoardDto> findByKeyword(String keyword) {
		return boardMapper.findByKeyword(keyword);
	}
	
	public void update(BoardDto boardDto) {
		boardMapper.update(boardDto);
	}
	
	public void delete(Long id) {
		boardMapper.delete(id);
	}
}
