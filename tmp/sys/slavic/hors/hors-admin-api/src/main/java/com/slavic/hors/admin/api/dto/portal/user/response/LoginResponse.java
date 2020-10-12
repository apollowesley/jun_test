package com.slavic.hors.admin.api.dto.portal.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String username;

    private Long avatarUrl;

    private String nickName;

    private String token;

}
