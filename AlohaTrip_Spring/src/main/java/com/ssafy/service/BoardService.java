package com.ssafy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.dao.BoardMapper;
import com.ssafy.dto.BoardDto;
import com.ssafy.dto.FindBoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardMapper boardMapper;
	private Map<Long, List<Long>> viewsMap = new HashMap<>();

	public void create(BoardDto boardDto) {
		boardDto.setViews(0);
		boardDto.setLikes(0);
		boardMapper.create(boardDto);
	}
	
	public FindBoardDto findById(Long id, Long userId) {
		FindBoardDto findBoardDto = boardMapper.findById(id);
		List<Long> userIdList = viewsMap.computeIfAbsent(id, k -> new ArrayList<>());
		if (userId != null && !userIdList.contains(userId)) {
			userIdList.add(userId);
			updateViews(id, findBoardDto.getViews());
		}

		return findBoardDto;
	}

	public void updateViews(Long id, int views) {
		boardMapper.updateViews(id, views+1);
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
