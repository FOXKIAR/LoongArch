## 项目介绍
为实现国产化转型，基于国产化操作系统的运维管理平台。

使用spring作为后端业务逻辑功能实现、前端使用uni-app + uni-ui实现界面、功能。

目前实现了用户管理、数据库管理、服务器资源负载可视化等基本运维功能。


## 环境依赖
+ java 17 及以上
+ mariadb
+ node.js

## 目录结构描述
```markdown
.
├── spring
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── cn
│   │   │   │       └── foxkiar
│   │   │   │           └── loongarch
│   │   │   │               ├── config                              // 该目录存放配置类相关
│   │   │   │               │   ├── CorsConfig.java                 // 配置跨域
│   │   │   │               │   └── MybatisPlusConfig.java          // 分页插件
│   │   │   │               ├── controller                          // 存放各种接口，详细请查阅 /spring/API.md
│   │   │   │               │   ├── DatabaseController.java 
│   │   │   │               │   ├── HostController.java 
│   │   │   │               │   ├── PatrolController.java   
│   │   │   │               │   └── UserController.java 
│   │   │   │               ├── entity                              // 存放实体类相关
│   │   │   │               │   ├── Patrol.java                     // 巡检记录实体类
│   │   │   │               │   └── Person.java                       // 用户实体类
│   │   │   │               ├── exception   
│   │   │   │               │   └── GlobalExceptionHandler.java     // 全局异常处理
│   │   │   │               ├── mapper                              // 存放数据库持久层相关
│   │   │   │               │   ├── DatabaseMapper.java 
│   │   │   │               │   ├── PatrolMapper.java   
│   │   │   │               │   └── UserMapper.java 
│   │   │   │               ├── util    
│   │   │   │               │   └── Result.java                     // 统一响应格式封装
│   │   │   │               ├── validation  
│   │   │   │               │   ├── Groups.java                     // 为分组效验定义此类
│   │   │   │               │   ├── IsPhoneValidator.java           // 自定义效验规则：是否为手机号
│   │   │   │               │   ├── Phone.java                      // 自定义效验注解
│   │   │   │               │   └── ValidatedList.java              // 为确保list中也进行效验而创建此类
│   │   │   │               └── LoongArchApplication.java
│   │   │   └── resources
│   │   └── test
│   ├── pom.xml
│   └── API.md
├── uniapp
│   ├── src
│   │   ├── interface       
│   │   │   ├── common.ts
│   │   │   ├── patrol.ts
│   │   │   └── person.ts
│   │   ├── pages                   // 存放vue页面
│   │   │   ├── database.vue
│   │   │   ├── login.vue
│   │   │   ├── patrol.vue
│   │   │   └── person.vue
│   │   ├── template                // 自定义vue模板
│   │   │   ├── my-menu-bar.vue
│   │   │   └── my-notice-bar.vue
│   │   ├── template                // 自定义工具
│   │   │   └── timeUtil.ts
│   │   ├── App.vue
│   │   ├── main.js
│   │   ├── manifest.json
│   │   ├── pages.json
│   │   └── uni.scss
│   ├── index.html
│   ├── package.json
│   ├── tsconfig.json
│   └── vite.config.js
├── .gitignore
├── loong_arch.sql
└── README.md
```
## 使用说明
你可以直接下载已经打包好的jar包来运行

wget https://github.com/FOXKIAR/LoongArch/releases/download/V0.0.1-test/loong-arch-0.0.1-SNAPSHOT.jar

首先你需要有一个mariadb数据库，然后进入数据库
```mysql
SOURCE loong_arch.sql;
INSERT INTO person(account, password, permission) VALUE('${你的账号}', md5('${你的密码}'), 0b1111);
```

或者你也可以克隆它然后自己再做更改
```shell
git clone https://github.com/FOXKIAR/LoongArch.git
```