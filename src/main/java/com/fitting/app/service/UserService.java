package com.fitting.app.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitting.app.entity.User;
import com.fitting.app.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}