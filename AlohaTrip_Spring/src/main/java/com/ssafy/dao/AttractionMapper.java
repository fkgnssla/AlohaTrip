package com.ssafy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.attraction.AttractionDto;


@Mapper
public interface AttractionMapper {
	public AttractionDto getOne(int contentId) throws Exception;
	public List<AttractionDto> getList() throws Exception;
	public List<AttractionDto> getListByCodeAndKeyword(Integer sidoCode, Integer gugunCode, Integer contentId, String keyword) throws Exception;
}
