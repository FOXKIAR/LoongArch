package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.mapper.UserMapper;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        Person person = new Person();
        person.setId(1);
        person.setPassword(MD5.create().digestHex("woaini1314@!&"));

        userMapper.updateById(person);
    }

}
