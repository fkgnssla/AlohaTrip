package com.ssafy.controller;

import com.ssafy.dto.like.LikeDto;
import com.ssafy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLike(@RequestBody LikeDto likeDto) {
        likeService.deleteLike(likeDto);

        return ResponseEntity.ok("좋아요 삭제 완료!");
    }

}
