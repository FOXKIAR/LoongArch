package cn.foxkiar.loongarch;

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
                .stream().map(HWDiskStore::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);


    }

}
