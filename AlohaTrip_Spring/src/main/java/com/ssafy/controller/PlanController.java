package com.ssafy.controller;

import com.ssafy.dto.plan.UpdatePlanDto;
import com.ssafy.model.PrincipalDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ssafy.dto.plan.PlanAttractionDto;
import com.ssafy.dto.plan.PlanDto;
import com.ssafy.dto.plan.PlanInfoDto;
import com.ssafy.service.PlanService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {

    private static final Logger log = LoggerFactory.getLogger(PlanController.class);
    private final PlanService planService;
    
    @GetMapping("/list")
    public ResponseEntity<List<PlanDto>> getPlanList(@RequestParam(required = false) Integer memberId) throws Exception {
        List<PlanDto> planList = planService.getPlanDtoList(memberId != null ? memberId : -1);
        return ResponseEntity.ok(planList);
    }
    
    @GetMapping("/listAll")
    public ResponseEntity<List<PlanDto>> getPlanListAll(@RequestParam int page) throws Exception {
        List<PlanDto> planList = planService.getPlanDtoListAll(page);
        return ResponseEntity.ok(planList);
    }

    //총 페이지 수를 반환
    @GetMapping("/totalPage")
    public ResponseEntity<?> findTotalPage() throws Exception {
        int totalPage = planService.findTotalPage();
        return ResponseEntity.ok(totalPage);
    }

    @GetMapping("/info/{planId}")
    public ResponseEntity<PlanInfoDto> getPlanInfo(@PathVariable int planId) throws Exception {
        PlanInfoDto planInfo = planService.getPlanInfo(planId);
        return ResponseEntity.ok(planInfo);
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createPlan(@RequestBody PlanDto planDto) throws Exception {
        int planId = planService.save(planDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(planId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlan(@RequestBody UpdatePlanDto updatePlanDto) throws Exception {
        planService.updatePlan(updatePlanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("여행 계획 변경 완료!");
    }

    @PostMapping("/addRoute")
    public ResponseEntity<Void> addRoute(@RequestBody PlanAttractionDto planAttractionDto) throws Exception {
        planService.save(planAttractionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/deleteRoute/{planAttractionId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable int planAttractionId) throws Exception {
        planService.deletePlanAttraction(planAttractionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/copy")
    public ResponseEntity<Integer> copyPlan(@RequestBody PlanInfoDto planInfoDto, @AuthenticationPrincipal PrincipalDetail principalDetail) throws Exception {
        log.info("현재 사용자 ID: " + principalDetail.getId());
        planService.copyPlan(principalDetail.getId(), planInfoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(1);
    }

    @PostMapping("/shortenPath/{planId}")
    public ResponseEntity<Void> shortenPath(@PathVariable int planId) throws Exception {
        planService.shortenPath(planId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable int planId) throws Exception {
        planService.deletePlan(planId);
        return ResponseEntity.ok().build();
    }
}
