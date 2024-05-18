package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dao.AttractionMapper;
import com.ssafy.dto.attraction.AttractionDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttractionService {

	private final AttractionMapper attractionMapper;
	
	public static AttractionService getInstance() {
		return null;
	}
	
	public AttractionDto getOne(int contentId) throws Exception{	
		return attractionMapper.getOne(contentId);
	}

	public List<AttractionDto> getList() throws Exception{
		return attractionMapper.getList();
	}

	public List<AttractionDto> getListByCodeAndKeyword(int sidoCode, int gugunCode, int contentId, String keyword) throws Exception{
		Integer sidoWrapper = sidoCode != -1 ? sidoCode : null;
		Integer gugunWrapper = gugunCode != -1 ? gugunCode : null;
		Integer contentIdWrapper = contentId != -1 ? contentId : null;
		
		return attractionMapper.getListByCodeAndKeyword(sidoWrapper, gugunWrapper, contentIdWrapper, keyword);
	}

}
