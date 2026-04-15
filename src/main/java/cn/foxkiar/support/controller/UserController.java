package cn.foxkiar.support.controller;

import cn.foxkiar.support.entity.Result;
import cn.foxkiar.support.entity.User;
import cn.foxkiar.support.service.UserService;
import cn.foxkiar.support.utils.JwtUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody User user) throws IllegalAccessException {
        user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPasswordHash, MD5.create().digestHex(user.getPassword()))
        );
        if (user == null)
            return new ResponseEntity<>(Result.fail("用户名或密码错误"), HttpStatus.UNAUTHORIZED);
        String jwt = JwtUtil.generateToken(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "token=" + jwt);
        return new ResponseEntity<>(Result.success(jwt), headers, HttpStatus.OK);
    }
}
