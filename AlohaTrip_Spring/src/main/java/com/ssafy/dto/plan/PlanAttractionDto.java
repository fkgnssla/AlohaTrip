package com.ssafy.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanAttractionDto {
	int planAttractionId;
	int planId;
	int contentId; // attractionId
	int order;
	String memo;

}
