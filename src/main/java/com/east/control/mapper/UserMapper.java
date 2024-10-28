package com.east.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.east.control.model.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    // 可以在这里定义自定义的方法

    @Select("select * from user where id = 1")
    User getUserByUsername();
}