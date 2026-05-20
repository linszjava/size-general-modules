export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar?: string
}

export interface MenuItem {
  id: number
  parentId: number
  menuName: string
  menuType: number
  path: string
  component: string
  permission: string
  icon: string
  children?: MenuItem[]
}

export interface LoginParams {
  username: string
  password: string
}

export interface LoginResult {
  token: string
}
