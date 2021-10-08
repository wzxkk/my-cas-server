package org.wzx.mycasserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: 鱼头
 * @description: 服务基础接口
 * @since: 2020-06-28
 */
public interface BaseService<T> extends IService<T> {
    default boolean checkInsert(T entity) { return true; }

    default boolean checkDelete(String uuid) {
        return true;
    }

    default boolean checkUpdate(T entity) {
        return true;
    }
}
