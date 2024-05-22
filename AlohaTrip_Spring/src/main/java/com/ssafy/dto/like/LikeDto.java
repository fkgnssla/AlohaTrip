package com.ssafy.dto.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeDto {
    private Long likeId;
    private Long memberId;
    private Long hotPlaceId;
}
