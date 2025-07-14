## 项目介绍

为实现国产化转型，基于国产化操作系统的运维管理平台。

## 📋 目录
+ [界面](#-界面)
+ [环境依赖](#-环境依赖)
+ [快速开始](#-快速开始)

## 📟️ 界面

## 🖥️ 环境依赖
+ java 17 及以上
+ openGauss 5.0.0 及以上
+ node.js

## 🚀 快速开始
你可以直接下载已经打包好的jar包来运行  
```shell
wget https://github.com/FOXKIAR/LoongArch/releases/download/v0.0.1/loong-arch-0.0.1.jar # 下载文件
java -jar loong-arch-0.0.1.jar # 运行
```
但在此之前你需要启动高斯数据库并导入 `loong_arch.sql` 文件
```shell
su omm # 切换用户
gs_ctl start # 启动高斯数据库
gsql -U username -d databasename -f loong_arch.sql # 导入文件
```

或者你也可以克隆它然后自己再做更改
```shell
git clone https://github.com/FOXKIAR/LoongArch.git
```