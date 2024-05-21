package com.ssafy.dto.plan;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanDto {
	Long memberId;
	int planId;	
	String title;
	String nickname;
	LocalDateTime startDate;
	LocalDateTime endDate;
	String imgSrc;

}
