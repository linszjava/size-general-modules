# 开发流程指南

## 分阶段开发计划

| 阶段 | 内容 | 产出 | 状态 |
|------|------|------|------|
| P0 基础 | 父工程、Gateway、Nacos、公共模块 | 服务能互相发现 | 骨架已生成 |
| P1 认证 | 登录/登出、Token、Redis 会话 | 能登录拿到 Token | 待开发 |
| P2 RBAC 核心 | 用户/角色/菜单/权限 CRUD | 完整权限管理后台 | 待开发 |
| P3 前端 | 动态路由、按钮权限、Admin 页面 | 可用的管理界面 | 待开发 |
| P4 增强 | 数据权限、操作日志、字典管理 | 企业级功能 | 待开发 |
| P5 复用 | Starter 封装、Docker Compose、文档 | 即插即用 | 待开发 |

## 本地环境要求

| 工具 | 版本 |
|------|------|
| JDK | 17+ |
| Maven | 3.8+ |
| Node.js | 18+ |
| MySQL | 8.0+ |
| Redis | 6.0+ |
| Nacos | 2.3+ |
| RabbitMQ | 3.12+（可选，P4 阶段） |

## 本地启动步骤

### 1. 启动基础设施

```bash
# 启动 MySQL，创建数据库（密码按本机实际填写）
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS size_rbac DEFAULT CHARSET utf8mb4;"

# 导入 SQL（按顺序，含默认账号 admin / admin123）
mysql -u root -p size_rbac < size-general-rbac-modules/sql/rbac.sql

# 若 MySQL 密码不是 root，启动 rbac 前设置环境变量：
# export MYSQL_PASSWORD=你的密码
mysql -u root -p size_rbac < size-general-rbac-modules/sql/auth.sql
mysql -u root -p size_rbac < size-general-rbac-modules/sql/system.sql

# 启动 Redis
redis-server

# 启动 Nacos（standalone 模式）
sh nacos/bin/startup.sh -m standalone
```

### 2. 启动后端服务

按顺序启动（IDEA 或命令行）：

```bash
cd size-general-rbac-modules

# 编译
mvn clean install -DskipTests

# 分别启动（各开终端）
cd size-rbac/rbac-biz && mvn spring-boot:run    # 9502
cd size-auth/auth-biz && mvn spring-boot:run    # 9501
cd size-system/system-biz && mvn spring-boot:run # 9503
cd size-gateway && mvn spring-boot:run           # 9500
```

### 3. 启动前端

```bash
cd size-general-rbac-admin
npm install
npm run dev
```

访问 http://localhost:5173

### 5. API 文档（Knife4j 网关聚合）

重启 **size-gateway** 后访问：

- 聚合文档 UI：http://localhost:9500/doc.html
- 各服务仍可直接访问：http://localhost:9501/doc.html（auth）、9502（rbac）、9503（system）

生产环境请在网关配置 `knife4j.gateway.enabled: false` 关闭文档入口。

### 4. 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 超级管理员 |

## 开发规范

### Git 分支

```
main          # 稳定版本
develop       # 开发主分支
feature/*     # 功能分支
fix/*         # 修复分支
```

### 提交信息

```
feat: 新增用户管理 CRUD
fix: 修复登录 Token 过期未跳转
docs: 更新架构设计文档
refactor: 重构权限缓存逻辑
```

### API 设计

- RESTful 风格，统一前缀 `/api/{service}`
- 统一响应格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

- 错误码规范：200 成功，400 参数错误，401 未认证，403 无权限，500 服务器错误
