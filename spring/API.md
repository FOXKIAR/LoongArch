# 用户接口
## 登录
### 请求信息
+ url: /user/login
+ method: POST
### 请求参数
| 参数名      | 类型     | 必选 | 说明              |
|----------|--------|----|-----------------|
| account  | String | 是  | 登录账号，长度要求8到16字符 |
| password | String | 是  | 登录密码，长度要求8到16字符 |
### 请求示例
```shell
curl -X POST 'http://localhost:8080/user/login' \
    -H 'Content-Type: application/json' \
    -d '{"account": "admin123", "password", "admin123"}'
```
### 响应格式
| 键               | 值类型                                                         | 说明   |
|-----------------|-------------------------------------------------------------|------|
| msg             | String                                                      | 提示信息 |
| data            | [User](src/main/java/cn/foxkiar/loongarch/entity/User.java) | 数据对象 |
| data.id         | Integer                                                     | 用户ID |
| data.name       | String                                                      | 用户名  |
| data.account    | String                                                      | 登录账号 |
| data.password   | String                                                      | 登录密码 |
| data.permission | Integer                                                     | 权限   |
| data.email      | String                                                      | 邮箱   |
| data.phone      | String                                                      | 手机号  |
### 响应示例
+ 成功，同时将返回 Set-Cookie
```json
{
    "msg": "成功",
    "data":
    {
        "id": 1,
        "name": null,
        "account": "Administrator",
        "password": null,
        "permission": 15,
        "email": null,
        "phone": null
    }
}
```
+ 失败，http状态码为403
```json
{
    "msg": "账号或密码不正确",
    "data": null
}
```
## 添加
### 请求信息
+ url: /user/append
+ method: POST
### 请求参数
<strong style="color: yellow;">注意：这个接口的参数使用List包裹。</strong>

| 参数名        | 类型     | 必选 | 说明                                      |
|------------|--------|----|-----------------------------------------|
| id         | int    | 否  | 用户ID                                    |
| name       | String | 是  | 用户姓名                                    |
| account    | String | 是  | 登录账号，长度要求8到16字符                         |
| password   | String | 是  | 登录密码，长度要求8到16字符                         |
| permission | int    | 否  | 权限，4位二进制数每一位分别表示 POST、DELETE、PUT、GET 权限 |
| email      | String | 否  | 邮箱账号                                    |
| phone      | String | 否  | 手机号                                     |
### 请求示例
```shell
curl -X POST 'http://localhost:8080/user/append' \
    -H 'Content-Type: application/json' \
    -d '[{"name": "管理员", "account": "admin123", "password", "admin123", "permission": 0b1111}]'
```
### 响应格式
| 键    | 值类型    | 说明                   |
|------|--------|----------------------|
| msg  | String | 提示信息                 |
| data | Object | 数据对象，这个接口只会返回 null 值 |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data": null
}
```
+ 失败，http状态码为400
```json
{
    "msg": "参数格式错误",
    "data": null
}
```
## 删除
### 请求信息
+ url: /user/delete/${id}
+ method: DELETE
### 请求参数
<strong style="color: yellow">注意：参数在URL中传递</strong>

| 参数名        | 类型     | 必选 | 说明                                      |
|------------|--------|----|-----------------------------------------|
| id         | int    | 是  | 用户ID                                    |
### 请求示例
```shell
curl -X DELETE 'http://localhost:8080/user/delete/10'
```
### 响应格式
| 键    | 值类型    | 说明                   |
|------|--------|----------------------|
| msg  | String | 提示信息                 |
| data | Object | 数据对象，这个接口只会返回 null 值 |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data": null
}
```
+ 失败，http状态码为404
```json
{
    "msg": "未找到该ID",
    "data": null
}
```
## 更新
### 请求信息
+ url: /user/update
+ method: PUT
### 请求参数
| 参数名        | 类型     | 必选 | 说明                                      |
|------------|--------|----|-----------------------------------------|
| id         | int    | 是  | 用户ID                                    |
| name       | String | 是  | 用户姓名                                    |
| account    | String | 是  | 登录账号，长度要求8到16字符                         |
| permission | int    | 是  | 权限，4位二进制数每一位分别表示 POST、DELETE、PUT、GET 权限 |
| email      | String | 是  | 邮箱账号                                    |
| phone      | String | 是  | 手机号                                     |
### 请求示例
```shell
curl -X PUT 'http://localhost:8080/user/update' \
    -H 'Content-Type: application/json' \
    -d '{"id": 1, "name": "管理员", "account": "admin123", "permission": 0b1111, "email": "test@qq.com", "phone": "13333333333"}'
```
### 响应格式
| 键    | 值类型    | 说明                   |
|------|--------|----------------------|
| msg  | String | 提示信息                 |
| data | Object | 数据对象，这个接口只会返回 null 值 |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data": null
}
```
+ 失败，http状态码为404
```json
{
    "msg": "未找到该ID",
    "data": null
}
```
## 获取一页数据
### 请求信息
+ url: /user/page/${currentPage}
+ method: GET
### 请求参数
<strong style="color: yellow">注意：currentPage参数在URL中传递</strong>

| 参数名         | 类型     | 必选 | 说明           |
|-------------|--------|----|--------------|
| currentPage | int    | 是  | 获取第几页用户数据    |
| name        | String | 否  | 用户姓名，根据值模糊查询 |
| account     | String | 否  | 登录账号，根据值模糊查询 |
| permission  | int    | 否  | 权限，根据值查询     |
| email       | String | 否  | 邮箱账号，根据值模糊查询 |
| phone       | String | 否  | 手机号，根据值模糊查询  |
### 请求示例
```shell
curl -X GET 'http://localhost:8080/user/page/1'
```
### 响应格式
| 键            | 值类型          | 说明                                                                        |
|--------------|--------------|---------------------------------------------------------------------------|
| msg          | String       | 提示信息                                                                      |
| data         | Page\<User\> | 数据对象                                                                      |
| data.records | List\<User\> | 当前这一页的所有数据，[泛型详情请参阅](src/main/java/cn/foxkiar/loongarch/entity/User.java) |
| data.total   | int          | 总共有多少数据                                                                   |
| data.size    | int          | 每一页有多少数据                                                                  |
| data.current | int          | 当前页                                                                       |
| data.pages   | int          | 总共有几页                                                                     |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data":
    {
        "records":
        [{
            "id": 1,
            "name": null,
            "account": "Administrator",
            "password": null,
            "permission": 15, 
            "email": null,
            "phone": null
        }, ...
        ],
        "total": 19,
        "size": 10,
        "current": 1,
        "pages": 2
    }
}
```
+ 失败，http状态码为400
```json
{
    "msg": "参数格式错误",
    "data": null
}
```

