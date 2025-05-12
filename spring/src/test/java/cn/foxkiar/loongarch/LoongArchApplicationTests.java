package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.entity.Patrol;
import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.PatrolMapper;
import cn.foxkiar.loongarch.mapper.UserMapper;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    PatrolMapper patrolMapper;

    @Test
    void contextLoads() {
        Patrol patrol = new Patrol();
        patrol.setRecordDate(new Date());
        patrol.setUserId(1);
        patrol.setUserName("foxkiar");
        patrol.setIsNormal(true);
        patrolMapper.insert(patrol);
    }

}
