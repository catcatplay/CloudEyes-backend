package com.nian.cloudEyes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nian.cloudEyes.constant.SessionConstants;
import com.nian.cloudEyes.enums.UserRoleEnum;
import com.nian.cloudEyes.exception.ErrorCode;
import com.nian.cloudEyes.exception.ThrowUtils;
import com.nian.cloudEyes.model.User;
import com.nian.cloudEyes.model.dto.UserDto;
import com.nian.cloudEyes.model.vo.UserVo;
import com.nian.cloudEyes.service.UserService;
import com.nian.cloudEyes.mapper.UserMapper;
import com.nian.cloudEyes.utils.MdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Objects;

/**
* @author admin
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-06-19 16:12:31
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public void register(UserDto userDto) {
        // 1. 参数校验
        ThrowUtils.throwIf(StrUtil.hasBlank(new String[]{userDto.getUserAccount(), userDto.getUserPassword(), userDto.getCheckPassword()})
                || !Objects.equals(userDto.getUserPassword(), userDto.getCheckPassword()), ErrorCode.PARAMS_ERROR);
        // 2. 检查账号是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserAccount, userDto.getUserAccount());
        User entity = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(Objects.nonNull(entity), ErrorCode.OPERATION_ERROR, "账号已存在");
        // 3. 密码加密
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserRole(UserRoleEnum.USER.getValue());
        user.setUserPassword(MdUtils.toMd5(userDto.getUserPassword()));
        // 4.保存
        userMapper.insert(user);
    }

    @Override
    public UserVo login(UserDto userDto, HttpServletRequest request) {
        // 1. 参数校验
        ThrowUtils.throwIf(StrUtil.hasBlank(new String[]{userDto.getUserAccount(), userDto.getUserPassword()}), ErrorCode.PARAMS_ERROR);
        // 2. 查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userDto.getUserAccount())
                .eq(User::getUserPassword, MdUtils.toMd5(userDto.getUserPassword()));
        User user = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.OPERATION_ERROR, "用户不存在或密码错误");
        // 3. 记录用户登录态
        request.getSession().setAttribute(SessionConstants.LOGIN_USER_KEY, user);
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(SessionConstants.LOGIN_USER_KEY);
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionConstants.LOGIN_USER_KEY);
    }

    @Override
    public Page<UserVo> pageList(UserDto userDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .like((StrUtil.isNotBlank(userDto.getUserAccount())), User::getUserAccount, userDto.getUserAccount())
                .like((StrUtil.isNotBlank(userDto.getUserName())), User::getUserName, userDto.getUserName());
        Page<User> page = page(new Page<>(userDto.getCurrent(), userDto.getPageSize()), queryWrapper);
        ArrayList<UserVo> userVos = new ArrayList<>();
        page.getRecords().forEach(item -> {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(item, vo);
            userVos.add(vo);
        });
        Page<UserVo> userVoPage = new Page<>(userDto.getCurrent(), userDto.getPageSize(), page.getTotal());
        userVoPage.setRecords(userVos);
        return userVoPage;
    }

}