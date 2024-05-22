package com.ssafy.dao;

import com.ssafy.dto.like.LikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    public void createLike(LikeDto likeDto);

    public void deleteLike(LikeDto likeDto);
}
