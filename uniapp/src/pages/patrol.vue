<script setup lang="ts">
import {Patrol, PatrolPage, patrolRules} from "../api/patrol";
import {ref} from "vue";
import {Result, serverUrl} from "../api/common";
import {onLoad} from "@dcloudio/uni-app";

const patrolPage = ref(new PatrolPage()),
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

// 加载完成后获取第一页用户列表
onLoad(() => getPatrolPage(1));
</script>

<template>
  <uni-popup ref="popup" type="dialog">
    <uni-popup-dialog title="记录" @confirm="">
      <uni-forms id="input-form" :modelValue=insertPatrol :rules=patrolRules validate-trigger="blur">
        <uni-forms-item class="item" label="设备是否正常运行：" name="isNormal">
          <uni-data-checkbox v-model="insertPatrol.isNormal" :localdata="[
                        {value: true, text: '是'},
                        {value: false, text: '否'}
                    ]"/>
        </uni-forms-item>
        <uni-forms-item class="item" label="备注：" name="comment">
          <uni-easyinput v-model=insertPatrol.comment type="text"/>
        </uni-forms-item>
      </uni-forms>
    </uni-popup-dialog>
  </uni-popup>

  <div id="patrol-div">
    <uni-card id="patrol-card" title="监测记录">
      <button @click="() => popup.value.open()">{{ "记录" }}</button>
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
          <uni-td>{{ patrol.recordDate.toLocaleString().slice(0, 10) }}</uni-td>
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
#patrol-div {
  display: flex;

  #patrol-card {
    height: 90vh;

    #table {
      height: 70vh;
    }
  }
}
</style>