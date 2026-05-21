import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { loginApi, getUserInfoApi, logoutApi } from '@/api/auth'
import type { MenuItem, UserInfo } from '@/types/api'

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const userInfo = ref<UserInfo | null>(null)
  const menus = ref<MenuItem[]>([])
  const permissions = ref<string[]>([])

  async function login(username: string, password: string) {
    const res = await loginApi({ username, password })
    const accessToken = res.data.data?.token
    if (!accessToken) {
      throw new Error('登录失败：未获取到 Token')
    }
    setToken(accessToken)
    token.value = accessToken
    await fetchUserInfo()
  }

  async function fetchUserInfo() {
    const res = await getUserInfoApi()
    const data = res.data.data
    if (!data) {
      throw new Error('获取用户信息失败')
    }
    userInfo.value = {
      id: data.user.id,
      username: data.user.username,
      nickname: data.user.nickname,
      avatar: data.user.avatar,
    }
    menus.value = data.menus as MenuItem[]
    permissions.value = data.permissions
  }

  async function logout() {
    try {
      await logoutApi()
    } catch {
      // 忽略登出接口异常，仍清理本地状态
    }
    removeToken()
    token.value = null
    userInfo.value = null
    menus.value = []
    permissions.value = []
  }

  return { token, userInfo, menus, permissions, login, fetchUserInfo, logout }
})
