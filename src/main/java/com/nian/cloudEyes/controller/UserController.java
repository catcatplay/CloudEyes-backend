package com.nian.cloudEyes.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nian.cloudEyes.common.BaseResponse;
import com.nian.cloudEyes.common.ResultUtils;
import com.nian.cloudEyes.model.dto.UserDto;
import com.nian.cloudEyes.model.vo.UserVo;
import com.nian.cloudEyes.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<?> userRegister(UserDto userDto) {
        userService.register(userDto);
        return ResultUtils.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<UserVo> userLogin(UserDto userDto, HttpServletRequest request) {
        return ResultUtils.success(userService.login(userDto, request));
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    public BaseResponse<?> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResultUtils.success();
    }

    /**
     * 分页查询
     */
    @PostMapping("/pageList")
    public BaseResponse<Page<UserVo>> pageList(UserDto userDto) {
        return ResultUtils.success(userService.pageList(userDto));
    }
}
