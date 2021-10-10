package org.wzx.mycasserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.wzx.mycasserver.entity.base.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author: 鱼头
 * @description: 客户端信息表
 * @since: 2021-10-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("clients")
@ApiModel(value="Clients对象", description="客户端信息表")
public class Clients extends CommonEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "客户端代号")
    @TableField("client_code")
    private String clientCode;

    @ApiModelProperty(value = "客户端名称")
    @TableField("client_name")
    private String clientName;

    @ApiModelProperty(value = "协议")
    @TableField("protocol")
    private String protocol;

    @ApiModelProperty(value = "地址")
    @TableField("hostname")
    private String hostname;

    @ApiModelProperty(value = "端口")
    @TableField("port")
    private String port;

    @ApiModelProperty(value = "备注")
    @TableField("mark")
    private String mark;

}
