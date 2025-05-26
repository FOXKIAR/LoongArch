package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.mapper.DatabaseMapper;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    DatabaseMapper databaseMapper;

    @Test
    void contextLoads() {
        System.out.println(OshiUtil.getCpuInfo().getCpuModel());
    }

}
