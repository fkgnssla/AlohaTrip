package com.ssafy.service;

import com.ssafy.dao.LikeMapper;
import com.ssafy.dto.like.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeMapper likeMapper;

    public void createLike(LikeDto likeDto) {
        likeMapper.createLike(likeDto);
    }

    public void deleteLike(LikeDto likeDto) {
        likeMapper.deleteLike(likeDto);
    }
}
