package com.size.rbac.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户认证信息（菜单 + 权限）
 */
@Data
public class AuthInfoDTO implements Serializable {

    private UserVO user;
    private List<MenuDTO> menus;
    private List<String> permissions;
}