# 主机信息
## 基本
### 请求信息
+ url: /host/info
+ method: GET
### 请求参数
无
### 请求示例
```shell
curl -X GET 'http://localhost:8080/host/info'
```
### 响应格式
| 键              | 值类型            | 说明        |
|----------------|----------------|-----------|
| msg            | String         | 提示信息      |
| data           | Object         | 数据对象      |
| data.hostname  | String         | 主机名称      |
| data.system    | String         | 系统名称      |
| data.cpu       | String         | CPU名称     |
| data.gpu       | List\<String\> | 显卡名称      |
| data.startTime | Date           | 启动时间      |
| data.uptime    | Long           | 运行时长，单位：秒 |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data":
    {
        "hostname": "ogserver",
        "system": " GNU/Linux 5.10.0-216.0.0.115.oe2203sp4.aarch64",
        "cpu": "AMD Ryzen 7 5800H with Radeon Graphics",
        "gpu": 
        [
            "NVIDIA GeForce RTX 3060 Laptop GPU"
        ],
        "startTime": "2025-05-26T03:33:39.241+00:00",
        "uptime": 20907
    }
}
```
+ 失败，http状态码为500
```json
{
    "msg": "未知异常",
    "data": null
}
```
## 硬件
### 请求信息
+ url: /host/hardware
+ method: GET
### 请求参数
无
### 请求示例
```shell
curl -X GET 'http://localhost:8080/host/hardware'
```
### 响应格式
| 键                       | 值类型             | 说明             |
|-------------------------|-----------------|----------------|
| msg                     | String          | 提示信息           |
| data                    | Object          | 数据对象           |
| data.cpu                | Object          | cpu            |
| data.cpu.used           | Double          | cpu使用率，单位 百分比  |
| data.cpu.free           | Double          | cpu空闲率，单位 百分比  |
| data.disks              | List\<Disk\>    | 磁盘             |
| data.disks.name         | String          | 磁盘名称           |
| data.disks.readBytes    | Long            | 磁盘总读取量，单位 byte |
| data.disks.writeBytes   | Long            | 磁盘总写入量，单位 byte |
| data.diskTotal          | Long            | 磁盘总量，单位 byte   |
| data.diskUsed           | Long            | 磁盘使用量，单位 byte  |
| data.diskFree           | Long            | 磁盘空闲量，单位 byte  |
| data.memory             | Memory          | 内存             |
| data.memory.total       | Long            | 内存总量，单位 byte   |
| data.memory.used        | Long            | 内存使用量，单位 byte  |
| data.memory.free        | Long            | 内存空闲量，单位 byte  |
| data.networks           | List\<Network\> | 网卡             |
| data.networks.name      | String          | 网卡名称           |
| data.networks.upBytes   | Long            | 上传量，单位 byte    |
| data.networks.downBytes | Long            | 下载量，单位 byte    |
### 响应示例
+ 成功
```json
{
    "msg": "成功",
    "data": {
        "cpu": {
            "used": 0.18,
            "free": 99.82
        },
        "disks": [
            {
                "name": "\\\\.\\PHYSICALDRIVE1",
                "readBytes": 50171904,
                "writeBytes": 81920
            },
            {
                "name": "\\\\.\\PHYSICALDRIVE0",
                "readBytes": 17513166848,
                "writeBytes": 17916362240
            }
        ],
        "diskTotal": 2512192004096,
        "diskUsed": 1840257318912,
        "diskFree": 671934685184,
        "memory": {
            "total": 17008422912,
            "used": 6932856832,
            "free": 10075566080
        },
        "networks": [
            {
                "name": "eth7",
                "upBytes": 2200720820,
                "downBytes": 5455257126
            },
            {
                "name": "eth9",
                "upBytes": 270618,
                "downBytes": 0
            },
            {
                "name": "eth27",
                "upBytes": 99703,
                "downBytes": 19374
            }
        ]
    }
}
```
+ 失败，http状态码为500
```json
{
    "msg": "未知异常",
    "data": null
}
```