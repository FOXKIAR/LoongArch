# 项目介绍
在不可控国际因素及国内数字经济转型需求的驱动下，我国IT 产业自主可控的紧迫性愈加凸显，为了解决核心技术“卡脖子”的问题，科技自立自强已是我国的一项国家战略。  
本项目《基于龙架构国产操作系统的服务器运维管理平台》由作者本人独自开发，也为国产化生态技术也出一份力。

# 环境依赖
+ java

# 目录结构描述
    ├── src                                                 // 源代码目录
    │   ├── main                                            // 主代码目录
    │       ├── java                                        // java 代码目录
    │           ├── cn/foxkiar/loongarch                    // 包名
    │               ├── config                              // 配置目录
    │                   ├── MybatisPlusConfig.java          // Mybatis-Plus配置文件
    │               ├── controller                          // 控制层接口目录
    │                   ├── HostController.java             // 设备信息控制层接口
    │                   ├── UserController.java             // 用户控制层接口
    │               ├── entity                              // 实体类目录
    │                   ├── HostInfo.java                   // 设备信息实体
    │                   ├── User.java                       // 用户实体
    │               ├── exception                           // 异常类目录
    │                   ├── GlobalExceptionHandler.java     // 全局异常处理控制器
    │               ├── mapper                              // 持久层目录
    │                   ├── UserMapper.java                 // 用户持久层
    │               ├── service                             // 服务层目录
    │                   ├── UserService.java                // 用户服务层
    │               ├── util                                // 工具类目录
    │                   ├── Result.java                     // 统一封装返回体格式
    │                   ├── SizeUtil.java                   // 存储空间大小单位工具
    │                   ├── TimeUtil.java                   // 时间单位工具
    │               ├── validation                          // 效验工具目录
    │                   ├── IsPhoneValidator.java           // 验证是否为手机号
    │                   ├── Phone.java                      // 效验手机号注解类
    │                   ├── ValidatedList.java              // 效验 List
    │               ├── LoongArchApplication.java           // 启动 Main 函数文件
    │       ├── resource                                    // 资源目录
    │           ├── static                                  // 静态资源目录
    │               ├── index.html                          // spring 主页面
    │           ├── application.yaml                        // spring 配置文件
    │   ├── test                                            // 测试代码目录
    │       ├── java                                        // java 代码目录
    │           ├── cn/foxkiar/loongarch                    // 包名
    │               ├── LoongArchApplicationTests.java      // spring 测试运行文件
    ├── pom.xml                                             // maven 依赖文件
    └── README.md                                           // 自述文件

# 使用说明
您可以使用git克隆到本地后使用：
```shell
git clone https://github.com/FOXKIAR/LoongArch.git
cd LoongArch
maven package
java -jar target/loong-arch-0.0.1-SNAPSHOT.jar
```
或者下载ZIP解压后使用：
```shell
wget https://codeload.github.com/FOXKIAR/LoongArch/zip/refs/heads/server-spring
unzip server-spring.zip
maven package
java -jar target/loong-arch-0.0.1-SNAPSHOT.jar
```

