<script setup lang="ts">
import {ref} from "vue";
import {FileInfo} from "../interface/file";
import {Result, serverUrl} from "../interface/common";
import {onLoad} from "@dcloudio/uni-app";
import {formatSize} from "../util/sizeUtil";

const files = ref(new Array<FileInfo>()),
    paths = ref(new Array<string>());

function getFiles(file: FileInfo) {
  if (!file.isDirectory)
    return
  if (file.name != null)
    paths.value.push(file.name)
  uni.request({
    url: serverUrl + "/directory",
    method: "GET",
    data: {
      path: toPath(paths.value)
    },
    success(callback) {
      const result: Result = callback.data as any;
      if (callback.statusCode == 200)
        files.value = result.data;
    }
  } as RequestOptions);
}

function goto(index: number) {
  paths.value.splice(index + 1);
  getFiles(new FileInfo(null, true, null));
}

function sizeToString(size: number | null) {
  if (size == null)
    return null;
  const result = formatSize(size)
  return result.size + result.unit;
}

function toPath(arr: Array<string>) {
  let result = '/';
  arr.forEach(item => result += item + '/');
  return result
}

// 加载完成后获取根目录文件集
onLoad(() => getFiles(new FileInfo('home', true, null)));
</script>

<template>
  <div id="file-box">
    <uni-breadcrumb separator="/">
      <uni-breadcrumb-item v-for="(path, index) in paths" :key="index" @click="goto(index)">
        {{path}}
      </uni-breadcrumb-item>
    </uni-breadcrumb>
    <uni-list>
      <uni-list-item clickable
          v-for="file in files"
          :disabled=!file.isDirectory
          :title=file.name
          :rightText=sizeToString(file.size)
          @dblclick="getFiles(file)"
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