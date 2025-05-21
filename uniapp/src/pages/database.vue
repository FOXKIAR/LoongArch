<script setup lang="ts">
import { ref } from "vue";
import {Result, serverUrl} from "../interface/common";
import {TableStruct} from "../interface/database";
import {onLoad} from "@dcloudio/uni-app";

const dbNames = ref([]),
    tableNames = ref([]),
    tableStruct = ref([]),
    currentDbName = ref(""),
    currentTableName = ref("");

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
  if ((currentDbName.value = option.db))
    getTables(option.db);
  if ((currentTableName.value = option.table))
    getTableStruct(option.db, option.table);
})
</script>

<template>
  <view>
    <my-menu-bar/>
    <div id="db-div">
      <uni-card id="db-card" title="数据库">
        <div id="databases">
          <navigator class="database" v-for="db in dbNames" :url="'database?db=' + db">
            <button :id="(db == currentDbName && 'current').toString()">{{ db }}</button>
          </navigator>
        </div>
        <div id="tables">
          <navigator class="table" v-for="table in tableNames" :url="'database?db=' + currentDbName + '&table=' + table">
            <button :id="(table == currentTableName && 'current').toString()">{{ table }}</button>
          </navigator>
        </div>
        <uni-table v-if="tableStruct.length != 0" id="table">
          <uni-tr>
            <uni-th>{{ "字段名" }}</uni-th>
            <uni-th>{{ "类型" }}</uni-th>
            <uni-th>{{ "非空" }}</uni-th>
            <uni-th>{{ "键" }}</uni-th>
            <uni-th>{{ "默认值" }}</uni-th>
            <uni-th>{{ "说明" }}</uni-th>
          </uni-tr>
          <uni-tr v-for="struct in tableStruct as TableStruct[]">
            <uni-td>{{ struct.Field }}</uni-td>
            <uni-td>{{ struct.Type }}</uni-td>
            <uni-td>{{ struct.Null }}</uni-td>
            <uni-td>{{ struct.Key }}</uni-td>
            <uni-td>{{ struct.Default ?? "NULL" }}</uni-td>
            <uni-td>{{ struct.Comment }}</uni-td>
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