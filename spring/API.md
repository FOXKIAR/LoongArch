# 用户接口
## 登录
### 请求信息
+ url: /user/login
+ method: POST
### 请求参数
| 参数名      | 类型     | 必选 | 说明              |
|----------|--------|----|-----------------|
| account  | string | 是  | 登录账号，长度要求8到16字符 |
| password | string | 是  | 登录密码，长度要求8到16字符 |
### 请求示例
```shell
curl -X POST 'http://localhost:8080/user/login' \
    -H 'Content-Type: application/json' \
    -d '{"account": "admin123", "password", "admin123"}'
```
### 响应格式
| 键    | 值类型    | 说明                                                                       |
|------|--------|--------------------------------------------------------------------------|
| msg  | string | 提示信息                                                                     |
| data | User   | 数据对象，请查阅[User.java](src/main/java/cn/foxkiar/loongarch/entity/User.java) |
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
| name       | string | 是  | 用户姓名                                    |
| account    | string | 是  | 登录账号，长度要求8到16字符                         |
| password   | string | 是  | 登录密码，长度要求8到16字符                         |
| permission | int    | 否  | 权限，4位二进制数每一位分别表示 POST、DELETE、PUT、GET 权限 |
| email      | string | 否  | 邮箱账号                                    |
| phone      | string | 否  | 手机号                                     |
### 请求示例
```shell
curl -X POST 'http://localhost:8080/user/append' \
    -H 'Content-Type: application/json' \
    -d '[{"name": "管理员", "account": "admin123", "password", "admin123", "permission": 0b1111}]'
```
### 响应格式
| 键    | 值类型    | 说明   |
|------|--------|------|
| msg  | string | 提示信息 |
| data | null   | 数据对象 |
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
| 键    | 值类型    | 说明   |
|------|--------|------|
| msg  | string | 提示信息 |
| data | null   | 数据对象 |
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
| name       | string | 是  | 用户姓名                                    |
| account    | string | 是  | 登录账号，长度要求8到16字符                         |
| permission | int    | 是  | 权限，4位二进制数每一位分别表示 POST、DELETE、PUT、GET 权限 |
| email      | string | 是  | 邮箱账号                                    |
| phone      | string | 是  | 手机号                                     |
### 请求示例
```shell
curl -X PUT 'http://localhost:8080/user/update' \
    -H 'Content-Type: application/json' \
    -d '{"id": 1, "name": "管理员", "account": "admin123", "permission": 0b1111, "email": "test@qq.com", "phone": "13333333333"}'
```
### 响应格式
| 键    | 值类型    | 说明   |
|------|--------|------|
| msg  | string | 提示信息 |
| data | null   | 数据对象 |
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
+ method: PUT
### 请求参数
<strong style="color: yellow">注意：currentPage参数在URL中传递</strong>

| 参数名         | 类型     | 必选 | 说明           |
|-------------|--------|----|--------------|
| currentPage | int    | 是  | 获取第几页用户数据    |
| name        | string | 否  | 用户姓名，根据值模糊查询 |
| account     | string | 否  | 登录账号，根据值模糊查询 |
| permission  | int    | 否  | 权限，根据值查询     |
| email       | string | 否  | 邮箱账号，根据值模糊查询 |
| phone       | string | 否  | 手机号，根据值模糊查询  |
### 请求示例
```shell
curl -X GET 'http://localhost:8080/user/page/1'
```
### 响应格式
| 键            | 值类型          | 说明         |
|--------------|--------------|------------|
| msg          | string       | 提示信息       |
| data         | Page         | 数据对象       |
| data.records | List\<User\> | 当前这一页的所有数据 |
| data.total   | int          | 总共有多少数据    |
| data.size    | int          | 每一页有多少数据   |
| data.current | int          | 当前页        |
| data.pages   | int          | 总共有几页      |
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

