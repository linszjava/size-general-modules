package com.size.rbac.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户 DTO（服务间调用，含密码用于认证）
 */
@Data
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private Integer status;
}
