package com.ssafy.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.dto.plan.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {
	public PlanDto getPlanDto(int planId) throws Exception;
	public List<PlanDto> getPlanDtoList(int memberId) throws Exception;
	public List<PlanDto> getPlanDtoListByPage(Map<String, Object> params) throws Exception;
	public int findTotalCount() throws Exception;
	public List<PlanAttractionDto> getPlanAttractionDtoList(int planId) throws Exception;
	public List<PlanAttractionInfoDto> getPlanAttractionInfoDtoList(int planId) throws Exception;
	public PlanInfoDto getPlanInfoDto(int planId) throws Exception;	
	public int savePlan(PlanDto planDto) throws Exception;
	public int savePlanAttraction(PlanAttractionDto planAttractionDto) throws Exception;
	public void savePlanAttractions(List<PlanAttractionDto> planAttractionDtos) throws Exception;
	public void savePlanAndPlanAttractions(int planId, List<PlanAttractionDto> planAttractionDtos) throws Exception;
	public int update(PlanAttractionDto planAttractionDto) throws Exception;
	public int updateOrder(int planAttractionId, int order) throws Exception;
	public int updatePlan(UpdatePlanDto updatePlanDto);
	public int deletePlan(int planId) throws Exception;
	public void deletePlanAttraction(int planAttractionId) throws Exception;
	public void deletePlanAttractionByPlanId(int planId) throws Exception;
	public void updateShareCount(int planId);
	public List<PlanDto> findTop3ByOrderByShareCountDesc() throws Exception;
}