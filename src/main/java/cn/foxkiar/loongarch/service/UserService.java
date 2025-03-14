package cn.foxkiar.loongarch.service;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(User user) {
        return userMapper.selectOne(new LambdaQueryWrapper<>(user));
    }

    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
