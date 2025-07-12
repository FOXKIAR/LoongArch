## 项目介绍
为实现国产化转型，基于国产化操作系统的运维管理平台。

## 🖥️ 环境依赖
+ java 17 及以上
+ openGauss 数据库
+ node.js

## 使用说明
你可以直接下载已经打包好的jar包来运行
https://github.com/FOXKIAR/LoongArch/releases

首先你需要有一个`openGauss`数据库，然后进入数据库
```postgresql
INSERT INTO person (name, account, password, permission) VALUES('${你的姓名}', '${你的账号}', md5('${你的密码}'), 0b111);
```

或者你也可以克隆它然后自己再做更改
```commandline
git clone https://github.com/FOXKIAR/LoongArch.git
```