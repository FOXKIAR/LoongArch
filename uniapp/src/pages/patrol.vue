<script setup lang="ts">
import {Patrol} from "../interface/patrol";
import {ref} from "vue";
import {Page, Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";
import {formatDate} from "@dcloudio/uni-ui/lib/uni-dateformat/date-format";

const patrolPage = ref(new Page<Patrol>()),
    patrol = ref(new Patrol()),
    insertPatrol = ref(new Patrol()),
    popup = ref();

function getPatrolPage(current: number) {
  uni.request({
    url: serverUrl + "/patrol/page/" + current,
    method: "GET",
    data: patrol.value,
    success(callback) {
      const result: Result = callback.data as any;
      if (callback.statusCode == 200)
        patrolPage.value = result.data;
    }
  } as RequestOptions);
}

function filterPatrol(filter: any, column: string) {
  if (column == 'recordDate') {
    patrol.value.startDate = filter[0];
    patrol.value.endDate = filter[1];
  } else {
    patrol.value[column] = filter;
  }
  getPatrolPage(patrolPage.value.current)
}

function popupOpen() {
  popup.value.open()
}

function addPatrol() {
  uni.request({
    url: serverUrl + "/patrol/append",
    method: "POST",
    data: insertPatrol.value,
  } as RequestOptions);
  getPatrolPage(patrolPage.value.current);
}

// 加载完成后获取第一页用户列表
onLoad(() => getPatrolPage(1));
</script>

<template>
  <my-menu-bar/>
  <uni-popup ref="popup" type="dialog">
    <uni-popup-dialog title="记录" @confirm="addPatrol">
      <uni-forms id="input-form" :modelValue=insertPatrol>
        {{ "设备是否正常运行：" }}
        <uni-data-checkbox v-model="insertPatrol.isNormal" :localdata="[
                        {value: true, text: '是'},
                        {value: false, text: '否'}
                    ]"/>
        {{ "备注：" }}
        <uni-easyinput v-model=insertPatrol.comment type="textarea"/>
      </uni-forms>
    </uni-popup-dialog>
  </uni-popup>

  <div id="patrol-div">
    <uni-card id="patrol-card" title="监测记录">
      <button @click="popupOpen">{{ "记录" }}</button>
      <uni-table id="table">
        <uni-tr>
          <uni-th filter-type="date" @filter-change="filterPatrol($event.filter, 'recordDate')">{{ "监测日期" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterPatrol($event.filter, 'userName')">{{ "监测人" }}</uni-th>
          <uni-th :filter-data="[
                        {value: true, text: '是'},
                        {value: false, text: '否'}
                    ]" filter-type="select" @filter-change="filterPatrol($event.filter, 'isNormal')">{{ "设备是否正常运行" }}</uni-th>
          <uni-th filter-type="search" @filter-change="filterPatrol($event.filter, 'comment')">{{ "备注" }}</uni-th>
        </uni-tr>
        <uni-tr v-for="patrol in (patrolPage.records as Patrol[])">
          <uni-td>{{ formatDate(new Date(patrol.recordDate), "yyyy/MM/dd") }}</uni-td>
          <uni-td>{{ patrol.userName }}</uni-td>
          <uni-td>{{ patrol.isNormal ? '是' : '否' }}</uni-td>
          <uni-td>{{ patrol.comment }}</uni-td>
        </uni-tr>
      </uni-table>
      <uni-pagination @change=getPatrolPage($event.current) :total=patrolPage.total show-icon="true"/>
    </uni-card>
  </div>
</template>

<style scoped lang="scss">
#input-form {
  width: 20vw;
}

#patrol-div {
  float: right;
  width: 95vw;

  #patrol-card {
    height: 90vh;

    #table {
      height: 70vh;
    }
  }
}
</style>