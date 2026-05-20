import { defineStore } from 'pinia'
import type { RouteRecordRaw } from 'vue-router'
import type { MenuItem } from '@/types/api'

export const usePermissionStore = defineStore('permission', () => {
  function generateRoutes(menus: MenuItem[]): RouteRecordRaw[] {
    // TODO: 将菜单树转换为 Vue Router 路由配置
    return menus
      .filter((menu) => menu.menuType === 1)
      .map((menu) => ({
        path: menu.path,
        name: menu.path,
        component: () => import(`@/views${menu.component}.vue`),
        meta: { title: menu.menuName, icon: menu.icon },
      }))
  }

  return { generateRoutes }
})
