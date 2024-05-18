package com.ssafy.dto;

import lombok.Data;

@Data
public class FindBoardDto {
	private Long id;
	
	private String name;
	
	private String title;
	
	private String contents;
	
	private int views;
	
	private int likes;
	
	private String createdDate;
	
}