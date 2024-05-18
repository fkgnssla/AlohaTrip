package com.ssafy.dto.plan;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanDto {
	int memberId;
	int planId;	
	String title;
	LocalDateTime startDate;
	LocalDateTime endDate;
	String imgSrc;

}
