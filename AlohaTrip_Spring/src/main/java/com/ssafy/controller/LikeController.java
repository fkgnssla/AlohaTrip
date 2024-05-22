package com.ssafy.controller;

import com.ssafy.dto.like.LikeDto;
import com.ssafy.model.PrincipalDetail;
import com.ssafy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/like")
@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<?> createLike(@RequestBody LikeDto likeDto) {
        likeService.createLike(likeDto);

        return ResponseEntity.ok("좋아요 생성 완료!");
    }

    @DeleteMapping("/delete/{hotPlaceId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long hotPlaceId, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        likeService.deleteLike(hotPlaceId, principalDetail.getId());

        return ResponseEntity.ok("좋아요 삭제 완료!");
    }

}
