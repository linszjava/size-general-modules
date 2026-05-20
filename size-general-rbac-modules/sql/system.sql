-- =============================================
-- system-service 系统服务 SQL
-- 数据库: size_rbac
-- =============================================

USE size_rbac;

-- 字典类型表
CREATE TABLE IF NOT EXISTS sys_dict_type (
    id          BIGINT       NOT NULL COMMENT '主键',
    dict_name   VARCHAR(64)  NOT NULL COMMENT '字典名称',
    dict_type   VARCHAR(64)  NOT NULL COMMENT '字典类型',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    remark      VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS sys_dict_data (
    id          BIGINT       NOT NULL COMMENT '主键',
    dict_type   VARCHAR(64)  NOT NULL COMMENT '字典类型',
    dict_label  VARCHAR(64)  NOT NULL COMMENT '字典标签',
    dict_value  VARCHAR(64)  NOT NULL COMMENT '字典值',
    sort        INT          DEFAULT 0 COMMENT '排序',
    status      TINYINT      DEFAULT 0 COMMENT '状态 0正常 1停用',
    remark      VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 系统参数配置表
CREATE TABLE IF NOT EXISTS sys_config (
    id           BIGINT       NOT NULL COMMENT '主键',
    config_name  VARCHAR(64)  NOT NULL COMMENT '参数名称',
    config_key   VARCHAR(64)  NOT NULL COMMENT '参数键',
    config_value VARCHAR(255) NOT NULL COMMENT '参数值',
    remark       VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted      TINYINT      DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数配置表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS sys_oper_log (
    id             BIGINT        NOT NULL COMMENT '主键',
    title          VARCHAR(64)   DEFAULT NULL COMMENT '模块标题',
    business_type  TINYINT       DEFAULT 0 COMMENT '业务类型 0其他 1新增 2修改 3删除',
    method         VARCHAR(128)  DEFAULT NULL COMMENT '方法名称',
    request_method VARCHAR(16)   DEFAULT NULL COMMENT '请求方式',
    operator_type  TINYINT       DEFAULT 0 COMMENT '操作类别 0其他 1后台用户',
    oper_name      VARCHAR(64)   DEFAULT NULL COMMENT '操作人员',
    oper_url       VARCHAR(255)  DEFAULT NULL COMMENT '请求URL',
    oper_ip        VARCHAR(64)   DEFAULT NULL COMMENT '主机地址',
    oper_param     TEXT          DEFAULT NULL COMMENT '请求参数',
    json_result    TEXT          DEFAULT NULL COMMENT '返回参数',
    status         TINYINT       DEFAULT 0 COMMENT '操作状态 0正常 1异常',
    error_msg      TEXT          DEFAULT NULL COMMENT '错误消息',
    oper_time      DATETIME      DEFAULT NULL COMMENT '操作时间',
    create_time    DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted        TINYINT       DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_oper_time (oper_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- =============================================
-- 初始化数据
-- =============================================

INSERT INTO sys_dict_type (id, dict_name, dict_type, status) VALUES
(1, '用户性别', 'sys_user_sex', 0),
(2, '系统状态', 'sys_status', 0);

INSERT INTO sys_dict_data (id, dict_type, dict_label, dict_value, sort, status) VALUES
(1, 'sys_user_sex', '男', '0', 1, 0),
(2, 'sys_user_sex', '女', '1', 2, 0),
(3, 'sys_user_sex', '未知', '2', 3, 0),
(4, 'sys_status', '正常', '0', 1, 0),
(5, 'sys_status', '停用', '1', 2, 0);

INSERT INTO sys_config (id, config_name, config_key, config_value, remark) VALUES
(1, '系统名称', 'sys.name', 'Size RBAC Admin', '管理系统名称');
