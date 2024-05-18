package com.ssafy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.dao.AddressMapper;
import com.ssafy.dto.address.GugunDto;
import com.ssafy.dto.address.SidoDto;

@Service
public class AddressService {

	private AddressMapper addressMapper;
	
	private AddressService(AddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}
	
	
	public List<SidoDto> getSidoList() throws Exception{
		return addressMapper.getSidoList();
	}

	public List<GugunDto> getGugunList() throws Exception {
		return addressMapper.getGugunList();
	}

	public List<GugunDto> getGugunList(int sidoCode) throws Exception {
		return addressMapper.getGugunListBySidoCode(sidoCode);
	}
}
