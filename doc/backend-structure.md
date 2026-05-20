# 后端项目结构

后端项目位于 `size-general-rbac-modules/`，采用 Maven 多模块管理。

## 目录树

```
size-general-rbac-modules/
├── pom.xml                              # 父 POM，统一版本管理
├── sql/                                 # 各微服务 SQL 脚本（一服务一文件）
│   ├── auth.sql
│   ├── rbac.sql
│   └── system.sql
├── size-common/                         # 公共模块
│   ├── common-core/                     # 统一响应、异常、常量、工具
│   ├── common-redis/                    # Redis 封装
│   ├── common-mybatis/                  # MyBatis-Plus 配置
│   ├── common-satoken/                  # Sa-Token 公共配置
│   └── common-feign/                    # Feign 接口定义
├── size-gateway/                        # 网关服务
│   └── src/main/java/com/size/gateway/
├── size-auth/                           # 认证服务
│   ├── auth-api/                        # 对外 Feign API
│   └── auth-biz/                        # 业务实现 + 启动类
├── size-rbac/                           # RBAC 核心服务
│   ├── rbac-api/                        # 对外 Feign API
│   └── rbac-biz/                        # 业务实现 + 启动类
└── size-system/                         # 系统管理服务
    ├── system-api/                      # 对外 Feign API
    └── system-biz/                      # 业务实现 + 启动类
```

## 模块依赖关系

```
size-gateway
    └── common-satoken, common-core

size-auth (auth-biz)
    ├── auth-api
    ├── common-core, common-redis, common-satoken, common-feign
    └── rbac-api (Feign 调用用户/权限)

size-rbac (rbac-biz)
    ├── rbac-api
    └── common-core, common-redis, common-mybatis, common-satoken

size-system (system-biz)
    ├── system-api
    └── common-core, common-redis, common-mybatis, common-satoken
```

## 包命名规范

```
com.size.{module}
├── controller/      # REST 接口
├── service/         # 业务逻辑
│   └── impl/
├── mapper/          # MyBatis Mapper
├── entity/          # 数据库实体
├── dto/             # 数据传输对象
├── vo/              # 视图对象
└── config/          # 配置类
```

## 服务端口

| 服务 | 端口 | 启动类 |
|------|------|--------|
| gateway | 9500 | `GatewayApplication` |
| auth | 9501 | `AuthApplication` |
| rbac | 9502 | `RbacApplication` |
| system | 9503 | `SystemApplication` |

## 配置文件

各服务 `application.yml` 通过 Nacos 拉取公共配置，本地保留 `bootstrap.yml`：

```yaml
spring:
  application:
    name: size-rbac
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yml
```
