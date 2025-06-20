package com.nian.cloudEyes.model.dto;

import com.nian.cloudEyes.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends PageRequest implements Serializable {
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String userPassword;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码")
    private String checkPassword;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String userName;

    private static final long serialVersionUID = 1L;
}
