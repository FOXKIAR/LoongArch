package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.util.SizeUtil;
import cn.foxkiar.loongarch.util.TimeUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import oshi.hardware.NetworkIF;

import java.util.List;

@SpringBootTest
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        List<NetworkIF> networkIFList = OshiUtil.getNetworkIFs();
        long up = 0L, down = 0L;
        for (NetworkIF networkIF : networkIFList) {
            System.out.println(SizeUtil.formatSize(networkIF.getSpeed()));
            down += networkIF.getBytesRecv();
            up += networkIF.getBytesSent();
        }
        System.out.println(SizeUtil.formatSize(up));
        System.out.println(SizeUtil.formatSize(down));
    }

}
