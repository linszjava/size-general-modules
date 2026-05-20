import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const collapsed = ref(false)
  const title = ref(import.meta.env.VITE_APP_TITLE)

  function toggleCollapsed() {
    collapsed.value = !collapsed.value
  }

  return { collapsed, title, toggleCollapsed }
})
