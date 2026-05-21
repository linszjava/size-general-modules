package com.size.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.size.common.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private String roleName;
    private String roleKey;
    private Integer sort;
    private Integer status;
    private String remark;
}
