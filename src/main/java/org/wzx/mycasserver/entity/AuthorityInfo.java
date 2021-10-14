package org.wzx.mycasserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.wzx.mycasserver.entity.base.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @description: 角色信息表
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-13
 * @version: 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("authority_info")
@ApiModel(value="AuthorityInfo对象", description="角色信息表")
public class AuthorityInfo extends CommonEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色名称")
    @TableField("authority")
    private String authority;

    @ApiModelProperty(value = "备注")
    @TableField("mark")
    private String mark;

}
