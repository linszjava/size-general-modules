# Size General Modules

这是一个前后端分离 Spring Cloud + Vue3 的通用 RBAC 项目，旨在为更多项目提供通用的模块复用，并附上完整的开发流程供参考学习。

## 项目结构

```
size-general-modules/
├── doc/                              # 项目文档
├── size-general-rbac-modules/        # 后端（Spring Cloud 微服务）
└── size-general-rbac-admin/          # 前端（Vue3 Admin）
```

## 项目内容

涵盖前后端 RBAC 管理系统，尽可能作为即插即用的项目存在。

## 技术栈

### 后端

Sa-Token · Java 17 · Spring Boot 3 · MyBatis-Plus · Redis · MySQL 8 · Spring Cloud · Spring Cloud Alibaba · RabbitMQ

### 前端

Vue 3 · Ant Design Vue · TypeScript · Pinia · Vite

## 快速开始

详细步骤见 [doc/development-guide.md](./doc/development-guide.md)。

### 后端

```bash
# 导入 SQL
mysql -u root -p < size-general-rbac-modules/sql/rbac.sql
mysql -u root -p < size-general-rbac-modules/sql/auth.sql
mysql -u root -p < size-general-rbac-modules/sql/system.sql

# 编译 & 启动
cd size-general-rbac-modules
mvn clean install -DskipTests
```

### 前端

```bash
cd size-general-rbac-admin
npm install
npm run dev
```

## 文档

| 文档 | 说明 |
|------|------|
| [doc/README.md](./doc/README.md) | 文档索引 |
| [doc/architecture.md](./doc/architecture.md) | 架构设计 |
| [doc/backend-structure.md](./doc/backend-structure.md) | 后端目录说明 |
| [doc/frontend-structure.md](./doc/frontend-structure.md) | 前端目录说明 |
| [doc/development-guide.md](./doc/development-guide.md) | 开发流程指南 |

## 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 超级管理员 |
