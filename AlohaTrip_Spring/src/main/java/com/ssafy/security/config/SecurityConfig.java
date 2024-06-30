package com.ssafy.security.config;

import com.ssafy.jwt.filter.JwtVerifyFilter;
import com.ssafy.oauth2.service.OAuth2UserService;
import com.ssafy.security.handler.CommonLoginFailHandler;
import com.ssafy.security.handler.CommonLoginSuccessHandler;
import com.ssafy.security.handler.CustomAccessDeniedHandler;
import com.ssafy.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final OAuth2UserService oAuth2UserService;

    // 일반 로그인 하고 싶으면 일반 로그인 잡아주는 필터 생성, 시큐리티 설정만 잡아주면 될 듯
    //private final UserDetailsServiceImpl userDetailsService; //일반 로그인 서비스

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommonLoginSuccessHandler commonLoginSuccessHandler() {
        return new CommonLoginSuccessHandler();
    }

    @Bean
    public CommonLoginFailHandler commonLoginFailHandler() {
        return new CommonLoginFailHandler();
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter() {
        return new JwtVerifyFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/plan/**").hasAnyRole("USER") // 인가 테스트용
                .anyRequest().permitAll())
                .exceptionHandling((exceptionConfig) ->  {
                    exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
                    exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
                }
        );

        // JwtVerifyFilter -> UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin(AbstractHttpConfigurer::disable);

        http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                httpSecurityOAuth2LoginConfigurer
                        .successHandler(commonLoginSuccessHandler())
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService)));

        // 일반 로그인 추가해줘야함.

        return http.build();
    }
}
