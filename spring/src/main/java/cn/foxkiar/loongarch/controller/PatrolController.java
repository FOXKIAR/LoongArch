package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.entity.Patrol;
import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.mapper.PatrolMapper;
import cn.foxkiar.loongarch.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        Person currentPerson = (Person) session.getAttribute("LOGIN_USER");
        patrol.setPersonId(currentPerson.getId());
        patrol.setPersonName(currentPerson.getName());
        patrol.setRecordDate(new Date());
        return patrolMapper.insert(patrol) != 0 ?
                ResponseEntity.ok(Result.success(null)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(Result.message("未知错误"));
    }

    @GetMapping("/page/{currentPage}")
    public ResponseEntity<Result<IPage<Patrol>>> page(@PathVariable("currentPage") int currentPage, @ModelAttribute Patrol patrol) {
        LambdaQueryWrapper<Patrol> wrapper = new LambdaQueryWrapper<>();
        if (patrol.getStartDate() != null) wrapper.ge(Patrol::getRecordDate, new Date(patrol.getStartDate()));
        if (patrol.getEndDate() != null) wrapper.le(Patrol::getRecordDate, new Date(patrol.getEndDate()));
        if (patrol.getPersonId() != null) wrapper.eq(Patrol::getPersonId, patrol.getPersonId());
        if (patrol.getPersonName() != null) wrapper.like(Patrol::getPersonName, patrol.getPersonName());
        if (patrol.getIsNormal() != null) wrapper.eq(Patrol::getIsNormal, patrol.getIsNormal());
        if (patrol.getComment() != null) wrapper.like(Patrol::getComment, patrol.getComment());
        wrapper.orderByAsc(Patrol::getRecordDate);
        return ResponseEntity.ok(Result.success(patrolMapper.selectPage(new Page<>(currentPage, 10), wrapper)));
    }
}
