package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.plan.PlanAttractionDto;
import com.ssafy.dto.plan.PlanAttractionInfoDto;
import com.ssafy.dto.plan.PlanDto;
import com.ssafy.dto.plan.PlanInfoDto;

@Mapper
public interface PlanMapper {
	public PlanDto getPlanDto(int planId) throws Exception;
	public List<PlanDto> getPlanDtoList(int memberId) throws Exception;
	public List<PlanAttractionDto> getPlanAttractionDtoList(int planId) throws Exception;
	public List<PlanAttractionInfoDto> getPlanAttractionInfoDtoList(int planId) throws Exception;
	public PlanInfoDto getPlanInfoDto(int planId) throws Exception;	
	public int savePlan(PlanDto planDto) throws Exception;
	public int savePlanAttraction(PlanAttractionDto planAttractionDto) throws Exception;
	public void savePlanAttractions(List<PlanAttractionDto> planAttractionDtos) throws Exception;
	public void savePlanAndPlanAttractions(int planId, List<PlanAttractionDto> planAttractionDtos) throws Exception;
	public int update(PlanAttractionDto planAttractionDto) throws Exception;
	public int updateOrder(int planAttractionId, int order) throws Exception;
	public int deletePlan(int planId) throws Exception;
	public void deletePlanAttraction(int planAttractionId) throws Exception;
	public void deletePlanAttractionByPlanId(int planId) throws Exception;
}