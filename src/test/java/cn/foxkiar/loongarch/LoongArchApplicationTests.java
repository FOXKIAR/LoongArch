package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.util.SizeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        for (File root : File.listRoots()) {
            System.out.println(SizeUtil.formatSize(root.getTotalSpace()));
        }
    }

}
