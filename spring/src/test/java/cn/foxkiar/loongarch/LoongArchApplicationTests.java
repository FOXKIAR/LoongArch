package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.entity.Person;
import cn.foxkiar.loongarch.mapper.PersonMapper;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    PersonMapper personMapper;

    @Test
    void contextLoads() {
        Person person = new Person();
        person.setId(1);
        person.setName("foxkiar");
        person.setAccount("Administrator");
        person.setPassword(MD5.create().digestHex("woaini1314@!&"));
        person.setPermission(7);

        personMapper.insert(person);
    }

}
