package com.yzpocket.esg_showcase_app.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    ROLE_USER("ROLE_USER", "일반 사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_MENTOR("ROLE_MENTOR", "멘토"),
    ROLE_TUTOR("ROLE_TUTOR", "교강사"),
    ROLE_BIZ("ROLE_BIZ", "기업담당자");

    private final String authority;
    private final String description;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}