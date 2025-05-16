<script setup lang="ts">
import {ref} from "vue";
import {AreaCharts, HostInfo} from "../interface/host";
import {Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";
import {formatTime} from "../util/timeUtil";
import {formatDate, friendlyDate} from "@dcloudio/uni-ui/lib/uni-dateformat/date-format";

const hostInfo = ref(new HostInfo()),
    networkArea = ref(new AreaCharts()),
    diskIOArea = ref(new AreaCharts())

function getHostInfo() {
  uni.request({
    url: serverUrl + "/host/info",
    method: "GET",
    success(callback) {
      const result: Result = callback.data as any;
      hostInfo.value = result.data;
      hostInfo.value.startTime = friendlyDate(new Date(Date.now() - hostInfo.value.uptime * 1000), {});
      setInterval(() => hostInfo.value.uptime ++, 1000);
    }
  })
}

function getHardware() {
  uni.request({
    url: serverUrl + "/host/hardware",
    method: "GET",
    success(callback) {
      const result: Result = callback.data as any;
      networkArea.value.categories.push(formatDate(new Date(), "hh:mm:ss"));
      networkArea.value.series[0] = {name: "上行", data: []};
      networkArea.value.series[1] = {name: "下行", data: []};
      networkArea.value.series[1].data.push((result.data.networkIFs[0].bytesRecv / Math.pow(1024, 2)).toFixed(2));
      networkArea.value.series[0].data.push((result.data.networkIFs[0].bytesSent / Math.pow(1024, 2)).toFixed(2));

    }
  })
}

onLoad(() => {
  getHostInfo();
  getHardware();
})
</script>

<template>
  <my-menu-bar/>
  <div class="column" id="super">
    <div class="row" id="top">
      <uni-card id="monitor" title="监控">
        <uni-segmented-control :current="0" :values="['123', '456']"/>
        <qiun-data-charts type="area" :opts="{extra: {area: {type: 'curve'}}}" :chartData="networkArea"/>
      </uni-card>
      <uni-card id="info" title="基本信息">
        <uni-table>
          <uni-tr/>
          <uni-tr v-for="(value, key) in hostInfo">
            <uni-td>{{ ((columnName: string) => {
                switch (columnName) {
                  case "hostname": return "主机名称：";
                  case "system": return "系统名称：";
                  case "cpu": return "CPU型号：";
                  case "gpu": return "显卡名称：";
                  case "startTime": return "启动日期：";
                  case "uptime": return "运行时长";
                }
            })(key) }}</uni-td>
            <uni-td>{{ (() => {
              if (typeof value == "number")
                  return formatTime(value * 1000);
              return value;
            })() }}</uni-td>
          </uni-tr>
        </uni-table>
      </uni-card>
    </div>
    <div class="row" id="bottom">
      <uni-card id="cpu" title="CPU"></uni-card>
      <uni-card id="memory" title="内存"></uni-card>
    </div>
  </div>
</template>

<style scoped lang="scss">
.column {
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
  flex-direction: row;
}

#super {
  float: right;
  width: 95vw;
  height: 100vh;
}

#top {
  flex: 1;
  #monitor {
    flex: 2;
  }
  #info {
    flex: 1;
  }
}

#bottom {
  flex: 1;
  #cpu {
    flex: 1;
  }
  #memory {
    flex: 1;
  }
}
</style>