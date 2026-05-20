import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getToken, setToken, removeToken } from '@/utils/auth'
import type { MenuItem, UserInfo } from '@/types/api'

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const userInfo = ref<UserInfo | null>(null)
  const menus = ref<MenuItem[]>([])
  const permissions = ref<string[]>([])

  async function login(_username: string, _password: string) {
    // TODO: 对接 auth-service 登录接口
    const mockToken = 'mock-token'
    setToken(mockToken)
    token.value = mockToken
  }

  async function fetchUserInfo() {
    // TODO: 对接 auth-service 用户信息接口
    userInfo.value = { id: 1, username: 'admin', nickname: '管理员' }
    menus.value = []
    permissions.value = ['system:user:list']
  }

  function logout() {
    removeToken()
    token.value = null
    userInfo.value = null
    menus.value = []
    permissions.value = []
  }

  return { token, userInfo, menus, permissions, login, fetchUserInfo, logout }
})
