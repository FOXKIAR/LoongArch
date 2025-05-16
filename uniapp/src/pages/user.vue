<script lang="ts" setup>
import {ref} from 'vue';
import {User, Permission, userRules} from '../interface/user';
import {Page, Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";

const userPage = ref(new Page<User>()),
    userQueryField = ref(new User()),
    operatedUser = ref(new User()),
    isUpdate = ref(true),
    popup = ref();

function getUserPage(current: number) {
  uni.request({
    url: serverUrl + "/user/page/" + current,
    method: "GET",
    data: userQueryField.value,
    success(callback) {
      const result: Result = callback.data as any;
      if (callback.statusCode == 200)
        userPage.value = result.data;
    }
  } as RequestOptions);
}

function remove() {
  uni.request({
    url: serverUrl + "/user/delete/" + operatedUser.value.id,
    method: "DELETE",
    success() {
      getUserPage(userPage.value.current);
    }
  } as RequestOptions);
}

function update() {
  uni.request({
    url: serverUrl + "/user/update",
    method: "PUT",
    data: operatedUser.value,
  } as RequestOptions);
}

function filterUser(filter: string | number[], column: string) {
  if (typeof filter == "string")
    userQueryField.value[column] = filter || null;
  else
    userQueryField.value.permission = (filter as number[]).length != 0 ?
        (filter as number[]).reduce(function (prev, curr) {
          return prev + curr;
        }) : null;
  getUserPage(userPage.value.current);
}

function clickOptionButton(user: User, flag: boolean) {
  isUpdate.value = flag;
  operatedUser.value = user;
  popup.value.open();
}

// 加载完成后获取第一页用户列表
onLoad(() => getUserPage(1));
</script>

<template>
  <my-menu-bar/>
  <uni-popup ref="popup" type="dialog">
    <uni-popup-dialog :title="isUpdate ? '更新信息' : '你确定要删除吗？'" @confirm="isUpdate ? update() : remove()">
      <uni-forms v-if=isUpdate id="input-form" :modelValue=operatedUser :rules=userRules validate-trigger="blur">
        <uni-forms-item class="item" label="姓名：" name="name">
          <uni-easyinput v-model=operatedUser.name type="text"/>
        </uni-forms-item>
        <uni-forms-item class="item" label="账号：" name="account">
          <uni-easyinput v-model=operatedUser.account type="text"/>
        </uni-forms-item>
        <uni-forms-item class="item" label="邮箱：" name="email">
          <uni-easyinput v-model=operatedUser.email type="text"/>
        </uni-forms-item>
        <uni-forms-item class="item" label="手机号：" name="phone">
          <uni-easyinput v-model=operatedUser.phone type="text"/>
        </uni-forms-item>
      </uni-forms>
    </uni-popup-dialog>
  </uni-popup>
  <div id="user-div">
    <uni-card id="user-card" title="用户">
      <uni-table id="table">
        <uni-tr>
          <uni-th width="80px">{{ "ID" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterUser($event.filter, 'name')">{{ "姓名" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterUser($event.filter, 'account')">{{ "账号" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterUser($event.filter, 'phone')">{{ "手机号" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterUser($event.filter, 'email')">{{ "邮箱" }}</uni-th>
          <uni-th :filter-data="[
                        {value: 1, text: 'GET'},
                        {value: 2, text: 'PUT'},
                        {value: 4, text: 'DELETE'},
                        {value: 8, text: 'POST'}
                    ]" filter-type="select"
                  @filter-change="filterUser($event.filter, 'permission')">{{ "权限" }}</uni-th>
          <uni-th>{{ "操作" }}</uni-th>
        </uni-tr>
        <uni-tr v-for="user in (userPage.records as User[])">
          <uni-td>{{ user.id }}</uni-td>
          <uni-td>{{ user.name }}</uni-td>
          <uni-td>{{ user.account }}</uni-td>
          <uni-td>{{ user.phone }}</uni-td>
          <uni-td>{{ user.email }}</uni-td>
          <uni-td>
            <uni-tag :inverted="!Boolean((user.permission ?? 0) & Permission.POST)" class="tag" text="POST" type="royal"/>
            <uni-tag :inverted="!Boolean((user.permission ?? 0) & Permission.DELETE)" class="tag" text="DELETE" type="error"/>
            <uni-tag :inverted="!Boolean((user.permission ?? 0) & Permission.PUT)" class="tag" text="PUT" type="primary"/>
            <uni-tag :inverted="!Boolean((user.permission ?? 0) & Permission.GET)" class="tag" text="GET" type="success"/>
          </uni-td>
          <uni-td>
            <uni-tag @click=clickOptionButton(user,true) class="option" text="修改" type="primary"/>
            <uni-tag @click=clickOptionButton(user,false) class="option" text="删除" type="error"/>
          </uni-td>
        </uni-tr>
      </uni-table>
      <uni-pagination @change=getUserPage($event.current) :total=userPage.total show-icon="true"/>
    </uni-card>
  </div>
</template>

<style lang="scss" scoped>
#user-div {
  float: right;
  width: 95vw;

  #user-card {
    height: 90vh;

    #table {
      height: 70vh;
    }



    .tag, .option {
      margin: auto 5px;
    }
  }
}
</style>