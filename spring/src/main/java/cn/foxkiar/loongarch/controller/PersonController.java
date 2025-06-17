package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.mapper.UserMapper;
import cn.foxkiar.loongarch.util.Result;
import cn.foxkiar.loongarch.validation.Groups;
import cn.foxkiar.loongarch.validation.ValidatedList;
import cn.hutool.crypto.digest.MD5;
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
@RequestMapping("/person")
public class PersonController {
    final UserMapper userMapper;

    public PersonController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<Result<Person>> login(@RequestBody @Validated({Groups.Login.class}) Person person, HttpSession session) {
        person.setPassword(MD5.create().digestHex(person.getPassword()));
        if (isNull(person = userMapper.selectOne(new LambdaQueryWrapper<>(person))))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body(message("账号或密码不正确"));
        session.setAttribute("LOGIN_USER", person);
        return ResponseEntity.ok(success(person));
    }

    @PostMapping("/append")
    public ResponseEntity<Result<?>> append(@RequestBody @Validated ValidatedList<Person> people) {
        int successCount = 0;
        for (Person person : people.getData()) {
            person.setPassword(MD5.create().digestHex(person.getPassword()));
            successCount += userMapper.insert(person);
        }
        return successCount == people.size() ?
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
    public ResponseEntity<Result<?>> update(@RequestBody @Validated({Groups.Save.class}) Person person) {
        return userMapper.updateById(person) != 0 ?
                ResponseEntity.ok(Result.success(null)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(Result.message("未找到该ID"));
    }

    @GetMapping("/page/{currentPage}")
    public ResponseEntity<Result<IPage<Person>>> page(@PathVariable Integer currentPage, @ModelAttribute Person person) {
        LambdaQueryWrapper<Person> wrapper = new LambdaQueryWrapper<>();
        if (person.getName() != null) wrapper.like(Person::getName, person.getName());
        if (person.getAccount() != null) wrapper.like(Person::getAccount, person.getAccount());
        if (person.getPermission() != null) wrapper.eq(Person::getPermission, person.getPermission());
        if (person.getEmail() != null) wrapper.like(Person::getEmail, person.getEmail());
        if (person.getPhone() != null) wrapper.like(Person::getPhone, person.getPhone());
        return ResponseEntity.ok(Result.success(userMapper.selectPage(new Page<>(currentPage, 10), wrapper)));
    }
}
