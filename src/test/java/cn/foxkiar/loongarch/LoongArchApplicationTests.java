package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.util.SizeUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        OshiUtil.getDiskStores().forEach(hwDiskStore -> {
            System.out.println(SizeUtil.formatSize(hwDiskStore.getSize()));
        });
    }

}
