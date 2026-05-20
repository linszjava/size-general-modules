import type { App, Directive } from 'vue'
import { useUserStore } from '@/stores/user'

const permissionDirective: Directive<HTMLElement, string[]> = {
  mounted(el, binding) {
    const { value } = binding
    const userStore = useUserStore()
    if (value && value.length > 0) {
      const hasPermission = value.some((p) => userStore.permissions.includes(p))
      if (!hasPermission) {
        el.parentNode?.removeChild(el)
      }
    }
  },
}

export function setupPermissionDirective(app: App) {
  app.directive('permission', permissionDirective)
}
