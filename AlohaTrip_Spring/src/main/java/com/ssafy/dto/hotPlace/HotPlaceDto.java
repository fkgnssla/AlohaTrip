package com.ssafy.dto.hotPlace;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HotPlaceDto {
	private Long hotPlaceId;
	private String writerName;
	private String HotPlaceName;
	private String address;
	private String contents;
	private String ImgAddress;
	private double lat;
	private double lng;
	private int views;
	private int likes;
	private LocalDateTime createdDate;
	private LocalDateTime visitedDate;
	
}
