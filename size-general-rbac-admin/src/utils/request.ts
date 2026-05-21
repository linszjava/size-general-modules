import axios from 'axios'
import type { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import { getToken, removeToken } from './auth'
import { message } from 'ant-design-vue'
import router from '@/router'

export interface ApiResult<T = unknown> {
  code: number
  message: string
  data: T
}

const request = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 10000,
  // 仅 2xx 走成功回调；4xx/5xx 走错误回调（方式 A）
  validateStatus: (status) => status >= 200 && status < 300,
})

function isLoginRequest(url?: string) {
  return !!url?.includes('/auth/login')
}

/** 401 时跳转登录页（登录接口本身、已在登录页时不跳转） */
function handleUnauthorized(config?: InternalAxiosRequestConfig) {
  removeToken()
  if (isLoginRequest(config?.url)) {
    return
  }
  if (router.currentRoute.value.path === '/login') {
    return
  }
  router.push('/login')
}

request.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

request.interceptors.response.use(
  (response: AxiosResponse<ApiResult>) => {
    const res = response.data
    // 兼容极少数仍返回 HTTP 200 + 业务错误码的情况
    if (res?.code !== undefined && res.code !== 200) {
      const msg = res.message || '请求失败'
      message.error(msg)
      if (res.code === 401) {
        handleUnauthorized(response.config)
      }
      return Promise.reject(new Error(msg))
    }
    return response
  },
  (error: AxiosError<ApiResult>) => {
    const status = error.response?.status
    const res = error.response?.data
    const msg = res?.message || error.message || '请求失败'
    message.error(msg)
    if (status === 401) {
      handleUnauthorized(error.config)
    }
    return Promise.reject(new Error(msg))
  },
)

export default request
