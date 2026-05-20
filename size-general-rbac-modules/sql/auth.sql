-- =============================================
-- auth-service 认证服务 SQL
-- 数据库: size_rbac
-- =============================================

USE size_rbac;

-- 登录日志表
CREATE TABLE IF NOT EXISTS sys_login_log (
    id          BIGINT       NOT NULL COMMENT '主键',
    user_id     BIGINT       DEFAULT NULL COMMENT '用户ID',
    username    VARCHAR(64)  DEFAULT NULL COMMENT '用户名',
    ip          VARCHAR(64)  DEFAULT NULL COMMENT '登录IP',
    location    VARCHAR(128) DEFAULT NULL COMMENT '登录地点',
    browser     VARCHAR(64)  DEFAULT NULL COMMENT '浏览器',
    os          VARCHAR(64)  DEFAULT NULL COMMENT '操作系统',
    status      TINYINT      DEFAULT 0 COMMENT '登录状态 0成功 1失败',
    message     VARCHAR(255) DEFAULT NULL COMMENT '提示消息',
    login_time  DATETIME     DEFAULT NULL COMMENT '登录时间',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_login_time (login_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';
