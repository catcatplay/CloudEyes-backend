package com.nian.cloudEyes.aop;

import com.nian.cloudEyes.annotation.AuthCheck;
import com.nian.cloudEyes.enums.UserRoleEnum;
import com.nian.cloudEyes.exception.BusinessException;
import com.nian.cloudEyes.exception.ErrorCode;
import com.nian.cloudEyes.model.User;
import com.nian.cloudEyes.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint 切入点
     * @param authCheck 权限校验注解
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 1. 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 2. 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (Objects.isNull(loginUser)) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        // 3. 获取用户角色
        String userRole = loginUser.getUserRole();
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(userRole);
        // 4. 获取注解要求的角色
        UserRoleEnum requiredRoleEnum = authCheck.role();
        // 5. 权限检查
        if (Objects.equals(requiredRoleEnum, UserRoleEnum.USER) || Objects.equals(userRoleEnum, requiredRoleEnum)) return joinPoint.proceed();
        // 6. 无权限
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
}
