<template>
  <a-layout class="basic-layout">
    <a-layout-sider v-model:collapsed="appStore.collapsed" collapsible>
      <div class="logo">{{ appStore.title }}</div>
      <Sidebar />
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="header">
        <Header />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { useAppStore } from '@/stores/app'
import Sidebar from './components/Sidebar.vue'
import Header from './components/Header.vue'

const appStore = useAppStore()
</script>

<style scoped lang="less">
.basic-layout {
  min-height: 100vh;
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
}

.header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
}

.content {
  margin: 16px;
  padding: 24px;
  background: #fff;
  min-height: 280px;
}
</style>
