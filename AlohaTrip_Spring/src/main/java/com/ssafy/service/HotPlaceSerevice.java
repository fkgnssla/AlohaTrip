package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dao.HotPlaceMapper;
import com.ssafy.dto.hotPlace.HotPlaceCreateAndUpdateDto;
import com.ssafy.dto.hotPlace.HotPlaceDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HotPlaceSerevice {
	private final HotPlaceMapper hotPlaceMapper;
	
	public void create(HotPlaceCreateAndUpdateDto hotPlace) {
		hotPlace.setViews(0);
		hotPlace.setLikes(0);
		hotPlaceMapper.create(hotPlace);
	}
	
	public HotPlaceDto findById(Long id) {
		return hotPlaceMapper.findById(id);
	}
	
	public List<HotPlaceDto> findBestHotPlacePost(){
		return hotPlaceMapper.findBestHotPlacePost();
	}
	
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
	
	public void update(HotPlaceCreateAndUpdateDto hotPlace) {
		hotPlaceMapper.update(hotPlace);
	}
	
	public void updateWithOutImg(HotPlaceCreateAndUpdateDto hotPlace) {
		hotPlaceMapper.updateWithOutImg(hotPlace);
	}
	
	public void deleteById(Long id) {
		hotPlaceMapper.deleteById(id);
	}
}
