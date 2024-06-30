package com.ssafy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.ssafy.dto.plan.*;
import com.ssafy.exception.plan.PlanNotFoundException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.dao.AttractionMapper;
import com.ssafy.dao.PlanMapper;
import com.ssafy.dto.attraction.AttractionDto;
import com.ssafy.util.LocationUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PlanService {
	private final PlanMapper planMapper;
	private final AttractionMapper attractionMapper;
	private final TMapApiService tMapService;
	private double minDist;
	private int[] shortestPath;

	public PlanInfoDto getPlanInfo(int planId) throws Exception {
		PlanInfoDto planInfoDto = planMapper.getPlanInfoDto(planId);

		if (planInfoDto == null) {
			planInfoDto = createNewPlanInfoDto(planId);
		} else {
			List<PlanAttractionInfoDto> attractions = planInfoDto.getAttractions();
			List<CompletableFuture<Void>> futures = createAndRunAsyncTasks(attractions);
			waitForAllFutures(futures);
		}
		return planInfoDto;
	}

	private PlanInfoDto createNewPlanInfoDto(int planId) throws Exception {
		PlanDto planDto = getPlanDto(planId);
		PlanInfoDto planInfoDto = new PlanInfoDto();
		planInfoDto.setPlanId(planId);
		planInfoDto.setTitle(planDto.getTitle());
		return planInfoDto;
	}

	private List<CompletableFuture<Void>> createAndRunAsyncTasks(List<PlanAttractionInfoDto> attractions) {
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (int i = 0; i < attractions.size() - 1; i++) {
			int index = i; // for lambda expression
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> processAttraction(attractions, index));
			futures.add(future);
		}
		return futures;
	}

	private void processAttraction(List<PlanAttractionInfoDto> attractions, int index) {
		try {
			AttractionDto a1 = attractions.get(index).getAttractionDto();
			AttractionDto a2 = attractions.get(index + 1).getAttractionDto();

			// 차량 이동 시간, 택시비 반환
			Map<String, String> moveDataMap = tMapService.carTime(a1.getLongitude(), a1.getLatitude(), a2.getLongitude(), a2.getLatitude());
			// 도보 이동 시간 반환
			String walkArrivalTime = tMapService.walkTime(a1.getLongitude(), a1.getLatitude(), a2.getLongitude(), a2.getLatitude());
			// 대중교통 이동 시간 반환
			String transportArrivalTime = tMapService.transportTime(a1.getLongitude(), a1.getLatitude(), a2.getLongitude(), a2.getLatitude());

			moveDataMap.put("walkArrivalTime", walkArrivalTime);
			moveDataMap.put("transportArrivalTime", transportArrivalTime);
			attractions.get(index).setMoveDataMap(moveDataMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForAllFutures(List<CompletableFuture<Void>> futures) {
		for (CompletableFuture<Void> future : futures) {
			try {
				// 각 CompletableFuture의 작업은 병렬 처리된다.
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public PlanDto getPlanDto(int planId) throws NotFoundException {
		PlanDto planDto = planMapper.getPlanDto(planId);

		if(planDto == null) throw new PlanNotFoundException();

		return planMapper.getPlanDto(planId);
	}

	public List<PlanAttractionDto> getPlanAttractionDtoList(int planId) throws Exception {
		return planMapper.getPlanAttractionDtoList(planId);
	}

	public void addFirstImg(List<PlanDto> planDtoList) throws Exception {
		for(PlanDto p : planDtoList) {
			List<PlanAttractionDto> planAttractionDto = getPlanAttractionDtoList(p.getPlanId());
			
			if(planAttractionDto.size() != 0) {
				int contentId = planAttractionDto.get(0).getContentId();
				p.setImgSrc(attractionMapper.getOne(contentId).getFirstImage());
			}
		}
	}
	
	public List<PlanDto> getPlanDtoList(int memberId) throws Exception {
		List<PlanDto> planDtoList = planMapper.getPlanDtoList(memberId);
		addFirstImg(planDtoList);
		return planDtoList;
	}
	
	public List<PlanDto> getPlanDtoListAll(int pageNum) throws Exception {
		int offset = (pageNum - 1) * 8;
		
        Map<String, Object> params = new HashMap();
        params.put("rowsPerPage", 8);
        params.put("offset", offset);
		
		
		List<PlanDto> planDtoList = planMapper.getPlanDtoListByPage(params);
		addFirstImg(planDtoList);
		return planDtoList;
	}
	
	public int findTotalPage() throws Exception {
		int totalPage = planMapper.findTotalCount();
		
		if(totalPage % 8 == 0) return totalPage / 8;
		else return totalPage /8 + 1;
	}

	public int save(PlanDto planDto) throws Exception {
		return planMapper.savePlan(planDto);
	}

	public int save(PlanAttractionDto planAttractionDto) throws Exception {
		return planMapper.savePlanAttraction(planAttractionDto);
	}
	
	public int save(PlanDto planDto, List<PlanAttractionDto> planAttractionDtos) throws Exception {
		int planId = -1;
		planId = planMapper.savePlan(planDto);
		planMapper.savePlanAndPlanAttractions(planId, planAttractionDtos);
		return planId;
	}

	public int updatePlan(UpdatePlanDto updatePlanDto) {
		return planMapper.updatePlan(updatePlanDto);
	}

	public void copyPlan(Long memberId, PlanInfoDto planInfoDto) throws Exception {
		//공유 수 갱신
		planMapper.updateShareCount(planInfoDto.getPlanId());

		//계획 생성
		PlanDto planDto = new PlanDto();
		planDto.setMemberId(memberId);
		planDto.setTitle(planInfoDto.getTitle());

		save(planDto);

		//루트 생성
		List<PlanAttractionInfoDto> attractions = planInfoDto.getAttractions();
		for (PlanAttractionInfoDto attraction : attractions) {
			PlanAttractionDto planAttractionDto = new PlanAttractionDto();
			planAttractionDto.setPlanId(planDto.getPlanId());
			planAttractionDto.setContentId(attraction.getAttractionDto().getContentId());
			planAttractionDto.setOrder(attraction.getOrder());
			planAttractionDto.setMemo(attraction.getMemo());

			save(planAttractionDto);
		}
	}

	public void deletePlanAttraction(int planAttractionId) throws Exception {
		planMapper.deletePlanAttraction(planAttractionId);
	}

	public List<PlanDto> findTop3ByOrderByShareCountDesc() throws Exception {
		List<PlanDto> planDtoList = planMapper.findTop3ByOrderByShareCountDesc();
		addFirstImg(planDtoList);
		return planDtoList;
	}

	public void shortenPath(int planId) throws Exception {
		// 1. plan_attraction에서 planId에 해당하는 경로 정보 가져오기
		List<PlanAttractionInfoDto> attractions = planMapper.getPlanAttractionInfoDtoList(planId);
		
		// 2. attraction 안에 있는 위/경도를 사용하여 출발지에서 도착지까지의 최단경로 구함
		// 	  단, 모든 경유지를 거쳐야 하므로 다익스트라는 안됨
		//	  즉, 백트래킹을 통해 경로를 구하면 좋을 것 같음
		
		// 최소 거리 초기화
		minDist = Double.MAX_VALUE;
		
		// 경로가 1개 이하라면 최단경로의 의미가 없으므로 return
		if(attractions.size() <= 1) return;
		
		// 최단경로 구하기
		backtrack(0, 0, 0, attractions, new boolean[attractions.size()-1], new int[attractions.size()]);
		
		// 변경된 순서 저장하기
		for (int i = 0; i < shortestPath.length; i++) {
			int idx = shortestPath[i];
			PlanAttractionInfoDto planAttractionInfoDto = attractions.get(idx);
			
			if(planAttractionInfoDto.getOrder() != i) {
				planMapper.updateOrder(planAttractionInfoDto.getPlanAttractionId(), i);
			}
		}
	}
	
	public void deletePlan(int planId) throws Exception {
		planMapper.deletePlanAttractionByPlanId(planId);
		planMapper.deletePlan(planId);
	}
	
	private void backtrack(int idx, int cnt, double totalDist, List<PlanAttractionInfoDto> attractions, boolean[] selected, int[] pathIndices) {
		if(minDist <= totalDist) return;
		
		// 현재 경유지(관광지) 정보 얻어오기
		AttractionDto cur = attractions.get(idx).getAttractionDto();
		
		int limit = attractions.size()-1;
		if(cnt == limit) {
			// 목적지 제외하고 다른 모든 곳을 돌았을 때
			AttractionDto dest = attractions.get(limit).getAttractionDto();
			double nextDist = totalDist+LocationUtil.distance(cur.getLatitude(), cur.getLongitude(), dest.getLatitude(), dest.getLongitude(), "meter");
			 
			if(nextDist < minDist) {
				// 만약 현재 거리가 최단 거리보다 짧다면 최단 거리 및 순서 갱신
				minDist = nextDist;
				shortestPath = pathIndices.clone();
				shortestPath[limit] = limit;
			}
			
			return;
		}
		
		// 다음 경유지(관광지) 구하기
		for (int i = 0; i < limit; i++) {
			if(selected[i]) continue;
			selected[i] = true;
			pathIndices[cnt] = i;
			// 다음 경유지(관광지) 정보 얻어오기
			AttractionDto next = attractions.get(i).getAttractionDto();
			// 현재 관광지와 다음 관광지간 거리 구하기
			double nextDist = totalDist+LocationUtil.distance(cur.getLatitude(), cur.getLongitude(), next.getLatitude(), next.getLongitude(), "meter");
			// 다음으로 이동
			backtrack(i, cnt+1, nextDist, attractions, selected, pathIndices);
			selected[i] = false;
		}
	}
}
