import type { Router } from 'vue-router'
import { isLoggedIn } from '@/utils/auth'
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'

const whiteList = ['/login']

export function setupRouterGuard(router: Router) {
  router.beforeEach(async (to, _from, next) => {
    if (isLoggedIn()) {
      if (to.path === '/login') {
        next({ path: '/' })
        return
      }

      const userStore = useUserStore()
      const permissionStore = usePermissionStore()

      if (!userStore.userInfo) {
        try {
          await userStore.fetchUserInfo()
          const dynamicRoutes = permissionStore.generateRoutes(userStore.menus)
          dynamicRoutes.forEach((route) => router.addRoute(route))
          next({ ...to, replace: true })
        } catch {
          await userStore.logout()
          next(`/login?redirect=${to.path}`)
        }
        return
      }

      next()
    } else {
      if (whiteList.includes(to.path)) {
        next()
      } else {
        next(`/login?redirect=${to.path}`)
      }
    }
  })
}
