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
    │               ├── controller                          // 控制层接口目录
    │               ├── entity                              // 实体类目录
    │               ├── exception                           // 异常类目录
    │               ├── mapper                              // 持久层目录
    │               ├── service                             // 服务层目录
    │               ├── util                                // 工具类目录
    │               ├── validation                          // 效验工具目录
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
```bash
# 克隆项目到本地
ash
# 克隆项目到本地
git clone URL_ADDRESSgit clone https://github.com/FOXKIAR/LoongArch.git
# 进入项目目录
cd LoongArch
# 编译项目
mvn clean package
# 运行项目
java -jar target/loong-arch-0.0.1-SNAPSHOT.jar
```
或者下载ZIP解压后使用：
```bash
# 下载ZIP
wget URL_ADDRESSwget https://codeload.github.com/FOXKIAR/LoongArch/zip/refs/heads/server-spring
# 解压ZIP
unzip server-spring.zip
# 编译项目
mvn clean package
# 运行项目
java -jar target/loong-arch-0.0.1-SNAPSHOT.jar  
```