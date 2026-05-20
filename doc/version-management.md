# 版本与端口管理

## Maven 版本号

**只需改父 POM 里一行：**

```xml
<!-- size-general-rbac-modules/pom.xml -->
<properties>
    <revision>1.0.0-SNAPSHOT</revision>   <!-- 改这里 -->
</properties>
```

子模块通过 `${revision}` 引用，不需要任何额外插件。

## 服务端口

端口直接配置在各微服务的 `application.yml` 中：

| 服务 | 端口 | 配置文件 |
|------|------|----------|
| size-gateway | 9500 | `size-gateway/src/main/resources/application.yml` |
| size-auth | 9501 | `size-auth/auth-biz/src/main/resources/application.yml` |
| size-rbac | 9502 | `size-rbac/rbac-biz/src/main/resources/application.yml` |
| size-system | 9503 | `size-system/system-biz/src/main/resources/application.yml` |

前端代理目标：`size-general-rbac-admin/vite.config.ts` → `9500`
