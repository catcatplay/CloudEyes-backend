package com.nian.cloudEyes.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER("用户", "user"),
    ADMIN("管理员", "admin");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getEnumByValue(String userRole) {
        for (UserRoleEnum value : UserRoleEnum.values()) {
            if (value.value.equals(userRole)) {
                return value;
            }
        }
        return null;
    }
}
