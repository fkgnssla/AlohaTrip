package com.ssafy.dto.plan;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlanInfoDto {
	int planId;	
	String title;
	List<PlanAttractionInfoDto> attractions;
	
	public PlanInfoDto(int planId, String title, List<PlanAttractionInfoDto> attractions) {
		super();
		this.planId = planId;
		this.title = title;
		this.attractions = attractions;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PlanAttractionInfoDto> getAttractions() {
		return attractions;
	}

	public void setAttractions(List<PlanAttractionInfoDto> attractions) {
		this.attractions = attractions;
	}

	@Override
	public String toString() {
		return "ResponsePlanDto [planId=" + planId + ", title=" + title + ", attractions=" + attractions + "]";
	}

	
}
