<script setup lang="ts">
import {ref} from "vue";
import {AreaCharts, GaugeCharts, Hardware, HostInfo, PieCharts} from "../interface/host";
import {Result, serverUrl} from "../interface/common";
import {onHide, onLoad, onShow} from "@dcloudio/uni-app";
import {auto, formatTime} from "../util/timeUtil";
import {formatDate, friendlyDate} from "@dcloudio/uni-ui/lib/uni-dateformat/date-format";

const hostInfo = ref(new HostInfo()),
    networkAreas = ref([]),
    networks = ref([]),
    diskAreas = ref([]),
    disks = ref([]),
    isDisk = ref(true),
    cpuGauge = ref(new GaugeCharts()),
    memoryRing = ref(new PieCharts()),
    memoryUsage = ref(0),
    diskPie = ref(new PieCharts()),
    diskUsage = ref(0),
    current = ref(0);

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

function getHardware(count: number) {
  uni.request({
    url: serverUrl + "/host/hardware",
    method: "GET",
    success(callback) {
      const hardware: Hardware = (callback.data as Result).data;
      for (let currentIndex = 0; currentIndex < hardware.disks.length; currentIndex++){
        if (count < 1)
          disks.value.push({value: currentIndex, text: hardware.disks[currentIndex].name});
        diskAreas.value.push(new AreaCharts());
        diskAreas.value[currentIndex].categories.push(formatDate(new Date(), "hh:mm:ss"));
        diskAreas.value[currentIndex].series[0].name = "读取";
        diskAreas.value[currentIndex].series[1].name = "写入";
        diskAreas.value[currentIndex].series[0].data.push(hardware.disks[currentIndex].readBytes);
        diskAreas.value[currentIndex].series[1].data.push(hardware.disks[currentIndex].writeBytes);
        if (diskAreas.value[currentIndex].categories.length > 6) {
          diskAreas.value[currentIndex].categories.shift();
          diskAreas.value[currentIndex].series[0].data.shift();
          diskAreas.value[currentIndex].series[1].data.shift();
        }
      }
      for (let currentIndex = 0; currentIndex < hardware.disks.length; currentIndex++){
        if (count < 1)
          networks.value.push({value: currentIndex, text: hardware.networks[currentIndex].name});
        networkAreas.value.push(new AreaCharts());
        networkAreas.value[currentIndex].categories.push(formatDate(new Date(), "hh:mm:ss"));
        networkAreas.value[currentIndex].series[0].name = "上行";
        networkAreas.value[currentIndex].series[1].name = "下行";
        networkAreas.value[currentIndex].series[0].data.push(hardware.networks[currentIndex].upBytes);
        networkAreas.value[currentIndex].series[1].data.push(hardware.networks[currentIndex].downBytes);
        if (networkAreas.value[currentIndex].categories.length > 6) {
          networkAreas.value[currentIndex].categories.shift();
          networkAreas.value[currentIndex].series[0].data.shift();
          networkAreas.value[currentIndex].series[1].data.shift();
        }
      }
      cpuGauge.value.series[0].data = Number((hardware.cpu.used / 100).toFixed(4));
      memoryRing.value.series[0].data[0].value = hardware.memory.used;
      memoryRing.value.series[0].data[1].value = hardware.memory.free;
      memoryUsage.value = Number((hardware.memory.used / hardware.memory.total * 100).toFixed(2));
      diskPie.value.series[0].data[0].value = hardware.diskUsed;
      diskPie.value.series[0].data[1].value = hardware.diskFree;
      diskUsage.value = Number((hardware.diskUsed / hardware.diskTotal * 100).toFixed(2));
    }
  });
}

let interval = null;

onShow(() => {
  interval = setInterval(() => getHardware(1), 5000);
});

onHide(() => {
  console.log(isDisk.value)
  clearInterval(interval);
  interval = null;
})

onLoad(() => {
  getHostInfo();
  getHardware(0);
});
</script>

<template>
  <my-menu-bar/>
  <div class="column" id="super">
    <div class="row" id="top">
      <uni-card id="monitor" title="监控">
        <div style="display: flex;width: 50%;float: right">
          <uni-data-select style="flex: 1" v-model="current" :localdata="isDisk ? disks : networks" :clear="false"/>
          <uni-segmented-control style="flex: 1" @clickItem="isDisk = !isDisk" :values="['磁盘IO', '网络']"/>
        </div>
        <qiun-data-charts type="area" :opts="{update: true, duration: 0, animation: false,extra: {area: {type: 'curve'}}}"
                          :chartData="isDisk ? diskAreas[current] : networkAreas[current]"/>
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
                  return formatTime(value * 1000, auto);
              return value;
            })() }}</uni-td>
          </uni-tr>
        </uni-table>
      </uni-card>
    </div>
    <div class="row" id="bottom">
      <uni-card id="cpu" title="CPU">
        <qiun-data-charts type="gauge" :opts="{title: {name: cpuGauge.series[0].data * 100 + '%'}, subtitle: {name: 'CPU使用率'}, update: true, duration: 0, animation: false}"
                          :chartData="cpuGauge"/>
      </uni-card>
      <uni-card id="memory" title="内存">
        <qiun-data-charts type="ring" :opts="{title: {name: memoryUsage + '%'}, subtitle: {name: '内存使用率'}, update: true, duration: 0, animation: false}"
                          :chartData="memoryRing"/>
      </uni-card>
      <uni-card id="disk" title="磁盘空间">
        <qiun-data-charts type="pie" :opts="{update: true, duration: 0, animation: false}"
                          :chartData="diskPie"/>
      </uni-card>
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