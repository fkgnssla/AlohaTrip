package com.ssafy.dto.plan;

import java.util.Map;

import com.ssafy.dto.attraction.AttractionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanAttractionInfoDto {
	int planAttractionId;
	int order;
	String memo;
	Map<String, String> moveDataMap;
	AttractionDto attractionDto; // attractionId
	
	
}
