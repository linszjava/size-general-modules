package com.size.rbac.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单 DTO
 */
@Data
public class MenuDTO implements Serializable {

    private Long id;
    private Long parentId;
    private String menuName;
    private Integer menuType;
    private String path;
    private String component;
    private String permission;
    private String icon;
    private List<MenuDTO> children;
}
