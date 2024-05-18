package com.ssafy.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardDto {
	private Long id;
	
	private Long memberId;
	
	private String title;
	
	private String contents;
	
	private int views;
	
	private int likes;
	
	private LocalDateTime createdDate;
	
}