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

    public void deleteLike(Long hotPlaceId, Long memberId) {
        likeMapper.deleteLike(hotPlaceId, memberId);
    }

    public int findByHotPlaceIdCount(Long hotPlaceId) {
        return likeMapper.findByHotPlaceIdCount(hotPlaceId);
    }

    public LikeDto findByMemberIdAndHotPlaceId(Long memberId, Long hotPlaceId) {
        return likeMapper.findByMemberIdAndHotPlaceId(memberId, hotPlaceId);
    }
}
