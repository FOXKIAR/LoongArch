package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.service.UserService;
import cn.foxkiar.loongarch.util.Result;
import cn.foxkiar.loongarch.util.ValidatedList;
import cn.hutool.core.codec.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

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
        user.setPassword(Base64.encode(user.getPassword()));
        if (isNull(user = userService.getUser(user)))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body(message(Result.Message.INCORRECT_USERNAME_OR_PASSWORD));
        session.setAttribute("PERMISSION", user.getPermission());
        return ResponseEntity.ok(success(user));
    }

    @GetMapping("/all")
    public ResponseEntity<Result<List<User>>> getAll() {
        return ResponseEntity.ok(Result.success(userService.getUsers()));
    }

    @PutMapping("/append")
    public ResponseEntity<Result<User>> append(@RequestBody @Validated ValidatedList<User> users) {
        for (User user : users.getData()) {
            user.setPassword(Base64.encode(user.getPassword()));
            userService.insertUser(user);
        }
        return ResponseEntity.ok(success(null));
    }
}
