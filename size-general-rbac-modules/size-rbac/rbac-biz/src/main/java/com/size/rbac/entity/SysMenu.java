package com.size.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.size.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    private Long parentId;
    private String menuName;
    private Integer menuType;
    private String path;
    private String component;
    private String permission;
    private String icon;
    private Integer sort;
    private Integer visible;
    private Integer status;
}
