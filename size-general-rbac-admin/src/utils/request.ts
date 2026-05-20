import axios from 'axios'
import type { AxiosResponse, InternalAxiosRequestConfig } from 'axios'
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
})

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
    if (res.code !== 200) {
      message.error(res.message || '请求失败')
      if (res.code === 401) {
        removeToken()
        router.push('/login')
      }
      return Promise.reject(new Error(res.message))
    }
    return response
  },
  (error) => {
    message.error(error.message || '网络异常')
    return Promise.reject(error)
  },
)

export default request
