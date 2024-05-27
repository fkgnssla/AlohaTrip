package com.ssafy.oauth2.service;

import com.ssafy.dao.MemberMapper;
import com.ssafy.dto.MemberDto;
import com.ssafy.dto.MemberRole;
import com.ssafy.model.PrincipalDetail;
import com.ssafy.oauth2.user.KakaoUserInfo;
import com.ssafy.oauth2.user.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper memberMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("=== OAuth2UserService 실행 ===");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("OAuth2User = {}", oAuth2User);
        log.info("attributes = {}", attributes);

        // 사용자 이름(Username)에 해당하는 속성(Attribute)의 이름 추출
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        log.info("nameAttributeKey = {}", userNameAttributeName);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId = {}", registrationId);

        String socialId;
        String name;
        String profileImgSrc;

        if ("kakao".equals(registrationId)) {
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes, userNameAttributeName);
            socialId = kakaoUserInfo.getSocialId();
            name = kakaoUserInfo.getName();
            profileImgSrc = kakaoUserInfo.getProfileImage();
        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);
            for (String string : response.keySet()) {
                System.out.println(string + ": " + response.get(string));
            }
            NaverUserInfo naverUserInfo = new NaverUserInfo(response, "id");
            socialId = naverUserInfo.getSocialId();
            name = naverUserInfo.getName();
            profileImgSrc = naverUserInfo.getProfileImage();
        } else {
            socialId = name = profileImgSrc = null;
        }

        // 소셜 ID 로 사용자를 조회, 없으면 socialId 와 이름으로 사용자 생성
        Optional<MemberDto> bySocialId = memberMapper.findBySocialId(socialId);
        MemberDto member = bySocialId.orElseGet(() -> saveSocialMember(socialId, name, profileImgSrc));
        System.out.println(member);

        return new PrincipalDetail(member, Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
                attributes);
    }

    // 소셜 ID로 가입된 사용자가 없으면 새로운 사용자를 만들어 저장한다
    public MemberDto saveSocialMember(String socialId, String name, String profileImgSrc) {
        log.info("=== 새로운 소셜 로그인 사용자 추가 ===");
        MemberDto newMember = MemberDto.builder().socialId(socialId).name(name).profileImgSrc(profileImgSrc).role(MemberRole.USER.getValue()).build();
        memberMapper.memberRegister(newMember);
        return memberMapper.findBySocialId(newMember.getSocialId()).get();
    }
}
