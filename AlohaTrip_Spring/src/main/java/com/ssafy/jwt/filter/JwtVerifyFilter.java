package com.ssafy.jwt.filter;

import com.google.gson.Gson;
import com.ssafy.jwt.exception.CustomExpiredJwtException;
import com.ssafy.jwt.exception.CustomJwtException;
import com.ssafy.jwt.utils.JwtConstants;
import com.ssafy.jwt.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class JwtVerifyFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/signUp", "/login" , "/refresh", "/", "/index.html"
        ,"/address/*", "/attraction/*", "/plan/totalPage", "/plan/listAll", "/plan/info/*", "/bragOfHotPlace/hotPlaceList", "/bragOfHotPlace/hotPlaceBestList",
        "/bragOfHotPlace/hotPlaceDetail", "/tripInfoShare", "/tripInfoShare/*"};

    private static void checkAuthorizationHeader(String header) {
        if(header == null) {
            log.error("토큰이 존재하지 않습니다.");
            throw new CustomJwtException("토큰이 존재하지 않습니다.");
        } else if (!header.startsWith(JwtConstants.JWT_TYPE)) {
            throw new CustomJwtException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
        }
    }

    // 필터를 거치지 않을 URL 을 설정하고, true 를 return 하면 현재 필터를 건너뛰고 다음 필터로 이동, doFilterInternal() 이전에 실행
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI); //삭제해야함.

//       return true; //나중에 삭제해야함.
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtVerifyFilter 호출");

        String authHeader = request.getHeader(JwtConstants.JWT_HEADER);
        try {
            checkAuthorizationHeader(authHeader);   // header가 올바른 형식인지 체크
            System.out.println(authHeader);

            String token = JwtUtils.getTokenFromHeader(authHeader);
            Authentication authentication = JwtUtils.getAuthentication(token);

            log.info("authentication = {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);    // 다음 필터로 이동
        } catch (Exception e) {
            Gson gson = new Gson();
            String json = "";
            if (e instanceof CustomExpiredJwtException) {
                json = gson.toJson(Map.of("Token_Expired", e.getMessage()));
            } else {
                json = gson.toJson(Map.of("error", e.getMessage()));
            }

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(json);
            printWriter.close();
        }
    }
}
