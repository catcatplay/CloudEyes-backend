package com.nian.cloudEyes.utils;

import org.springframework.util.DigestUtils;

public class MdUtils {
    private static final String SALT = "cloudEyes";

    public static String toMd5(String origin) {
        return DigestUtils.md5DigestAsHex((origin + SALT).getBytes());
    }
}
