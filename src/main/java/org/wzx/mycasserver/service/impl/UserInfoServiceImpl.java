package org.wzx.mycasserver.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.wzx.mycasserver.entity.UserInfo;
import org.wzx.mycasserver.mapper.UserInfoMapper;
import org.wzx.mycasserver.service.IUserInfoService;

/**
 * @author: 鱼头
 * @description: 用户信息表服务实现类
 * @since: 2021-10-02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService, UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("用户名：" + s);
        UserInfo userInfo = getOne(new QueryWrapper<UserInfo>().eq(true, "username", s));
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        log.debug("用户信息：" + JSONUtil.toJsonStr(userInfo));
        return userInfo;
    }
}
