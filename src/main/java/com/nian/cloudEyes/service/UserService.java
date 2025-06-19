package com.nian.cloudEyes.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nian.cloudEyes.common.BaseResponse;
import com.nian.cloudEyes.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nian.cloudEyes.model.dto.UserDto;
import com.nian.cloudEyes.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author admin
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-06-19 16:12:31
*/
public interface UserService extends IService<User> {

    void register(UserDto userDto);

    UserVo login(UserDto userDto, HttpServletRequest request);

    /**
     * 获取当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    void logout(HttpServletRequest request);

    Page<UserVo> pageList(UserDto userDto);
}
