package com.ssafy.jwt.controller;

import com.ssafy.jwt.exception.CustomJwtException;
import com.ssafy.jwt.utils.JwtConstants;
import com.ssafy.jwt.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {

//    private final RedisUtil redisUtil;

    @RequestMapping("/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, @RequestHeader("refreshToken") String refreshToken) {
        log.info("Refresh Token = {}", refreshToken);
        if (authHeader == null) {
            throw new CustomJwtException("Access Token 이 존재하지 않습니다");
        } else if (!authHeader.startsWith(JwtConstants.JWT_TYPE)) {
            throw new CustomJwtException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
        }

        String accessToken = JwtUtils.getTokenFromHeader(authHeader);

        // Access Token 의 만료 여부 확인 (만료된 경우에만 재발급)
        if (!JwtUtils.isExpired(accessToken)) {
            return Map.of("Access Token", accessToken, "Refresh Token", refreshToken);
        }

        // refreshToken 검증 후 새로운 토큰 생성 후 전달
        Map<String, Object> claims = JwtUtils.validateToken(refreshToken);

        // Redis에 기존 refreshToken 있는 지 확인
//        if(redisUtil.get(refreshToken) == null) {
//            log.info("RefreshToken 재발급 실패: 탈취 당한 토큰!");
//            return Map.of("RefreshToken 재발급 실패", "탈취 당한 토큰!");
//        }

        // accessToken 재발급
        String newAccessToken = JwtUtils.generateToken(claims, JwtConstants.ACCESS_EXP_TIME);

        // Redis에 기존 refreshToken 삭제
//        redisUtil.delete(refreshToken);

        // refreshToken 재발급
        String newRefreshToken = JwtUtils.generateToken(claims, JwtConstants.REFRESH_EXP_TIME);

        // Redis에 새로운 refreshToken 추가
//        redisUtil.set(newRefreshToken, "refreshToken", (int)JwtUtils.tokenRemainMinute((Integer) claims.get("exp")));

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }
}
