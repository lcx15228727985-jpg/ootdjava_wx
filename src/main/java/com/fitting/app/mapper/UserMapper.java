package com.fitting.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fitting.app.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}