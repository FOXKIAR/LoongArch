package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.entity.Patrol;
import cn.foxkiar.loongarch.mapper.PatrolMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
