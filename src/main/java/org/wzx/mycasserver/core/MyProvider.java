package org.wzx.mycasserver.core;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-2 17:00
 */
@Slf4j
@Service
public class MyProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("调用自定义的provider,参数信息为:" + JSONUtil.toJsonStr(authentication));
        return authentication;
    }

    //supports() 方法接受一个 authentication 参数，
    // 用来判断传进来的 authentication 是不是该 AuthenticationProvider 能够处理的类型
    @Override
    public boolean supports(Class<?> aClass) {
        log.debug("supports,参数信息为:" + JSONUtil.toJsonStr(aClass));
        return false;
    }
}
