package cn.foxkiar.support.config;

import cn.foxkiar.support.entity.User;
import cn.foxkiar.support.service.UserService;
import cn.foxkiar.support.utils.ConfigUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class Initializer implements CommandLineRunner {
    private final UserService userService;
    private Map<String, Object> config;

    @Autowired
    public Initializer(UserService userService) {
        this.userService = userService;
        this.config = new HashMap<>();
    }

    @Override
    public void run(String... args) throws Exception {
        config = ConfigUtil.readConfig();
        createAdminUser();
    }

    private void createAdminUser() {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getIsAdmin, true));
        if (user != null)
            return;
        user = new User();
        user.setUsername("管理员");
        user.setAccount("admin");
        user.setPasswordHash(MD5.create().digestHex(config.get("defaultPassword").toString()));
        user.setIsAdmin(true);
        userService.save(user);
    }
}