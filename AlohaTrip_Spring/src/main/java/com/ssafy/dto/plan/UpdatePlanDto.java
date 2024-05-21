package com.ssafy.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePlanDto {
	int planId;
	String title;
	LocalDateTime startDate;
	LocalDateTime endDate;
}
