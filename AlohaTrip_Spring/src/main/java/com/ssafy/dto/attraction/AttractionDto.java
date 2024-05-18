package com.ssafy.dto.attraction;

import com.ssafy.dto.plan.PlanAttractionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto {
	int contentId;
	int contentTypeId;
	String title;
	String addr1;
	String addr2;
	String zipcode;
	String tel;
	String firstImage;
	String firstImage2;
	int readCount;
	int sidoCode;
	int gugunCode;
	double latitude;
	double longitude;
	String mlevel;
	
}
