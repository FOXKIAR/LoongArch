package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.service.UserService;
import cn.foxkiar.loongarch.util.Base;
import cn.foxkiar.loongarch.util.Result;
import cn.foxkiar.loongarch.util.ValidatedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static cn.foxkiar.loongarch.util.Result.message;
import static cn.foxkiar.loongarch.util.Result.success;
import static java.util.Objects.isNull;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Result<User>> login(@RequestBody @Validated User user, HttpSession session) {
        user.setPassword(Base.encoder(user.getPassword()));
        if (isNull(user = userService.getUser(user)))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body(message("用户名或密码错误"));
        session.setAttribute("PERMISSION", user.getPermission());
        return ResponseEntity.ok(success(user));
    }

    @PutMapping("/append")
    public ResponseEntity<Result<User>> append(@RequestBody @Validated ValidatedList<User> users) {
        for (User user : users.getData()) {
            user.setPassword(Base.encoder(user.getPassword()));
            userService.insertUser(user);
        }
        return ResponseEntity.ok(success(null));
    }
}
