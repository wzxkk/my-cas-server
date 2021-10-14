package org.wzx.mycasserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import org.wzx.mycasserver.entity.base.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: 用户信息表
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-13
 * @version: 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="用户信息表")
public class UserInfo extends CommonEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "手机号")
    @TableField("cellphone")
    private String cellphone;

    @ApiModelProperty(value = "真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    @TableField("ident_card_num")
    private String identCardNum;

    @ApiModelProperty(value = "是否未过期")
    @TableField("is_account_non_expired")
    private Boolean isAccountNonExpired;

    @ApiModelProperty(value = "是否未被封号")
    @TableField("is_account_non_locked")
    private Boolean isAccountNonLocked;

    @ApiModelProperty(value = "密码是否未过期")
    @TableField("is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "是否可用")
    @TableField("is_enabled")
    private Boolean isEnabled;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "电子邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private String birthday;

    @ApiModelProperty(value = "登录类型")
    @TableField("login_type")
    private String loginType;

    @ApiModelProperty(value = "上一次登录时间")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "上一次登录IP")
    @TableField("last_login_IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "现在是否在线")
    @TableField("online")
    private Integer online;

    @TableField("current_login_time")
    private LocalDateTime currentLoginTime;

    @ApiModelProperty(value = "现在的IP")
    @TableField("current_IP")
    private String currentIp;

    @ApiModelProperty(value = "是否是超级管理员")
    @TableField("is_super")
    private Integer isSuper;

}
