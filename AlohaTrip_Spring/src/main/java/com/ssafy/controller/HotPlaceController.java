package com.ssafy.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ssafy.model.PrincipalDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.hotPlace.HotPlaceCreateAndUpdateDto;
import com.ssafy.dto.hotPlace.HotPlaceDto;
import com.ssafy.service.HotPlaceSerevice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/bragOfHotPlace")
@RestController
public class HotPlaceController {
	private final HotPlaceSerevice hotPlaceService; 
	
	@Value("${file.path}")
	private String uploadPath;
	
	@PostMapping("/create")
	public ResponseEntity<?> postMethodName(
			@RequestPart String memberId, 
			@RequestPart String hotPlaceName, 
			@RequestPart String visitedDate, 
			@RequestPart String address, 
			@RequestPart MultipartFile ImgInfo,
			@RequestPart String contents, 
			@RequestPart String lat, 
			@RequestPart String lng
			) {
		System.out.println("/create");
		System.out.println(memberId + "/" + hotPlaceName + "/" + visitedDate + "/" + address + "/" + ImgInfo + "/" + contents + "/" + lat + "/" + lng);
		
		HotPlaceCreateAndUpdateDto hotPlace = new HotPlaceCreateAndUpdateDto();
		hotPlace.setMemberId(Integer.parseInt(memberId));
		hotPlace.setHotPlaceName(hotPlaceName);
		hotPlace.setAddress(address);
		hotPlace.setContents(contents);
		hotPlace.setLat(Double.parseDouble(lat));
		hotPlace.setLng(Double.parseDouble(lng));
		
		System.out.println(visitedDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		hotPlace.setVisitedDate(LocalDateTime.parse(visitedDate, formatter));
		
		
		if (!ImgInfo.isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			System.out.println("저장 폴더 : "+ saveFolder);
			File folder = new File(saveFolder);
			
			if (!folder.exists())
				System.out.println("폴더 생성");
				folder.mkdirs();
					
			String originalFileName = ImgInfo.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				System.out.println(UUID.randomUUID().toString());
				System.out.println(originalFileName.substring(originalFileName.lastIndexOf('.')));
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				System.out.println(saveFileName);
				try {
					ImgInfo.transferTo(new File(folder, saveFileName));
					hotPlace.setImgAddress(folder+"\\"+saveFileName);
				} catch (IllegalStateException e) {e.printStackTrace();}
				catch (IOException e) {e.printStackTrace();}
			}
		}
		hotPlaceService.create(hotPlace);
		return ResponseEntity.ok("HotPlace 게시글 생성 완료");
	}
	
	@GetMapping("/hotPlaceDetail")
	public ResponseEntity<?> findById(@Param("Id") long Id,  @AuthenticationPrincipal PrincipalDetail principalDetail) {
		System.out.println("bragOfHotPlace/hotPlaceDetail/${id} : " + Id);
		HotPlaceDto hotPlace = hotPlaceService.findById(Id, principalDetail == null ? null : principalDetail.getId());
		return ResponseEntity.ok().body(hotPlace);
	}
	
	@GetMapping("/hotPlaceBestList")
	public ResponseEntity<?> findBestHotPlacePost() {
		List<HotPlaceDto> hotPlaceDtoList = hotPlaceService.findBestHotPlacePost();
		return ResponseEntity.ok().body(hotPlaceDtoList);
	}
	
	@GetMapping("/hotPlaceList")
	public ResponseEntity<?> findPostByPageNum(@Param("pageNum") int pageNum) {
		//System.out.println("/hotPlaceList?page="+pageNum);
		List<HotPlaceDto> hotPlaceDtoList = hotPlaceService.findPostByPageNum(pageNum);
//		System.out.println("list의 크기 : " + hotPlaceDtoList.size());
//		for(int i = 0; i < hotPlaceDtoList.size(); i++) {
//			System.out.println(hotPlaceDtoList.get(i).toString());
//		}
		int lastPage = hotPlaceService.findLastPageNum();
		Map<String, Object> result = new HashMap<>();
		result.put("hotPlaceDtoList", hotPlaceDtoList);
		result.putIfAbsent("lastPage", lastPage);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> updateHotPlace(
			@RequestPart String hotPlaceId, 
			@RequestPart String hotPlaceName, 
			@RequestPart String visitedDate, 
			@RequestPart String address, 
			@RequestPart(required = false) MultipartFile ImgInfo,
			@RequestPart String contents, 
			@RequestPart String lat, 
			@RequestPart String lng
			) {
		//TODO: process POST request
		System.out.println("/updated");
		System.out.println(hotPlaceName + "/" + visitedDate + "/" + address + "/" + contents + "/" + lat + "/" + lng);
		if(ImgInfo != null) {
			System.out.println(ImgInfo);
		}
		System.out.println("==============================");
		
		HotPlaceCreateAndUpdateDto hotPlace = new HotPlaceCreateAndUpdateDto();
		hotPlace.setHotPlaceId(Long.parseLong(hotPlaceId));
		hotPlace.setHotPlaceName(hotPlaceName);
		hotPlace.setAddress(address);
		hotPlace.setContents(contents);
		hotPlace.setLat(Double.parseDouble(lat));
		hotPlace.setLng(Double.parseDouble(lng));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		hotPlace.setVisitedDate(LocalDateTime.parse(visitedDate, formatter));
		
		
		if (ImgInfo!= null && !ImgInfo.isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			System.out.println("저장 폴더 : "+ saveFolder);
			File folder = new File(saveFolder);
			
			if (!folder.exists())
				System.out.println("폴더 생성");
				folder.mkdirs();
					
			String originalFileName = ImgInfo.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				System.out.println(UUID.randomUUID().toString());
				System.out.println(originalFileName.substring(originalFileName.lastIndexOf('.')));
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				System.out.println(saveFileName);
				try {
					ImgInfo.transferTo(new File(folder, saveFileName));
					hotPlace.setImgAddress(folder+"\\"+saveFileName);
				} catch (IllegalStateException e) {e.printStackTrace();}
				catch (IOException e) {e.printStackTrace();}
			}
			hotPlaceService.update(hotPlace);
		}else {
			hotPlaceService.updateWithOutImg(hotPlace);
		}
		return ResponseEntity.ok("HotPlace 게시글 업데이트 완료");
	}
	
	@DeleteMapping("/delete/{Id}")
	public ResponseEntity<?> delete(@PathVariable long Id) {
		hotPlaceService.deleteById(Id);
		return ResponseEntity.ok("삭제 완료");
	}
	
}
