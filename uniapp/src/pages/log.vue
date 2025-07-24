<script setup lang="ts">
import {formatDate} from "@dcloudio/uni-ui/lib/uni-dateformat/date-format";
import {OperationLog} from "../interface/log";
import {Page, Result, serverUrl} from "../interface/common";
import {ref} from "vue";
import {onLoad} from "@dcloudio/uni-app";

const logPage = ref(new Page<OperationLog>());

function getLogPage(current: number) {
  uni.request({
    url: serverUrl + "/log/operation/page/" + current,
    method: "GET",
    success(callback) {
      const result: Result = callback.data as any;
      if (callback.statusCode == 200)
        logPage.value = result.data;
    }
  } as RequestOptions);
}

onLoad(() => getLogPage(1));
</script>

<template>
  <my-menu-bar/>
  <div id="log-div">
    <uni-card id="log-card" title="监测记录">
      <uni-table id="table">
        <uni-tr>
          <uni-th>{{ "操作完成时间" }}</uni-th>
          <uni-th>{{ "操作人" }}</uni-th>
          <uni-th>{{ "操作模块" }}</uni-th>
          <uni-th>{{ "操作" }}</uni-th>
          <uni-th>{{ "是否成功" }}</uni-th>
        </uni-tr>
        <uni-tr v-for="log in (logPage.records as OperationLog[])">
          <uni-td>{{ formatDate(new Date(log.completionTime), "yyyy/MM/dd hh:mm:ss.SSS") }}</uni-td>
          <uni-td>{{ log.operatorName }}</uni-td>
          <uni-td>{{ log.module }}</uni-td>
          <uni-td>{{ log.operate }}</uni-td>
          <uni-td>{{ log.result ? "成功" : "失败" }}</uni-td>
        </uni-tr>
      </uni-table>
      <uni-pagination @change=getLogPage($event.current) :total=logPage.total show-icon="true"/>
    </uni-card>
  </div>
</template>

<style scoped lang="scss">

</style>