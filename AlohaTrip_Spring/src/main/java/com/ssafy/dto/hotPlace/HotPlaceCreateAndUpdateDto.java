package com.ssafy.dto.hotPlace;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HotPlaceCreateAndUpdateDto {
	private Long hotPlaceId;
	private int memberId;
	private String HotPlaceName;
	private String address;
	private String contents;
	private String ImgAddress;
	private double lat;
	private double lng;
	private int views;
	private int likes;
	private LocalDateTime visitedDate;
	
}
