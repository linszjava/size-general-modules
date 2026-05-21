-- 初始化数据（可重复执行，INSERT IGNORE 避免主键冲突）
-- 默认账号: admin / admin123

INSERT IGNORE INTO sys_role (id, role_name, role_key, sort, status, deleted, remark) VALUES
(1, '超级管理员', 'super_admin', 1, 0, 0, '拥有所有权限');

INSERT IGNORE INTO sys_user (id, username, password, nickname, status, deleted, dept_id) VALUES
(1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 0, 0, NULL);

INSERT IGNORE INTO sys_user_role (user_id, role_id) VALUES (1, 1);

INSERT IGNORE INTO sys_menu (id, parent_id, menu_name, menu_type, path, component, permission, icon, sort, deleted, status, visible) VALUES
(1,  0, '系统管理', 0, '/system',     NULL,                    NULL,                  'SettingOutlined', 1, 0, 0, 0),
(2,  1, '用户管理', 1, '/system/user', 'system/user/index',     'system:user:list',    'UserOutlined',    1, 0, 0, 0),
(3,  1, '角色管理', 1, '/system/role', 'system/role/index',     'system:role:list',    'TeamOutlined',    2, 0, 0, 0),
(4,  1, '菜单管理', 1, '/system/menu', 'system/menu/index',     'system:menu:list',    'MenuOutlined',    3, 0, 0, 0),
(5,  1, '部门管理', 1, '/system/dept', 'system/dept/index',     'system:dept:list',    'ApartmentOutlined', 4, 0, 0, 0),
(10, 2, '新增用户', 2, NULL,           NULL,                    'system:user:add',     NULL, 1, 0, 0, 0),
(11, 2, '编辑用户', 2, NULL,           NULL,                    'system:user:edit',    NULL, 2, 0, 0, 0),
(12, 2, '删除用户', 2, NULL,           NULL,                    'system:user:delete',  NULL, 3, 0, 0, 0);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 10), (1, 11), (1, 12);
