package cn.foxkiar.loongarch.service;

import cn.foxkiar.loongarch.entity.User;
import cn.foxkiar.loongarch.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    public IPage<User> getUserPages(Page<User> page, User user) {
        return userMapper.selectPage(page, new LambdaQueryWrapper<>(user));
    }
}
