package com.size.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.size.common.core.constant.CommonConstants;
import com.size.common.core.exception.BizException;
import com.size.common.core.result.ResultCode;
import com.size.rbac.api.dto.AuthInfoDTO;
import com.size.rbac.api.dto.MenuDTO;
import com.size.rbac.api.dto.UserDTO;
import com.size.rbac.api.dto.UserVO;
import com.size.rbac.entity.SysMenu;
import com.size.rbac.entity.SysUser;
import com.size.rbac.mapper.SysMenuMapper;
import com.size.rbac.mapper.SysRoleMapper;
import com.size.rbac.mapper.SysUserMapper;
import com.size.rbac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public UserDTO getByUsername(String username) {
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public AuthInfoDTO getAuthInfo(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }

        List<String> roleKeys = sysRoleMapper.selectRoleKeysByUserId(userId);
        boolean superAdmin = roleKeys.contains(CommonConstants.SUPER_ADMIN_ROLE);

        List<SysMenu> menus = superAdmin
                ? sysMenuMapper.selectAllRouteMenus()
                : sysMenuMapper.selectMenusByUserId(userId).stream()
                        .filter(m -> m.getMenuType() != null && m.getMenuType() <= 1)
                        .collect(Collectors.toList());

        List<String> permissions = superAdmin
                ? sysMenuMapper.selectAllPermissions()
                : sysMenuMapper.selectPermissionsByUserId(userId);

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setAvatar(user.getAvatar());

        AuthInfoDTO authInfo = new AuthInfoDTO();
        authInfo.setUser(userVO);
        authInfo.setMenus(buildMenuTree(menus));
        authInfo.setPermissions(permissions);
        return authInfo;
    }

    private List<MenuDTO> buildMenuTree(List<SysMenu> menus) {
        List<MenuDTO> nodes = menus.stream().map(this::toMenuDTO).collect(Collectors.toList());
        List<MenuDTO> roots = new ArrayList<>();
        for (MenuDTO node : nodes) {
            Long parentId = node.getParentId() == null ? 0L : node.getParentId();
            if (parentId == 0L) {
                roots.add(node);
            } else {
                nodes.stream()
                        .filter(p -> p.getId().equals(parentId))
                        .findFirst()
                        .ifPresent(parent -> {
                            if (parent.getChildren() == null) {
                                parent.setChildren(new ArrayList<>());
                            }
                            parent.getChildren().add(node);
                        });
            }
        }
        return roots;
    }

    private MenuDTO toMenuDTO(SysMenu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setParentId(menu.getParentId());
        dto.setMenuName(menu.getMenuName());
        dto.setMenuType(menu.getMenuType());
        dto.setPath(menu.getPath());
        dto.setComponent(menu.getComponent());
        dto.setPermission(menu.getPermission());
        dto.setIcon(menu.getIcon());
        return dto;
    }
}
