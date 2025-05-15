<script setup lang="ts">
import {ref} from "vue";
import {HostInfo} from "../interface/host";
import {Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";
import {formatTime} from "../util/timeUtil";
import {friendlyDate} from "@dcloudio/uni-ui/lib/uni-dateformat/date-format";

const hostInfo = ref(new HostInfo());

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

onLoad(() => {
  getHostInfo();
})
</script>

<template>
  <my-menu-bar/>
  <div class="column" id="super">
    <div class="row" id="top">
      <uni-card id="monitor" title="监控"></uni-card>
      <uni-card id="info" title="基本信息">
        <uni-table>
          <uni-tr/>
          <uni-tr v-for="(value, key) in hostInfo">
            <uni-td>{{ ((columnName: string) => {
                switch (columnName) {
                  case "hostname": return "主机名称：";
                  case "system": return "系统名称：";
                  case "cpu": return "CPU：";
                  case "gpu": return "GPU：";
                  case "startTime": return "启动日期：";
                  case "uptime": return "运行时间";
                }
            })(key) }}</uni-td>
            <uni-td>{{ ((formatData: any) => {
              switch (typeof formatData) {
                case "number": return formatTime(formatData * 1000);
                default: return formatData;
              }
            })(value) }}</uni-td>
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
  height: 120vh;
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