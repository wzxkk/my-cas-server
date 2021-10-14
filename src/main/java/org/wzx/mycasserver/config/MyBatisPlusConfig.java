package org.wzx.mycasserver.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @description: mybatis-plus 相关配置类
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-8 15:56
 * @version: 0.0.1
 */
@Configuration
@MapperScan("org.wzx.mycasserver.mapper")
public class MyBatisPlusConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("uuid", IdUtil.simpleUUID(), metaObject);
        LocalDateTime t = LocalDateTimeUtil.now();
        this.setFieldValByName("createTime", t, metaObject);
        this.setFieldValByName("updateTime", t, metaObject);
        this.setFieldValByName("status", "normal", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTimeUtil.now(), metaObject);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
