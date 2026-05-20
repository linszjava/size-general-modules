import request from '@/utils/request'
import type { ApiResult } from '@/utils/request'
import type { LoginParams, LoginResult, UserInfo, MenuItem } from '@/types/api'

export function loginApi(data: LoginParams) {
  return request.post<ApiResult<LoginResult>>('/auth/login', data)
}

export function getUserInfoApi() {
  return request.get<ApiResult<{ user: UserInfo; menus: MenuItem[]; permissions: string[] }>>('/auth/info')
}

export function logoutApi() {
  return request.post<ApiResult<void>>('/auth/logout')
}
