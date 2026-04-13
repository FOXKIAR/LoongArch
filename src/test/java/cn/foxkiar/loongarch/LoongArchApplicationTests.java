package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoongArchApplicationTests {

    @Test
    void contextLoads() {
        try {
            throw new AuthenticationException();
        } catch (Exception e) {
            log.error("e: ", e);
        }
    }
}
