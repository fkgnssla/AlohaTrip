package com.ssafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
	Long memberId;
	String name;
	String id;
	String password;
	String email;
	String profileImgSrc;
	private String socialId;
	private String role;
}
