package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.util.SizeUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import oshi.hardware.HWDiskStore;
import java.util.stream.Collectors;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        OshiUtil.getDiskStores()
                .stream().map(HWDiskStore::getReads)
                .collect(Collectors.toList())
                .forEach(temp -> System.out.println(SizeUtil.formatSize(temp * 8 / 1024)));


    }

}
