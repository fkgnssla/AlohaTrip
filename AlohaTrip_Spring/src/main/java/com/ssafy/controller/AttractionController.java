package com.ssafy.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.attraction.AttractionDto;
import com.ssafy.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "AttractionController", description = "관광지에 관련된 API 요청을 처리하는 Controller")
@RequestMapping("/attraction")
@RequiredArgsConstructor
@RestController
public class AttractionController {
	
	private final AttractionService attractionService;

	@Operation(summary = "관광지 목록", description = "시/도, 구/군, 키워드에 맞는 관광지 목록을 반환해 줍니다.")
	@GetMapping("/list")
	public ResponseEntity<List<AttractionDto>> findAttractionList(@Param("sidoCode") String sidoCode, 
			@Param("gugunCode") String gugunCode, @Param("contentId") int contentId, @Param("keyword") String keyword) throws Exception {
		int s = (sidoCode == null || sidoCode.equals("")) ? -1 : Integer.parseInt(sidoCode);
		int g = (gugunCode == null || gugunCode.equals("")) ? -1 : Integer.parseInt(gugunCode);
		
		List<AttractionDto> list = attractionService.getListByCodeAndKeyword(s,g,contentId,keyword);
		
		return ResponseEntity.ok(list);
	}
	
	
	@Operation(summary = "관광지 단일 조회", description = "식별자에 맞는 관광지를 반환해 줍니다.")
	@GetMapping("/view")
	public ResponseEntity<AttractionDto> findOne(@Param("contentId") int contentId) {
		try {
			AttractionDto attractionDto = attractionService.getOne(contentId);
			return ResponseEntity.ok(attractionDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
