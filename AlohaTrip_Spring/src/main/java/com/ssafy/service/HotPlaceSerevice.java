package com.ssafy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.dto.like.LikeDto;
import org.springframework.stereotype.Service;

import com.ssafy.dao.HotPlaceMapper;
import com.ssafy.dto.hotPlace.HotPlaceCreateAndUpdateDto;
import com.ssafy.dto.hotPlace.HotPlaceDto;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class HotPlaceSerevice {
	private final HotPlaceMapper hotPlaceMapper;
	private final LikeService likeService;
	private Map<Long, List<Long>> viewsMap = new HashMap<>();

	public void create(HotPlaceCreateAndUpdateDto hotPlace) {
		hotPlace.setViews(0);
		hotPlace.setLikes(0);
		hotPlaceMapper.create(hotPlace);
	}

	public HotPlaceDto findById(Long id, Long userId) {
		HotPlaceDto hotPlaceDto = hotPlaceMapper.findById(id);

		LikeDto likeDto = likeService.findByMemberIdAndHotPlaceId(hotPlaceDto.getMemberId(), hotPlaceDto.getHotPlaceId());
		if(likeDto == null) hotPlaceDto.setLikeFlag(false);
		else hotPlaceDto.setLikeFlag(true);

		List<Long> userIdList = viewsMap.computeIfAbsent(id, k -> new ArrayList<>());
		if (userId != null && !userIdList.contains(userId)) {
			userIdList.add(userId);
			updateViews(id, hotPlaceDto.getViews());
		}

		int likes = likeService.findByHotPlaceIdCount(id);
		hotPlaceDto.setLikes(likes);

		System.out.println(hotPlaceDto);
		return hotPlaceDto;
	}

	public void updateViews(Long id, int views) {
		hotPlaceMapper.updateViews(id, views + 1);
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
