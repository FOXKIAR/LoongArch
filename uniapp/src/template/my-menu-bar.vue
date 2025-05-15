<script setup lang="ts">
import {ref} from "vue";

const showLeft = ref(),
    route = getCurrentPages(),
    current = route[route.length -1].route.split('/')[1],
    menus = {
      host: "主机信息",
      user: "用户",
      patrol: "巡检记录",
      database: "数据库"
    }

function showDrawer() {
  showLeft.value.open();
}
</script>

<template>
  <button @click="showDrawer" id="show">
    <uni-icons type="right" size="30"/>
  </button>

  <uni-drawer ref="showLeft">
    <navigator class="menu" v-for="(value, key) in menus" :url="key">
      <button :id="(key == current && 'current').toString()">{{ value }}</button>
    </navigator>
  </uni-drawer>
</template>

<style scoped lang="scss">
#show {
  position: fixed;
  width: 55px;
  height: 100vh;
  line-height: 100vh;
  text-align: center;
}
.menu {
  margin: 20px 20px;

  #current {
    background: cornflowerblue;
  }
}
</style>