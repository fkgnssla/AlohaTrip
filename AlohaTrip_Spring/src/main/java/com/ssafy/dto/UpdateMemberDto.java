package com.ssafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateMemberDto {
	Long memberId;
	String name;
	String id;
	String password;
	String nickname;
}
