package com.size.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.size.rbac.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);
}
