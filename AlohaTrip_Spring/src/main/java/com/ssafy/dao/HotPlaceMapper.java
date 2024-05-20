package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.hotPlace.HotPlaceDto;

@Mapper
public interface HotPlaceMapper {
	public List<HotPlaceDto> findPostByPageNum(int startPostNum);
	public int findLastPageNum();
}
