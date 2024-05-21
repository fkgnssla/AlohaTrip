package com.ssafy.dto.plan;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanInfoDto {
	int planId;	
	String title;
	List<PlanAttractionInfoDto> attractions;
	
}
