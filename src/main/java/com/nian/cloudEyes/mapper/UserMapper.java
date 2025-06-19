package com.nian.cloudEyes.mapper;

import com.nian.cloudEyes.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author admin
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2025-06-19 16:12:31
* @Entity com.nian.cloudEyes.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




