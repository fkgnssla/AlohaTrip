package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.hotPlace.HotPlaceCreateAndUpdateDto;
import com.ssafy.dto.hotPlace.HotPlaceDto;

@Mapper
public interface HotPlaceMapper {
	public void create(HotPlaceCreateAndUpdateDto hotPlace);
	public HotPlaceDto findById(Long id);
	public List<HotPlaceDto> findBestHotPlacePost();
	public List<HotPlaceDto> findPostByPageNum(int startPostNum);
	public int findLastPageNum();
	public void update(HotPlaceCreateAndUpdateDto hotPlace);
	public void updateWithOutImg(HotPlaceCreateAndUpdateDto hotPlace);
	public void deleteById(Long id);
	public void updateViews(Long id, int views);
}
