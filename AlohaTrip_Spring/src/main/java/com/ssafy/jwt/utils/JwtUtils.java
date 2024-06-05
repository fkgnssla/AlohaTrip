package com.ssafy.jwt.utils;

import com.ssafy.dto.member.MemberDto;
import com.ssafy.jwt.exception.CustomExpiredJwtException;
import com.ssafy.jwt.exception.CustomJwtException;
import com.ssafy.model.PrincipalDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JwtUtils {

    public static String secretKey = JwtConstants.key;

    // 헤더에 "Bearer XXX" 형식으로 담겨온 토큰을 추출한다
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public static String generateToken(Map<String, Object> valueMap, int validTime) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Authentication getAuthentication(String token) {
        Map<String, Object> claims = validateToken(token);

        Integer id = (Integer) claims.get("id");
        String name = (String) claims.get("name");
        String role = (String) claims.get("role");

//        MemberRole memberRole = MemberRole.valueOf(role);
        Long longId = (long) id;
        MemberDto member = MemberDto.builder().memberId(longId).name(name).role(role).build();

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole()));
        PrincipalDetail principalDetail = new PrincipalDetail(member, authorities);
        return new UsernamePasswordAuthenticationToken(principalDetail, "", authorities);
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        try {
            //jwt 토큰을 생성하기 위한 키를 생성
            SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                    .getBody();
        } catch(ExpiredJwtException expiredJwtException){
            log.error("만료된 토큰입니다.");
            throw new CustomExpiredJwtException("만료된 토큰입니다.", expiredJwtException);
        } catch(Exception e){
            log.error("존재하지 않는 토큰입니다.");
            throw new CustomJwtException("존재하지 않는 토큰입니다.");
        }
        return claim;
    }

    // 토큰이 만료되었는지 판단하는 메서드
    public static boolean isExpired(String token) {
        try {
            validateToken(token);
        } catch (Exception e) {
            return (e instanceof CustomExpiredJwtException);
        }
        return false;
    }

    // 토큰의 남은 만료시간 계산
    public static long tokenRemainTime(Integer expTime) {
        Date expDate = new Date((long) expTime * (1000));
        long remainMs = expDate.getTime() - System.currentTimeMillis();
        return remainMs / (1000 * 60);
    }

    // 토큰의 남은 만료시간 계산(분)
    public static long tokenRemainMinute(Integer expTime) {
        Date expDate = new Date((long) expTime * (1000));
        long remainMs = expDate.getTime() - System.currentTimeMillis();
        return remainMs / 1000;
    }
}
