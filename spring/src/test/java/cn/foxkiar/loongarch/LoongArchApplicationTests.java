package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.mapper.DatabaseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    DatabaseMapper databaseMapper;

    @Test
    void contextLoads() {
        System.out.println(databaseMapper.getTableStruct("loong_arch", "user"));
    }

}
