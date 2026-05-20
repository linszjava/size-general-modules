# 架构设计

## 一、整体架构

```
┌─────────────┐     ┌──────────────────┐     ┌─────────────────────────────┐
│ Vue3 Admin  │────▶│ Spring Cloud     │────▶│ 微服务集群                   │
│ (前端)      │     │ Gateway (网关)   │     │ auth / rbac / system        │
└─────────────┘     └──────────────────┘     └─────────────────────────────┘
                              │                          │
                              ▼                          ▼
                     ┌────────────────┐         ┌────────────────┐
                     │ Nacos          │         │ Redis / MySQL  │
                     │ 注册 + 配置    │         │ RabbitMQ       │
                     └────────────────┘         └────────────────┘
```

**核心原则：**

- RBAC 独立成服务，不与业务服务耦合
- 认证与授权分离：登录发 Token 在 `auth-service`，权限数据在 `rbac-service`
- 网关统一鉴权，业务服务做细粒度 `@SaCheckPermission` 校验

## 二、微服务拆分

| 服务 | 职责 | 端口（默认） |
|------|------|-------------|
| `gateway-service` | 路由、限流、统一鉴权、跨域 | 9500 |
| `auth-service` | 登录/登出、Token 刷新、验证码 | 9501 |
| `rbac-service` | 用户、角色、权限、菜单、部门 | 9502 |
| `system-service` | 字典、参数配置、操作日志 | 9503 |

**公共模块：**

| 模块 | 职责 |
|------|------|
| `common-core` | 统一响应、异常、常量、工具类 |
| `common-redis` | Redis 封装 |
| `common-mybatis` | MyBatis-Plus 配置 |
| `common-satoken` | Sa-Token 公共配置与注解 |
| `common-feign` | Feign 接口定义 |

> 初期可将 auth + rbac 合并为 `iam-service`，业务复杂后再拆分。

## 三、RBAC 数据模型

采用 **RBAC + 菜单 + 数据权限** 经典模型：

```
用户(User) ──N:N──> 角色(Role) ──N:N──> 权限(Permission)
                                              │
                                         菜单(Menu)
部门(Dept) ──树形结构──> 用于数据权限范围
```

### 核心表（rbac-service）

| 表 | 说明 |
|----|------|
| `sys_user` | 用户基本信息 |
| `sys_role` | 角色 |
| `sys_menu` | 菜单 + 按钮权限（type: 目录/菜单/按钮） |
| `sys_permission` | API 权限标识（如 `user:list`） |
| `sys_user_role` | 用户-角色关联 |
| `sys_role_menu` | 角色-菜单关联 |
| `sys_role_permission` | 角色-API 权限关联 |
| `sys_dept` | 部门树 |
| `sys_user_dept` | 用户所属部门 |

### 权限粒度

1. **菜单权限** — 控制前端路由/侧边栏
2. **按钮权限** — 控制页面内按钮（`v-permission` 指令）
3. **API 权限** — 网关/后端 `@SaCheckPermission`

## 四、Sa-Token 集成方案

### 登录流程

```
用户 → auth-service 校验账号密码
     → Sa-Token 生成 Token，存入 Redis
     → 返回 Token + 用户基本信息 + 菜单树 + 权限码列表
```

### 请求流程

```
前端带 Token → Gateway 校验 Token 有效性
             → 转发到业务服务
             → @SaCheckPermission("user:add") 细粒度校验
```

### 关键配置

- Token 存 Redis，支持集群
- 权限码列表登录时加载，缓存在 Redis
- 角色变更时通过 RabbitMQ 广播，清除对应用户权限缓存
- Gateway 使用 Sa-Token Reactor 版做统一拦截

## 五、通用模块复用设计

### Maven Starter 模式

业务项目引入 starter 即可接入 RBAC 能力：

```xml
<dependency>
    <groupId>com.size</groupId>
    <artifactId>size-rbac-starter</artifactId>
</dependency>
```

### 配置化开关

```yaml
size:
  rbac:
    enabled: true
    data-scope: true      # 是否启用数据权限
    multi-tenant: false   # 是否多租户
```

### 扩展点

- 登录方式可插拔（账号密码 / 手机号 / OAuth2）
- 权限校验策略可替换
- 菜单数据来源可切换（DB / 配置文件）

## 六、技术选型补充

| 方面 | 选型 |
|------|------|
| 服务注册/配置 | Nacos |
| 分布式事务 | 初期不需要 Seata，RBAC 单库即可 |
| 消息队列 | RabbitMQ（权限变更通知、操作日志异步） |
| 数据库 | 初期共库不同 schema，后期再拆库 |
| API 文档 | Knife4j（OpenAPI 3），网关聚合 |
| 代码生成 | MyBatis-Plus Generator |
| 部署 | Docker Compose 开发环境 |

## 七、注意事项

1. 不要一开始把每张表拆成独立服务，RBAC 表关联紧密
2. 权限缓存一致性：角色/权限变更后必须失效相关用户缓存
3. 预留 `super_admin` 角色绕过权限校验
4. 菜单与路由分离：DB 存菜单树，前端转换为 Router 配置
5. 统一错误码和响应格式，方便前端统一处理
