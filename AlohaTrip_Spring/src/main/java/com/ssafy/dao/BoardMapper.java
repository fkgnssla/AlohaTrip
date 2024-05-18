package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.BoardDto;
import com.ssafy.dto.FindBoardDto;

@Mapper
public interface BoardMapper {
	public void create(BoardDto boardDto);
	
	public FindBoardDto findById(Long id);
	
	public List<FindBoardDto> findAll();
	
	public List<FindBoardDto> findByKeyword(String keyword);
	
	public void update(BoardDto boardDto);
	
	public void delete(Long id);
}
