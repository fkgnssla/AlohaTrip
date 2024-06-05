package com.ssafy.dto.board;

import lombok.Data;

@Data
public class FindBoardDto {
	private Long id;

	private Long memberId;

	private String name;
	
	private String title;
	
	private String contents;
	
	private int views;
	
	private int likes;
	
	private String createdDate;
	
}