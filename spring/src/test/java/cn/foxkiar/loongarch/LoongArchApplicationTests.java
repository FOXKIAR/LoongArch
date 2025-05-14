package cn.foxkiar.loongarch;

import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(OshiUtil.getHardware().getSoundCards());
    }

}
