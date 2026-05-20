<template>
  <a-menu
    theme="dark"
    mode="inline"
    :selected-keys="selectedKeys"
  >
    <a-menu-item key="/dashboard">
      <router-link to="/dashboard">首页</router-link>
    </a-menu-item>
    <!-- TODO: 动态渲染菜单 -->
  </a-menu>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const selectedKeys = computed(() => [route.path])
</script>
