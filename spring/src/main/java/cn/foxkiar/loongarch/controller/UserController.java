package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.UserMapper;
import cn.foxkiar.loongarch.util.Result;
import cn.foxkiar.loongarch.validation.Groups;
import cn.foxkiar.loongarch.validation.ValidatedList;
import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import static cn.foxkiar.loongarch.util.Result.message;
import static cn.foxkiar.loongarch.util.Result.success;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<Result<User>> login(@RequestBody @Validated({Groups.Login.class}) User user, HttpSession session) {
        user.setPassword(Base64.encode(user.getPassword()));
        if (isNull(user = userMapper.selectOne(new LambdaQueryWrapper<>(user))))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body(message("账号或密码不正确"));
        session.setAttribute("LOGIN_USER", user);
        return ResponseEntity.ok(success(user));
    }

    @PostMapping("/append")
    public ResponseEntity<Result<?>> append(@RequestBody @Validated ValidatedList<User> users) {
        int successCount = 0;
        for (User user : users.getData()) {
            user.setPassword(Base64.encode(user.getPassword()));
            successCount += userMapper.insert(user);
        }
        return successCount == users.size() ?
                ResponseEntity.ok(success(null)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(Result.message("未知错误"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result<?>> deleteById(@PathVariable Integer id) {
        return userMapper.deleteById(id) != 0 ?
                ResponseEntity.ok(Result.success(null)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(Result.message("未找到该ID"));
    }

    @PutMapping("/update")
    public ResponseEntity<Result<?>> update(@RequestBody @Validated({Groups.Save.class}) User user) {
        return userMapper.updateById(user) != 0 ?
                ResponseEntity.ok(Result.success(null)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(Result.message("未找到该ID"));
    }

    @GetMapping("/page/{currentPage}")
    public ResponseEntity<Result<IPage<User>>> page(@PathVariable Integer currentPage, @ModelAttribute User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (user.getName() != null) wrapper.like(User::getName, user.getName());
        if (user.getAccount() != null) wrapper.like(User::getAccount, user.getAccount());
        if (user.getPermission() != null) wrapper.eq(User::getPermission, user.getPermission());
        if (user.getEmail() != null) wrapper.like(User::getEmail, user.getEmail());
        if (user.getPhone() != null) wrapper.like(User::getPhone, user.getPhone());
        return ResponseEntity.ok(Result.success(userMapper.selectPage(new Page<>(currentPage, 10), wrapper)));
    }
}
