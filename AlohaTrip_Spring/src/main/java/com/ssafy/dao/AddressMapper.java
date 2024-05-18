package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.address.GugunDto;
import com.ssafy.dto.address.SidoDto;


@Mapper
public interface AddressMapper {
	List<SidoDto> getSidoList() throws Exception;
	List<GugunDto> getGugunList() throws Exception;
	List<GugunDto> getGugunListBySidoCode(int sidoCode) throws Exception;
}
