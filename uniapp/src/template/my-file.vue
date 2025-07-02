<script setup lang="ts">
import {ref} from "vue";
import {FileInfo} from "../interface/file";
import {Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";
import {formatSize} from "../util/sizeUtil";

const files = ref(new Array<FileInfo>()),
    path = ref("");

function getFiles(fileName: string) {
  path.value += fileName;
  uni.request({
    url: serverUrl + "/directory",
    method: "GET",
    data: {
      path: path.value
    },
    success(callback) {
      const result: Result = callback.data as any;
      if (callback.statusCode == 200)
        files.value = result.data;
    }
  } as RequestOptions);
}

function sizeToString(size: number | null) {
  if (size == null)
    return null;
  const result = formatSize(size)
  return result.size + result.unit;
}

// 加载完成后获取根目录文件集
onLoad(() => getFiles("/home/"));
</script>

<template>
  <div id="file-box">
    <uni-easyinput id="input" v-model="path" type="text"/>
    <uni-list>
      <uni-list-item clickable
          v-for="file in files"
          :disabled=!file.isDirectory
          :title=file.name
          :rightText=sizeToString(file.size)
          @dblclick="getFiles(file.name + '/')"
      />
    </uni-list>
  </div>
</template>

<style scoped lang="scss">
#file-box {
  float: left;
  height: 100vh;
  width: 20%;
  overflow:auto;
}
</style>