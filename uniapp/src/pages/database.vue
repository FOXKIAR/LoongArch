<script setup lang="ts">
import { ref } from "vue";
import {Result, serverUrl} from "../interface/common";
import {TableStruct} from "../interface/database";
import {onLoad} from "@dcloudio/uni-app";

const tableNames = ref([]),
    tableStruct = ref([]),
    currentTableName = ref("");

function getTables() {
  uni.request({
    url: serverUrl + "/database/all/",
    method: "GET",
    success(callback) {
      tableNames.value = (callback.data as Result).data;
    }
  });
}

function getTableStruct(tableName: string) {
  uni.request({
    url: serverUrl + "/database/struct/" + tableName,
    method: "GET",
    success(callback) {
      tableStruct.value = (callback.data as Result).data;
      console.log(tableStruct.value);
    }
  });
}

onLoad((option) => {
  getTables();
  if ((currentTableName.value = option.table))
    getTableStruct(option.table);
})
</script>

<template>
  <view>
    <my-menu-bar/>
    <div id="db-div">
      <uni-card id="db-card" title="数据库">
        <div id="tables">
          <navigator class="table" v-for="table in tableNames" :url="'database?table=' + table">
            <button :id="(table == currentTableName && 'current').toString()">{{ table }}</button>
          </navigator>
        </div>
        <uni-table v-if="tableStruct.length != 0" id="table">
          <uni-tr>
            <uni-th>{{ "字段名" }}</uni-th>
            <uni-th>{{ "类型" }}</uni-th>
            <uni-th>{{ "非空" }}</uni-th>
            <uni-th>{{ "默认值" }}</uni-th>
            <uni-th>{{ "说明" }}</uni-th>
          </uni-tr>
          <uni-tr v-for="struct in tableStruct as TableStruct[]">
            <uni-td>{{ struct.column_name }}</uni-td>
            <uni-td>{{ struct.data_type }}</uni-td>
            <uni-td>{{ struct.is_nullable }}</uni-td>
            <uni-td>{{ struct.column_default ?? "NULL" }}</uni-td>
            <uni-td>{{ struct.comment }}</uni-td>
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

    #databases {
      width: 15vw;
      height: 90vh;
      float: left;
      overflow: auto;

      .database {
        margin: 20px 20px;

        #current {
          background: cornflowerblue;
        }
      }
    }

    #tables {
      width: 15vw;
      height: 90vh;
      float: left;
      overflow: auto;

      .table {
        margin: 20px 20px;

        #current {
          background: cornflowerblue;
        }
      }
    }

    #table {
      float: right;
      overflow: visible;
      width: 60vw;
    }
  }
}
</style>