package com.ssafy.oauth2.user;

import java.util.Map;

public class NaverUserInfo {
    private Map<String, Object> attributes;
    private String userNameAttributeName;

    public NaverUserInfo(Map<String, Object> attributes, String userNameAttributeName) {
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    public String getSocialId() {
        return (String) attributes.get(userNameAttributeName);
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getProfileImage() {
        return (String) attributes.get("profile_image");
    }
}

