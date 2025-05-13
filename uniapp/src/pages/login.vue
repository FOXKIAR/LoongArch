<script lang="ts" setup>
import { User, userRules} from "../interface/user";
import { Result, serverUrl } from "../interface/common";
import {ref} from "vue";

const user = ref(new User()),
    messageText = ref(""),
    messageType = ref("error"),
    messageBox = ref();

function login(user: User) {
  // noinspection JSUnusedGlobalSymbols
  uni.request({
    url: serverUrl + "/user/login",
    method: "POST",
    data: user,
    success(callback) {
      const result = callback.data as Result;
      messageText.value = result.msg;
      if (callback.statusCode == 200) {
        messageType.value = "success";
        uni.setStorageSync("me", result.data);
        // 延迟 2.5 秒后跳转到主页
        setTimeout(() => uni.redirectTo({url: "/pages/index"}), 2500);
      }
    },
    fail() {
      messageText.value = "网络连接失败";
    },
    complete() {
      messageBox.value?.open();
    }
  } as RequestOptions);
}
</script>

<template>
  <div id="input-form-top"/>
  <uni-forms id="input-form" :modelValue="user" :rules="userRules" validate-trigger="blur">
    <uni-forms-item class="item" label="账号：" name="account">
      <uni-easyinput v-model="user.account" type="text"/>
    </uni-forms-item>
    <uni-forms-item class="item" label="密码：" name="password">
      <uni-easyinput v-model="user.password" type="password"/>
    </uni-forms-item>
    <button @click="login(user)">{{ "登录" }}</button>
  </uni-forms>
  <uni-popup ref="messageBox" type="message">
    <uni-popup-message :message=messageText :type=messageType is/>
  </uni-popup>
</template>

<style lang="scss" scoped>
@import "../uni.scss";
/* PC */
@media screen and (min-width: 600px) {
  body {
    background: linear-gradient(to right, cornflowerblue, white);
  }
  #input-form-top {
    height: 20vh;
  }
  #input-form {
    box-shadow: $uni-shadow-lg;
    width: 30vw;
    height: 40vh;
    background: white;
    border-radius: 2vh;
    margin: auto;

    .item {
      top: 8vh;
      margin-left: auto;
      margin-right: auto;
      width: 25vw;
    }

    button {
      top: 8vh;
      width: 10vw;
    }
  }
}

/* 手机 */
@media screen and (max-width: 600px) {
  body {
    background: linear-gradient(to top, cornflowerblue, white);
  }
  #input-form-top {
    height: 20vh;
  }
  #input-form {
    box-shadow: $uni-shadow-lg;
    width: 80vw;
    height: 35vh;
    background: white;
    border-radius: 2vh;
    margin: auto;

    .item {
      top: 7vh;
      margin-left: auto;
      margin-right: auto;
      width: 75vw;
    }

    button {
      top: 7vh;
      width: 60vw;
    }
  }
}
</style>