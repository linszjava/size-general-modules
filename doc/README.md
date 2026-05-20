# 项目文档索引

本目录存放 size-general-modules 项目的全部设计与开发文档，后续生成的文档均放在此目录下。

## 文档列表

| 文档 | 说明 |
|------|------|
| [architecture.md](./architecture.md) | 整体架构设计、微服务拆分、RBAC 数据模型 |
| [backend-structure.md](./backend-structure.md) | 后端 Maven 多模块目录说明 |
| [frontend-structure.md](./frontend-structure.md) | 前端 Vue3 项目目录说明 |
| [development-guide.md](./development-guide.md) | 分阶段开发流程与本地启动指南 |
| [version-management.md](./version-management.md) | 版本号与端口统一管理 |

## SQL 脚本

SQL 脚本位于后端项目 `size-general-rbac-modules/sql/` 目录，每个微服务对应一个 SQL 文件：

| 文件 | 对应服务 |
|------|----------|
| [auth.sql](../size-general-rbac-modules/sql/auth.sql) | auth-service 认证服务 |
| [rbac.sql](../size-general-rbac-modules/sql/rbac.sql) | rbac-service 权限服务 |
| [system.sql](../size-general-rbac-modules/sql/system.sql) | system-service 系统服务 |

> gateway-service 无数据库，不提供 SQL 文件。
