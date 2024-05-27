package com.ssafy.oauth2.user;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo{

    public KakaoUserInfo(Map<String, Object> attributes, String userNameAttributeName) {
        super(attributes, userNameAttributeName);
        this.attributes = (Map<String, Object>) attributes.get("kakao_account");
        this.attributes = (Map<String, Object>) attributes.get("profile");
    }

    @Override
    public String getSocialId() {
        return socialId;
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("nickname"));
    }

    @Override
    public String getProfileImage() {
        return String.valueOf(attributes.get("profile_image_url"));
    }
}
