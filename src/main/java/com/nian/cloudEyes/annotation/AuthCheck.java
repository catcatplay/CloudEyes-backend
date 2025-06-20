package com.nian.cloudEyes.annotation;

import com.nian.cloudEyes.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 角色权限
     */
    UserRoleEnum role() default UserRoleEnum.USER;
}
