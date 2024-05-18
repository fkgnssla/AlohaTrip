package com.ssafy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TempController {
    @GetMapping("/secure")
    public Map<String, String> secureMethod(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Member식별자: " + userDetails.getUsername());
        return Map.of("success", "filter 성공");
    }
}
