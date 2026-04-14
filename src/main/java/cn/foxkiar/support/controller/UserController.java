package cn.foxkiar.support.controller;

import cn.foxkiar.support.entity.Result;
import cn.foxkiar.support.entity.User;
import cn.foxkiar.support.utils.JwtUtil;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@RestController
@RequestMapping("/user")
public class UserController {
    @Setter
    @Getter
    public static class LoginForm extends User {
        private Boolean isKeepLogged;
    }
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginForm form) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Reader r = new FileReader("config/admin.yaml");
        if (!yaml.loadAs(r, User.class).equals(form))
            return new ResponseEntity<>(Result.fail("用户名或密码错误"), HttpStatus.UNAUTHORIZED);
        String jwt = JwtUtil.generateToken(form.getIsKeepLogged());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "token=" + jwt);
        return new ResponseEntity<>(Result.success(jwt), headers, HttpStatus.OK);
    }
}
