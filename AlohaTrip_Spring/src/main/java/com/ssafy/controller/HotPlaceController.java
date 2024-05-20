package com.ssafy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.hotPlace.HotPlaceDto;
import com.ssafy.service.HotPlaceSerevice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/bragOfHotPlace")
@RestController
public class HotPlaceController {
	private final HotPlaceSerevice hotPlaceService; 
	
	@PostMapping("/create")
	public ResponseEntity<?> postMethodName(@RequestParam MultipartFile hotPlaceFormData) {
		//TODO: process POST request
		System.out.println("test /create");
		System.out.println(hotPlaceFormData);
		return ResponseEntity.ok("HotPlace 게시글 생성 완료");
	}
	
	
	@GetMapping("/hotPlaceList")
	public ResponseEntity<?> findPostByPageNum(@Param("pageNum") int pageNum) {
		//System.out.println("/hotPlaceList?page="+pageNum);
		List<HotPlaceDto> hotPlaceDtoList = hotPlaceService.findPostByPageNum(pageNum);
		System.out.println("list의 크기 : " + hotPlaceDtoList.size());
		for(int i = 0; i < hotPlaceDtoList.size(); i++) {
			System.out.println(hotPlaceDtoList.get(i).toString());
		}
		
		int lastPage = hotPlaceService.findLastPageNum();
		Map<String, Object> result = new HashMap<>();
		result.put("hotPlaceDtoList", hotPlaceDtoList);
		result.putIfAbsent("lastPage", lastPage);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/{hotPlacePostId}")
	public String getMethodName(@RequestParam String param) {
		System.out.println("bragOfHotPlace/hotPlaceDetail/${id}");
		return new String();
	}
	
}
