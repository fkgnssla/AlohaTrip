package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dao.HotPlaceMapper;
import com.ssafy.dto.hotPlace.HotPlaceDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HotPlaceSerevice {
	private final HotPlaceMapper hotPlaceMapper;
	
	public List<HotPlaceDto> findPostByPageNum(int PageNum){
		//System.out.println("hotPlaceService");
		return hotPlaceMapper.findPostByPageNum((PageNum-1)*10);
	} 
	
	public int findLastPageNum() {
		int postNum  =  hotPlaceMapper.findLastPageNum();
		int lastPage = postNum/10;
		if(postNum%10 != 0) {
			lastPage++;
		}
		return lastPage;
	}
}
