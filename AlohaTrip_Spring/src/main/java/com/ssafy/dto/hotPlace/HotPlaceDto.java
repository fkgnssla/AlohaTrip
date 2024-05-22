package com.ssafy.dto.hotPlace;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotPlaceDto {
	public Long hotPlaceId;
	public String writerName;
	public String HotPlaceName;
	public String address;
	public String contents;
	public String ImgAddress;
	public double lat;
	public double lng;
	public int views;
	public int likes;
	public LocalDateTime createdDate;
	public LocalDateTime visitedDate;
	
}
