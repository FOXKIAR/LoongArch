package cn.foxkiar.support.controller;

import cn.foxkiar.support.entity.Result;
import cn.foxkiar.support.entity.User;
import cn.foxkiar.support.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Reader r = new FileReader("config/admin.yaml");
        if (yaml.loadAs(r, User.class).equals(user))
            return new Result<String>().success(JwtUtil.generateToken(user));
        return new Result<String>().fail(HttpStatus.UNAUTHORIZED,"用户名或密码错误");
    }
}
