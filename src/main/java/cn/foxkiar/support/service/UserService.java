package cn.foxkiar.support.service;

import cn.foxkiar.support.entity.User;
import cn.foxkiar.support.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {
}
