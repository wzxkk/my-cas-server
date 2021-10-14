package org.wzx.mycasserver.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 实体基础类
 * @author: 鱼头(韦忠幸)
 * @since: 2020/5/21 9:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity implements Serializable {
    /**
     * ID标识
     */
    @TableId("uuid" )
    @ApiModelProperty(value = "32位uuid" , hidden = true)
    private String uuid;

    /**
     * 添加时间
     */
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @ApiModelProperty(value = "数据创建时间" , hidden = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @ApiModelProperty(value = "数据最后的更新时间" , hidden = true)
    private LocalDateTime updateTime;
}
