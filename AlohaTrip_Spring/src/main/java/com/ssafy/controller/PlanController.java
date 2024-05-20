package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
