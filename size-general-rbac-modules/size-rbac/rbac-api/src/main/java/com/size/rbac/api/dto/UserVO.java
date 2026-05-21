package com.size.rbac.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户展示 VO（不含密码）
 */
@Data
public class UserVO implements Serializable {

    private Long id;
    private String username;
    private String nickname;
    private String avatar;
}
