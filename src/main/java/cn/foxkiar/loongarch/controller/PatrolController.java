package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.Patrol;
import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.PatrolMapper;
import cn.foxkiar.loongarch.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/patrol")
public class PatrolController {
    final PatrolMapper patrolMapper;

    public PatrolController(PatrolMapper patrolMapper) {
        this.patrolMapper = patrolMapper;
    }

    @PostMapping("/append")
    public ResponseEntity<Result<?>> append(@RequestBody Patrol patrol,  HttpSession session) {
        User currentUser = (User) session.getAttribute("LOGIN_USER");
        System.out.println(currentUser);
        patrol.setUserId(currentUser.getId());
        patrol.setUserName(currentUser.getName());
        patrol.setRecordDate(new Date());
        return patrolMapper.insert(patrol) != 0 ?
                ResponseEntity.ok(Result.success(null)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(Result.message("未知错误"));
    }
}
