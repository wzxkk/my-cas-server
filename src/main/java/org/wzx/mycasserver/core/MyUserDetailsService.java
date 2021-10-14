package org.wzx.mycasserver.core;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.wzx.mycasserver.entity.UserInfo;
import org.wzx.mycasserver.service.IUserInfoService;

/**
 * @description:
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-8 21:38
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("用户名：" + s);
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq(true, "username", s));
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        MyUserDetails myUserDetails = new MyUserDetails(userInfo);
        log.debug("用户信息：" + JSONUtil.toJsonStr(myUserDetails));
        return myUserDetails;
    }
}
