package org.wzx.mycasserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.wzx.mycasserver.entity.base.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: 用户与角色关系信息表
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-13
 * @version: 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user_authority")
@ApiModel(value="UserAuthority对象", description="用户与角色关系信息表")
public class UserAuthority extends CommonEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户表ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "角色表ID")
    @TableField("authority_id")
    private String authorityId;

}
