package com.osrs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.osrs.entity.User;
import com.osrs.service.UserService;
import com.osrs.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

/**
* @author jacky
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-06-04 18:23:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserInfo(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public int register(User user) {
        return userMapper.insertUser(user);
    }
}




