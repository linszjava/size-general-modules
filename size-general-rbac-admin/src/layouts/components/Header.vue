<template>
  <div class="header-bar">
    <a-breadcrumb>
      <a-breadcrumb-item>首页</a-breadcrumb-item>
    </a-breadcrumb>
    <div class="header-right">
      <span class="username">{{ userStore.userInfo?.nickname || '用户' }}</span>
      <a-button type="link" @click="handleLogout">退出</a-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped lang="less">
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.username {
  color: rgba(0, 0, 0, 0.65);
}
</style>
