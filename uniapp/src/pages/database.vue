<script setup lang="ts">
import { ref } from "vue";
import {Result, serverUrl} from "../interface/common";
import {TableStruct} from "../interface/database";
import {onLoad} from "@dcloudio/uni-app";

const dbNames = ref([]),
    tableNames = ref([]),
    tableStruct = ref([]);

function getDatabases() {
  uni.request({
    url: serverUrl + "/database/all",
    method: "GET",
    success(callback) {
      dbNames.value = (callback.data as Result).data;
    }
  });
}

function getTables(dbName: string) {
  uni.request({
    url: serverUrl + "/database/all/" + dbName,
    method: "GET",
    success(callback) {
      tableNames.value = (callback.data as Result).data;
    }
  });
}

function getTableStruct(dbName: string, tableName: string) {
  uni.request({
    url: serverUrl + "/database/struct/" + dbName + "/" + tableName,
    method: "GET",
    success(callback) {
      tableStruct.value = (callback.data as Result).data;
      console.log(tableStruct.value);
    }
  });
}

onLoad((option) => {
  getDatabases();
  if (option.db != null)
    getTables(option.db);
  if (option.table)
    getTableStruct(option.db, option.table);
})
</script>

<template>
  <view>
    <my-menu-bar/>
    <div id="db-div">
      <uni-card id="db-card" title="数据库">
        <uni-table id="table">
          <uni-tr>
            <uni-th>{{ "字段名" }}</uni-th>
            <uni-th>{{ "类型" }}</uni-th>
            <uni-th>{{ "非空" }}</uni-th>
            <uni-th>{{ "键" }}</uni-th>
            <uni-th>{{ "默认值" }}</uni-th>
            <uni-th>{{ "补充" }}</uni-th>
            <uni-th>{{ "说明" }}</uni-th>
          </uni-tr>
          <uni-tr v-for="struct in tableStruct as TableStruct[]">
            <uni-td>{{ struct.Field }}</uni-td>
            <uni-td>{{ struct.Type }}</uni-td>
            <uni-td>{{ struct.Null }}</uni-td>
            <uni-td>{{ struct.Key }}</uni-td>
            <uni-td>{{ struct.Default ?? "NULL" }}</uni-td>
            <uni-td>{{ struct.Extra }}</uni-td>
            <uni-td>{{ struct.Comment.slice(0, 12) }}</uni-td>
          </uni-tr>
        </uni-table>
      </uni-card>
    </div>
  </view>
</template>

<style scoped lang="scss">
#db-div {
  float: right;
  width: 95vw;

  #db-card {
    height: 90vh;

    #table {
      float: right;
      width: 60vw;

      .text {
        display: -webkit-box; /*弹性伸缩盒子模型显示*/
        -webkit-box-orient: vertical; /*排列方式*/
        -webkit-line-clamp: 1; /*显示文本行数(这里控制多少行隐藏)*/
        overflow: hidden; /*溢出隐藏*/
      }
    }
  }
}
</style>