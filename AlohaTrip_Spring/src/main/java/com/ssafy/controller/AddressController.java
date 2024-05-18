package com.ssafy.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.address.GugunDto;
import com.ssafy.dto.address.SidoDto;
import com.ssafy.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AddressController", description = "주소에 관련된 API 요청을 처리하는 Controller")
@RequestMapping("/address")
@RestController
public class AddressController {

	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}

	@Operation(summary = "시, 도 목록", description = "모든 시, 도 목록을 반환해 줍니다.")
	@GetMapping("/sido")
	public ResponseEntity<List<SidoDto>> findSidoList() throws Exception {
		List<SidoDto> list = addressService.getSidoList();
		return ResponseEntity.ok(list);
	}
	
	@Operation(summary = "구, 군 목록", description = "모든 구, 군 목록을 반환해 줍니다.")
	@GetMapping("/gugun")
	public ResponseEntity<List<GugunDto>> findGugunList(@Param("sidoCode") String sidoCode) throws Exception {
		List<GugunDto> list = null;
		if(sidoCode == null || sidoCode.equals("")) {
			list = addressService.getGugunList();
		}else {
			list = addressService.getGugunList(Integer.parseInt(sidoCode));
		}
		
		return ResponseEntity.ok(list);
	}
	
	
}
