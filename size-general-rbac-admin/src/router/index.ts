import { createRouter, createWebHistory } from 'vue-router'
import { constantRoutes } from './routes'
import { setupRouterGuard } from './guard'

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
})

setupRouterGuard(router)

export default router
