package cn.foxkiar.loongarch;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LoongArchApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        Page<User> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        List<OrderItem> orderItems = new ArrayList<>(){{
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn("id");
            orderItem.setAsc(false);
            add(orderItem);
        }};
        page.setOrders(orderItems);
        System.out.println(userMapper.selectPage(page,null));
    }

}
