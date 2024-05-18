package com.ssafy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Board {
	private Long id;
	
	private long memberId;
	
	private String title;
	
	private String contents;
	
	private int views;
	
	private int Likes;
	
}
