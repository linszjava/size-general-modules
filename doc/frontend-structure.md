# 前端项目结构

前端项目位于 `size-general-rbac-admin/`，基于 Vue3 + Vite + TypeScript + Ant Design Vue。

## 目录树

```
size-general-rbac-admin/
├── index.html
├── package.json
├── vite.config.ts
├── tsconfig.json
├── env.d.ts
├── public/
└── src/
    ├── main.ts                        # 入口
    ├── App.vue
    ├── api/                           # API 请求（按模块划分）
    │   ├── auth.ts
    │   ├── user.ts
    │   ├── role.ts
    │   ├── menu.ts
    │   └── dept.ts
    ├── assets/                        # 静态资源
    ├── components/                    # 公共组件
    ├── directives/                    # 自定义指令
    │   └── permission.ts              # v-permission 按钮权限
    ├── layouts/                       # 布局组件
    │   ├── BasicLayout.vue
    │   └── components/
    │       ├── Sidebar.vue
    │       └── Header.vue
    ├── router/
    │   ├── index.ts                   # 路由配置
    │   ├── routes.ts                  # 静态路由
    │   └── guard.ts                   # 路由守卫 + 动态路由
    ├── stores/                        # Pinia 状态管理
    │   ├── user.ts                    # 用户信息 + Token
    │   ├── permission.ts              # 菜单 + 权限码
    │   └── app.ts                     # 应用全局状态
    ├── styles/                        # 全局样式
    │   └── index.less
    ├── types/                         # TypeScript 类型定义
    │   ├── api.d.ts
    │   └── router.d.ts
    ├── utils/
    │   ├── auth.ts                    # Token 存取
    │   └── request.ts                 # Axios 封装
    └── views/
        ├── login/
        │   └── index.vue
        ├── dashboard/
        │   └── index.vue
        └── system/
            ├── user/
            ├── role/
            ├── menu/
            └── dept/
```

## 动态路由流程

```
1. 用户登录成功
2. 调用 /auth/info 获取用户信息 + 菜单树 + 权限码
3. permission store 存储菜单和权限码
4. 将菜单树转换为 Vue Router 路由配置
5. router.addRoute() 动态注册
6. 跳转到首页
```

## 按钮权限

通过自定义指令控制：

```vue
<a-button v-permission="['system:user:add']">新增用户</a-button>
```

指令内部读取 `permission store` 中的权限码列表，无权限则移除 DOM 元素。

## 环境变量

| 变量 | 说明 | 默认值 |
|------|------|--------|
| `VITE_APP_BASE_API` | 后端 API 前缀 | `/api` |
| `VITE_APP_TITLE` | 系统标题 | Size RBAC Admin |

## 开发命令

```bash
cd size-general-rbac-admin
npm install
npm run dev        # 启动开发服务器
npm run build      # 生产构建
npm run preview    # 预览构建结果
```
