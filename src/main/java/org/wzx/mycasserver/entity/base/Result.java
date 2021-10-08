package org.wzx.mycasserver.entity.base;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 鱼头
 * @description: API统一返回类
 * @time: 2020/5/21 9:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "API返回对象", description = "本系统所有API返回格式均为此格式！")
public class Result<T> implements Serializable {
    private T data;

    private String message = "操作成功";

    private int code = 0;

    private long elapsedTime = System.currentTimeMillis();

    public Result(T data) {
        this.data = data;
    }

    public Result(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public Result(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
    public void setTime() {
        this.elapsedTime = System.currentTimeMillis() - this.elapsedTime;
    }
}
