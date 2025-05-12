# 项目介绍
在不可控国际因素及国内数字经济转型需求的驱动下，我国IT 产业自主可控的紧迫性愈加凸显，为了解决核心技术“卡脖子”的问题，科技自立自强已是我国的一项国家战略。  
本项目《基于龙架构国产操作系统的服务器运维管理平台》又作者本人独自开发，也为国产化生态技术也出一份力。

# 环境依赖
+ node.js
+ [server端](https://github.com/FOXKIAR/LoongArch/tree/server-spring)

# 目录结构描述
    ├── src                                                 // 源代码目录
    │   ├── api                                             // 接口目录
    │       ├── common.ts                                   // 公共接口文件
    │       ├── host.ts                                     // 设备信息模块后端接口
    │       ├── user.ts                                     // 用户模块后端接口
    │   ├── pages                                           // 页面目录
    │       ├── database.vue                                // 数据库模块
    │       ├── index.vue                                   // 主页
    │       ├── log.vue                                     // 日志模块
    │       ├── login.vue                                   // 登录页面
    │       ├── settings.vue                                // 设置
    │       ├── status.vue                                  // 设备状态模块
    │       ├── terminal.vue                                // 终端
    │       ├── user.vue                                    // 用户模块
    │   ├── template                                        // 公用 vue 模板目录
    │       ├── lang-select.vue                             // 选择语言 vue 模板
    │       ├── menu-bar.vue                                // 菜单栏 vue 模板
    │   ├── util                                            // 工具目录
    │       ├── languages                                   // 各语言显示存储目录
    │           ├── chinese-simplified.ts                   // 用于存储简体中文显示
    │           ├── english.ts                              // 用于存储英文显示
    │       ├── formatUtil.ts                               // 用于将数据格式化为可读格式
    │       ├── lang.ts                                     // 用于语言转换
    │   ├── App.vue                                         // 应用配置文件，用来配置 App 全局样式以及监听应用生命周期
    │   ├── main.js                                         // Vue 初始化入口文件
    │   ├── manifest.json                                   // 配置应用名称、appid、logo、版本等打包信息
    │   ├── pages.json                                      // 配置页面路由、导航条、选项卡等页面类信息
    │   ├── uni.scss                                        // 内置的常用样式变量
    ├── index.html                                          // 主入口页面
    ├── index.html                                          // 主入口页面
    ├── package.json                                        // npm 依赖文件
    ├── README.md                                           // 自述文件
    └── vite.config.js                                      // vite 配置文件

# 使用说明
您可以使用git克隆到本地后使用：
```shell
git clone -b client-uniapp https://github.com/FOXKIAR/LoongArch.git
cd client-uniapp
npm install
npm run dev:h5
```
或者下载ZIP解压后使用：
```shell
wget https://codeload.github.com/FOXKIAR/LoongArch/zip/refs/heads/server-spring
unzip client-uniapp.zip
npm install
npm run dev:h5
```

