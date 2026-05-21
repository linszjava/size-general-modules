-- 表结构（需先创建数据库 size_rbac，JDBC URL 已指向该库）

CREATE TABLE IF NOT EXISTS sys_dept (
    id          BIGINT       NOT NULL COMMENT '主键',
    parent_id   BIGINT       DEFAULT 0 COMMENT '父部门ID',
    dept_name   VARCHAR(64)  NOT NULL COMMENT '部门名称',
    sort        INT          DEFAULT 0 COMMENT '排序',
    leader      VARCHAR(64)  DEFAULT NULL COMMENT '负责人',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '联系电话',
    email       VARCHAR(64)  DEFAULT NULL COMMENT '邮箱',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT       NOT NULL COMMENT '主键',
    username    VARCHAR(64)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码',
    nickname    VARCHAR(64)  DEFAULT NULL COMMENT '昵称',
    email       VARCHAR(64)  DEFAULT NULL COMMENT '邮箱',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    avatar      VARCHAR(255) DEFAULT NULL COMMENT '头像',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    dept_id     BIGINT       DEFAULT NULL COMMENT '部门ID',
    remark      VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS sys_role (
    id          BIGINT       NOT NULL COMMENT '主键',
    role_name   VARCHAR(64)  NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(64)  NOT NULL COMMENT '角色标识',
    sort        INT          DEFAULT 0 COMMENT '排序',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    remark      VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_key (role_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE IF NOT EXISTS sys_menu (
    id          BIGINT       NOT NULL COMMENT '主键',
    parent_id   BIGINT       DEFAULT 0 COMMENT '父菜单ID',
    menu_name   VARCHAR(64)  NOT NULL COMMENT '菜单名称',
    menu_type   TINYINT      NOT NULL COMMENT '类型 0目录 1菜单 2按钮',
    path        VARCHAR(128) DEFAULT NULL COMMENT '路由路径',
    component   VARCHAR(128) DEFAULT NULL COMMENT '组件路径',
    permission  VARCHAR(64)  DEFAULT NULL COMMENT '权限标识',
    icon        VARCHAR(64)  DEFAULT NULL COMMENT '图标',
    sort        INT          DEFAULT 0 COMMENT '排序',
    visible     TINYINT      DEFAULT 0 COMMENT '是否可见 0可见 1隐藏',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

CREATE TABLE IF NOT EXISTS sys_permission (
    id              BIGINT       NOT NULL COMMENT '主键',
    permission_name VARCHAR(64)  NOT NULL COMMENT '权限名称',
    permission_key  VARCHAR(64)  NOT NULL COMMENT '权限标识',
    remark          VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_key (permission_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

CREATE TABLE IF NOT EXISTS sys_role_permission (
    role_id       BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';
